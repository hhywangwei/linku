package com.tuoshecx.server.marketing.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.marketing.dao.SecondKillRecordDao;
import com.tuoshecx.server.marketing.domain.SecondKill;
import com.tuoshecx.server.marketing.domain.SecondKillRecord;
import com.tuoshecx.server.user.domain.User;
import com.tuoshecx.server.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SecondKillRecordService {
    private final SecondKillRecordDao dao;
    private final UserService userService;
    private final SecondKillService secondKillService;

    @Autowired
    public SecondKillRecordService(SecondKillRecordDao dao, UserService userService,
                                   SecondKillService secondKillService) {

        this.dao = dao;
        this.userService = userService;
        this.secondKillService = secondKillService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public SecondKillRecord save(String userId, String marketingId){

        for(int i = 0; i < 5; i++){

            if(dao.has(userId, marketingId)){
                throw new BaseException("已经购买");
            }

            User u = userService.get(userId);
            SecondKill m = secondKillService.get(marketingId);

            if(!isShop(u, m)){
                throw new BaseException("秒杀活动不存在");
            }

            if(!m.getOpen()){
                throw new BaseException("秒杀活动未开启");
            }

            if(m.getRemain() <= 0){
                throw new BaseException("商品已无库存");
            }

            if(secondKillService.incRemain(m.getId(), -1, m.getVersion())){
                SecondKillRecord t = new SecondKillRecord();
                t.setId(IdGenerators.uuid());
                t.setShopId(u.getShopId());
                t.setGoodsId(m.getGoodsId());
                t.setMarketingId(m.getId());
                t.setName(m.getName());
                t.setIcon(m.getIcon());
                t.setPrice(m.getPrice());
                t.setNickname(u.getNickname());
                t.setHeadImg(u.getHeadImg());
                t.setPhone(u.getPhone());
                t.setState(SecondKillRecord.State.WAIT);
                t.setUserId(u.getId());
                t.setOrderId(t.getId());

                dao.insert(t);

                return dao.findOne(t.getId());
            }
        }

        throw new BaseException("秒杀失败");
    }

    private boolean isShop(User u, SecondKill m) {
        return StringUtils.equals(u.getShopId(), m.getShopId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updateOrderId(String id, String orderId){
        return dao.updateOrderId(id, orderId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean pay(String id){
        return dao.pay(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean cancel(String id){
        return dao.cancel(id);
    }

    public SecondKillRecord get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("秒杀记录不存在");
        }
    }

    public Optional<SecondKillRecord> getOption(String id){
        try{
            return Optional.of(dao.findOne(id));
        }catch (DataAccessException e){
            return Optional.empty();
        }
    }

    public List<SecondKillRecord> queryExpired(int minutes, int limit) {
        return dao.findExpired(minutes, limit);
    }

    public long count(String shopId, String marketingId, String orderId, String name) {
        return dao.count(shopId, marketingId, orderId, name);
    }

    public List<SecondKillRecord> query(String shopId, String marketingId, String orderId, String name, int offset, int limit){
        return dao.find(shopId, marketingId, orderId, name, offset, limit);
    }
}
