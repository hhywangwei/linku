package com.tuoshecx.server.wx.pay.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.common.utils.SecurityUtils;
import com.tuoshecx.server.order.domain.Order;
import com.tuoshecx.server.shop.domain.Shop;
import com.tuoshecx.server.shop.service.ShopService;
import com.tuoshecx.server.user.domain.User;
import com.tuoshecx.server.user.service.UserService;
import com.tuoshecx.server.wx.pay.client.TradeType;
import com.tuoshecx.server.wx.pay.dao.WxUnifiedOrderDao;
import com.tuoshecx.server.wx.pay.domain.WxUnifiedOrder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 微信统一订单业务服务
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class WxUnifiedOrderService {
    private static final Logger logger = LoggerFactory.getLogger(WxUnifiedOrderService.class);
    private static final int MAX_TRY = 5;

    private final WxUnifiedOrderDao dao;
    private final UserService userService;
    private final ShopService shopService;

    @Autowired
    public WxUnifiedOrderService(WxUnifiedOrderDao dao, UserService userService, ShopService shopService) {
        this.dao = dao;
        this.userService = userService;
        this.shopService = shopService;
    }

    public WxUnifiedOrder get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("微信支付订单不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public WxUnifiedOrder save(String userId, Order o, TradeType tradeType){
        User u = userService.get(userId);

        if(!isShop(u, o)){
            throw new BaseException("订单不存在");
        }

        if(o.getState() != Order.State.WAIT){
            throw new BaseException("订单已经处理");
        }

        if(dao.hasOutTradeNo(o.getId())){
            WxUnifiedOrder t = dao.findOneByOutTradeNo(o.getId());
            if(!isWait(t.getState())){
                throw new BaseException("订单已经处理");
            }
            return t;
        }

        WxUnifiedOrder t = new WxUnifiedOrder();
        t.setId(IdGenerators.uuid());
        t.setShopId(u.getShopId());
        t.setAppid(u.getAppid());
        t.setUserId(u.getId());
        t.setOpenid(u.getOpenid());
        t.setFeeType("CN");
        t.setBody(buildBody(o));
        t.setOutTradeNo(o.getId());
//        t.setTotalFee(o.getPayTotal());
        //TODO 测试只支付1分钱
        t.setTotalFee(1);
        t.setState("wait");
        t.setTradeType(tradeType.name());
        t.setAttach(SecurityUtils.randomStr(16));
        dao.insert(t);

        return get(t.getId());
    }

    private boolean isShop(User u, Order o){
        return StringUtils.equals(u.getShopId(), o.getShopId());
    }

    private boolean isWait(String state){
        return StringUtils.equals(state, "wait");
    }

    private String buildBody(Order o){
        Shop shop = shopService.get(o.getShopId());
        return shop.getName() + "消费";
    }

    public WxUnifiedOrder getOutTradeNo(String outTradeNo){
        return dao.findOneByOutTradeNo(outTradeNo);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updatePrePay(String id, String prePay){
        return dao.updatePrePay(id, prePay);
    }

    private Optional<WxUnifiedOrder> getByOutTradeNo(String outTradeNo){
        try{
            return Optional.of(dao.findOneByOutTradeNo(outTradeNo));
        }catch (DataAccessException e){
            logger.warn("Wx unified order not exists, outTradeNo is {}", outTradeNo);
            return Optional.empty();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean pay(String outTradeNo, String transactionId, Integer totalFee){
        Optional<WxUnifiedOrder> optional = getByOutTradeNo(outTradeNo);
        if(!optional.isPresent()){
            return true;
        }

        for(int i = 0; i < MAX_TRY; i++){
            WxUnifiedOrder o = optional.get();
            if(!isWait(o.getState())){
                return true;
            }

            if(dao.pay(o.getId(), transactionId, totalFee)){
                return true;
            }
        }

        return false;

    }
}
