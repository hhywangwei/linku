package com.tuoshecx.server.ticket.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.base.service.AutoNumberService;
import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.common.utils.DateUtils;
import com.tuoshecx.server.ticket.dao.EticketDao;
import com.tuoshecx.server.ticket.domain.Eticket;
import com.tuoshecx.server.goods.domain.Goods;
import com.tuoshecx.server.goods.service.GoodsService;
import com.tuoshecx.server.user.domain.User;
import com.tuoshecx.server.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 电子券业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class EticketService {
    private static final int MAX_TRY = 5;

    private final EticketDao dao;
    private final UserService userService;
    private final GoodsService goodsService;
    private final AutoNumberService autoNumberService;

    @Autowired
    public EticketService(EticketDao dao, UserService userService,
                          GoodsService goodsService, AutoNumberService autoNumberService){
        this.dao = dao;
        this.userService = userService;
        this.goodsService = goodsService;
        this.autoNumberService = autoNumberService;
    }

    public Eticket get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("电子券不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(String userId, String orderId, String goodsId){
        User user = userService.get(userId);
        Goods goods = goodsService.get(goodsId);

        if(goods.getGroup()){
            goodsService.queryGroupItems(goodsId).forEach(
                    e -> saveEticket(user, orderId, e.getItemGoodsId(), e.getName(), e.getIcon()));
        }else{
            saveEticket(user, orderId, goods.getId(), goods.getName(), goods.getIcon());
        }
    }

    private void saveEticket(User user, String orderId, String goodsId, String goodsName, String goodsIcon){
        Eticket t = new Eticket();
        t.setId(IdGenerators.uuid());
        t.setCode(genCode(user.getShopId()));
        t.setShopId(user.getShopId());
        t.setUserId(user.getId());
        t.setName(StringUtils.isBlank(user.getName())? user.getNickname(): user.getName());
        t.setHeadImg(user.getHeadImg());
        t.setOrderId(orderId);
        t.setGoodsId(goodsId);
        t.setGoodsName(goodsName);
        t.setGoodsIcon(goodsIcon);
        t.setState(Eticket.State.WAIT);
        Date now = new Date();
        t.setFromDate(now);
        t.setToDate(DateUtils.plusDays(now, 365));

        dao.insert(t);
    }

    private String genCode(String shopId){
        String shopCode = String.format("%04d", Math.abs(shopId.hashCode()) % 10000);
        String now = DateUtils.format("yyyyMMdd", new Date());
        String key = shopId + "@eticket@" + now;
        String code = String.format("%06d", autoNumberService.maxNumber(key));
        return shopCode + now  + code;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Eticket use(String code, String shopId){

        for(int i = 0 ; i < MAX_TRY ; i++){
            Eticket t = getByCode(code);

            if(!StringUtils.equals(t.getShopId(), shopId)){
                throw new BaseException("电子券不存在");
            }
            if(t.getState() == Eticket.State.USE){
                throw new BaseException("电子券已经使用");
            }
            if(t.getState() == Eticket.State.EXPIRE) {
                throw new BaseException("电子券已经过期");
            }
            if(t.getState() == Eticket.State.PRESENT){
                throw new BaseException("电子券已经赠送");
            }

            if(dao.updateState(t.getId(), Eticket.State.USE, t.getVersion())){
                return get(t.getId());
            }
        }

        throw new BaseException("电子券使用失败");
    }

    public Eticket getByCode(String code){
        try{
            return dao.findOneByCode(code);
        }catch (DataAccessException e){
            throw new BaseException("电子券不存在");
        }
    }

    public long count(String userId, String shopId, Eticket.State state, String name, String goodsName){
        return dao.count(userId, shopId, state, name, goodsName);
    }

    public List<Eticket> query(String userId, String shopId, Eticket.State state, String name, String goodsName, int offset, int rows){
        return dao.find(userId, shopId, state, name, goodsName, offset, rows);
    }
}
