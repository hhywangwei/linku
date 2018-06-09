package com.linku.server.wx.pay.service;

import com.linku.server.BaseException;
import com.linku.server.common.id.IdGenerators;
import com.linku.server.common.utils.SecurityUtils;
import com.linku.server.order.domain.Order;
import com.linku.server.order.service.OrderService;
import com.linku.server.user.domain.User;
import com.linku.server.user.service.UserService;
import com.linku.server.wx.pay.client.TradeType;
import com.linku.server.wx.pay.dao.WxUnifiedOrderDao;
import com.linku.server.wx.pay.dao.WxUnifiedOrderNotifyDao;
import com.linku.server.wx.pay.domain.WxUnifiedOrder;
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
    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public WxUnifiedOrderService(WxUnifiedOrderDao dao,
                                 OrderService orderService, UserService userService) {
        this.dao = dao;
        this.orderService = orderService;
        this.userService = userService;
    }

    public WxUnifiedOrder get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("微信支付订单不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public WxUnifiedOrder save(String userId, String outTradeNo, TradeType tradeType){
        User u = userService.get(userId);
        Order o = orderService.get(outTradeNo);

        if(!isShop(u, o)){
            throw new BaseException("订单不存在");
        }

        if(o.getState() != Order.State.WAIT){
            throw new BaseException("订单已经处理");
        }

        if(dao.hasOutTradeNo(outTradeNo)){
            WxUnifiedOrder t = dao.findOneByOutTradeNo(outTradeNo);
            if(!isWait(t.getState())){
                throw new BaseException("订单已经处理");
            }
            return t;
        }

        WxUnifiedOrder t = new WxUnifiedOrder();
        t.setId(IdGenerators.uuid());
        t.setShopId(u.getShopId());
        t.setUserId(u.getId());
        t.setOpenid(u.getOpenid());
        t.setFeeType("CN");
        t.setBody(buildBody(o));
        t.setOutTradeNo(outTradeNo);
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
        //TODO build wx order body
        return "";
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
                orderService.pay(o.getOutTradeNo());
                return true;
            }
        }

        return false;

    }
}
