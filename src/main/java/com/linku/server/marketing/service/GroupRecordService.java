package com.linku.server.marketing.service;

import com.linku.server.BaseException;
import com.linku.server.common.utils.DateUtils;
import com.linku.server.marketing.domain.*;
import com.linku.server.marketing.dao.GroupRecordDao;
import com.linku.server.marketing.dao.GroupRecordItemDao;
import com.linku.server.common.id.IdGenerators;
import com.linku.server.marketing.event.GroupRecordPublisher;
import com.linku.server.user.domain.User;
import com.linku.server.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 拼团业务服务
 *
 * @author <a href="mailto:hhywangwei@mgail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class GroupRecordService {
    private static final Logger logger = LoggerFactory.getLogger(GroupRecordService.class);

    private static final int MAX_TRY = 5;
    private static final int JOIN_USER_LIMIT = 3;

    private final GroupRecordDao dao;
    private final GroupRecordItemDao itemDao;
    private final UserService userService;
    private final GroupBuyService groupBuyService;
    private final SharePresentService presentService;
    private final GroupRecordPublisher publisher;

    @Autowired
    public GroupRecordService(GroupRecordDao dao, GroupRecordItemDao itemDao, UserService userService,
                              GroupBuyService groupBuyService, SharePresentService presentService,
                              GroupRecordPublisher publisher) {

        this.dao = dao;
        this.itemDao = itemDao;
        this.userService = userService;
        this.groupBuyService = groupBuyService;
        this.presentService = presentService;
        this.publisher = publisher;
    }

    public GroupRecord get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("营销活动不存在");
        }
    }

    public Optional<GroupRecord> getOption(String id){
        try{
            return Optional.of(dao.findOne(id));
        }catch (DataAccessException e){
            return Optional.empty();
        }
    }

    public List<GroupRecordItem> getItems(String id){
        return itemDao.findLimit(id, Integer.MAX_VALUE);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public GroupRecord saveGroup(String userId, String id){
        User user = userService.get(userId);
        GroupBuy m = groupBuyService.get(id);

        if(!isShop(user, m)){
            throw new BaseException("营销活动不存在");
        }

        GroupRecord r = createRecord(m, user, "GROUP_BUY", m.getPerson(), m.getDays(), false);
        dao.insert(r);

        return get(r.getId());
    }

    private GroupRecord createRecord(Marketing m, User user, String type, int person, int days, boolean isFirst){
        GroupRecord t = new GroupRecord();

        t.setId(IdGenerators.uuid());
        t.setShopId(m.getShopId());
        t.setUserId(user.getId());
        t.setMarketingId(m.getId());
        t.setName(m.getName());
        t.setIcon(m.getIcon());
        t.setPrice(m.getPrice());
        t.setGoodsId(m.getGoodsId());
        t.setJoinPerson(0);
        t.setType(type);
        t.setState(GroupRecord.State.WAIT);
        t.setStartTime(new Date());
        t.setEndTime(DateUtils.plusDays(t.getStartTime(), days));
        t.setNeedPerson(person);
        t.setFirst(isFirst);

        return t;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public GroupRecord savePresenter(String userId, String id){
        User user = userService.get(userId);
        SharePresent m = presentService.get(id);

        if(!isShop(user, m)){
            throw new BaseException("营销活动不存在");
        }

        GroupRecord r = createRecord(m, user, "SHARE_PRESENTER", m.getPerson(), m.getDays(), m.getFirst());
        dao.insert(r);

        return get(r.getId());
    }

    private boolean isShop(User u, Marketing m){
        return StringUtils.equals(u.getShopId(), m.getShopId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveItem(String id, String userId, String orderId, boolean isFirst){
        User user = userService.get(userId);

        if(itemDao.hasOrderId(orderId)){
            logger.warn("Order has group marketing, id is {}", orderId);
            return ;
        }

        for(int i = 0; i < MAX_TRY; i++){
            GroupRecord t = get(id);
            final int inc = (!t.getFirst()|(t.getFirst() && isFirst)) ? 1 : 0;
            if(inc == 0){
                return ;
            }

            if(incJoinPersonAndSaveItem(t, user, orderId, isFirst)){
                return ;
            }
        }

        throw new BaseException("创建拼团活动失败");
    }

    private boolean incJoinPersonAndSaveItem(GroupRecord t, User user, String orderId, boolean isFirst){
        final String id = t.getId();
        final int version = t.getVersion();
        final boolean ok = dao.incJoinPerson(id, 1, version);

        if(ok){
            boolean isOwner = StringUtils.equals(t.getUserId(), user.getId());
            final boolean isFull = t.getNeedPerson() <= (t.getJoinPerson() + 1);
            saveItem(id, user, orderId, isOwner, isFirst);
            updateJoinUserDetail(id);
            if(isFull){
                publisher.publishEvent(id, GroupRecord.State.ACTIVATE);
            }
        }

        return ok;
    }

    private void saveItem(String id, User user, String orderId, boolean isOwner, boolean isFirst){
        GroupRecordItem t = new GroupRecordItem();
        t.setId(IdGenerators.uuid());
        t.setOrderId(orderId);
        t.setRecordId(id);
        t.setUserId(user.getId());
        t.setNickname(user.getNickname());
        t.setHeadImg(user.getHeadImg());
        t.setPhone(user.getPhone());
        t.setOwner(isOwner);
        t.setFirst(isFirst);

        itemDao.insert(t);
    }

    private void updateJoinUserDetail(String id){
        List<GroupRecordItem> items = itemDao.findLimit(id, JOIN_USER_LIMIT);
        String detail = "[" + items.stream().map(this::buildUserDetail).collect(Collectors.joining(",")) + "]";
        dao.updateJoinUserDetail(id, detail);
    }

    private String buildUserDetail(GroupRecordItem e){
        return  String.format("{\"name\": \"%s\", \"headImg\": \"%s\"}",
                StringUtils.defaultString(e.getNickname(), ""),
                StringUtils.defaultString(e.getHeadImg(), ""));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void expire(int limit){
        Date now = new Date();

        List<GroupRecord> records;
        while ((records = dao.findExpired(now, limit)).size() != limit){
            records.forEach(this::closeExpire);
        }
    }

    private void closeExpire(GroupRecord t){
        if(dao.close(t.getId())){
            publisher.publishEvent(t.getId(), GroupRecord.State.CLOSE);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean close(String id){
        GroupRecord t = get(id);
        if(isClose(t.getState())){
            return true;
        }
        return dao.close(t.getId());
    }

    private boolean isClose(GroupRecord.State state){
        return state == GroupRecord.State.CLOSE;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean active(String id){
        GroupRecord t = get(id);
        if(!isWait(t.getState())){
            throw new BaseException("营销活动已经处理");
        }
        return dao.active(id);
    }

    private boolean isWait(GroupRecord.State state){
        return state == GroupRecord.State.WAIT;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void share(String id){
        dao.share(id);
    }

    public long count(String userId, String activeId, GroupRecord.State state){
        return dao.count(userId, activeId, state);
    }

    public List<GroupRecord> query(String userId, String activeId, GroupRecord.State state, int offset, int limit){
        return dao.find(userId, activeId, state, StringUtils.EMPTY, offset, limit);
    }
}
