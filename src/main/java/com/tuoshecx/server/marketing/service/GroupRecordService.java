package com.tuoshecx.server.marketing.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.common.utils.DateUtils;
import com.tuoshecx.server.marketing.dao.GroupRecordDao;
import com.tuoshecx.server.marketing.dao.GroupRecordItemDao;
import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.marketing.domain.*;
import com.tuoshecx.server.marketing.event.GroupRecordFinishPublisher;
import com.tuoshecx.server.user.domain.User;
import com.tuoshecx.server.user.service.UserService;
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
    private final GroupRecordFinishService recordFinishService;
    private final GroupRecordFinishPublisher publisher;

    @Autowired
    public GroupRecordService(GroupRecordDao dao, GroupRecordItemDao itemDao, UserService userService,
                              GroupBuyService groupBuyService, SharePresentService presentService,
                              GroupRecordFinishService recordFinishService, GroupRecordFinishPublisher publisher) {

        this.dao = dao;
        this.itemDao = itemDao;
        this.userService = userService;
        this.groupBuyService = groupBuyService;
        this.presentService = presentService;
        this.recordFinishService = recordFinishService;
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

    public GroupRecordItem getItem(String id){
        try{
            return itemDao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("明细不存在");
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
            //TODO 屏蔽倒流首单用户处理
//            final int inc = (!t.getFirst()||(t.getFirst() && isFirst)) ? 1 : 0;
//            if(inc == 0){
//                return ;
//            }

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

        if(!ok){
            return false;
        }

        boolean isOwner = StringUtils.equals(t.getUserId(), user.getId());
        String itemId = saveItem(id, user, orderId, isOwner, isFirst);
        updateJoinUserDetail(id);

        if(t.getState() == GroupRecord.State.SUCCESS){
            finishHandler(t.getId(), itemId, GroupRecord.State.SUCCESS);
            return true;
        }

        if(t.getState() == GroupRecord.State.CLOSE){
            finishHandler(t.getId(), itemId, GroupRecord.State.CLOSE);
            return true;
        }

        if(t.getJoinPerson() == 0){
            dao.active(id);
        }

        boolean isFull = t.getNeedPerson() <= (t.getJoinPerson() + 1);
        if(isFull && dao.success(id)){
            finishHandler(t.getId(), "*", GroupRecord.State.SUCCESS);
        }

        return true;
    }

    private String saveItem(String id, User user, String orderId, boolean isOwner, boolean isFirst){
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

        return t.getId();
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

    private void finishHandler(String id, String itemId, GroupRecord.State state){
        recordFinishService.save(id, itemId, state.name());
        publisher.publishEvent(id, itemId, state);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void expire(int limit){
        Date now = new Date();
        List<GroupRecord> records;
        do {
            records = dao.findExpired(now, limit);
            records.forEach(this::closeExpire);
        } while (records.size() == limit);
    }

    private void closeExpire(GroupRecord t){
        if(dao.close(t.getId())){
            finishHandler(t.getId(), "*", GroupRecord.State.CLOSE);
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
    public void share(String id){
        dao.share(id);
    }

    public Optional<GroupRecord> getPresentOneWait(String marketingId, String userId){
        try{
            return Optional.of(dao.findPresentOneWait(marketingId, userId));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    public long count(String userId, String activeId, GroupRecord.State state){
        return dao.count(userId, activeId, state);
    }

    public List<GroupRecord> query(String userId, String activeId, GroupRecord.State state, String order, int offset, int limit){
        return dao.find(userId, activeId, state, order, offset, limit);
    }
}
