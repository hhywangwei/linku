package com.tuoshecx.server.wx.pay.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.order.service.OrderService;
import com.tuoshecx.server.wx.pay.dao.WxRefundDao;
import com.tuoshecx.server.wx.pay.dao.WxUnifiedOrderDao;
import com.tuoshecx.server.wx.pay.domain.WxRefund;
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
 * 微信退款申请
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class WxRefundService {
    private static final Logger logger = LoggerFactory.getLogger(WxRefundService.class);

    private final WxRefundDao dao;
    private final WxUnifiedOrderDao unifiedOrderDao;
    private final OrderService orderService;

    @Autowired
    public WxRefundService(WxRefundDao dao, WxUnifiedOrderDao unifiedOrderDao,
                           OrderService orderService) {
        this.dao = dao;
        this.unifiedOrderDao = unifiedOrderDao;
        this.orderService = orderService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public WxRefund refund(String outTradeNo, String refundDesc){
        WxUnifiedOrder o = getUnifiedOrder(outTradeNo);
        if(isRoll(o.getState())){
            throw new BaseException("已经申请退款");
        }
        Optional<WxRefund> optional = getByOutTradeNo(outTradeNo);
        if(optional.isPresent()){
            return optional.get();
        }

        WxRefund t = new WxRefund();

        t.setId(IdGenerators.uuid());
        t.setUserId(o.getUserId());
        t.setOpenid(o.getOpenid());
        t.setShopId(o.getShopId());
        t.setOutTradeNo(o.getOutTradeNo());
        t.setRefundDesc(refundDesc);
        t.setRefundFee(o.getTotalFee());
        t.setTotalFee(o.getTotalFee());
        t.setState("wait");

        dao.insert(t);
        return dao.findOne(t.getId());
    }

    private WxUnifiedOrder getUnifiedOrder(String outTradeNo){
        try{
            return unifiedOrderDao.findOneByOutTradeNo(outTradeNo);
        }catch (DataAccessException e){
            logger.debug("Get wx unified order fail, outTradeNo is {} error is {}", outTradeNo, e.getMessage());
            throw new BaseException("订单不存在或还未支付");
        }
    }

    private boolean isRoll(String state){
        return StringUtils.equalsIgnoreCase(state, "roll");
    }

    private Optional<WxRefund> getByOutTradeNo(String outTradeNo){
        try{
            return Optional.of(dao.findOneByOutTradeNo(outTradeNo));
        }catch (DataAccessException e){
            logger.debug("Get wx refund fail, outTradeNo is {} error is {}", outTradeNo, e.getMessage());
            return Optional.empty();
        }
    }

    public WxRefund get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("退款记录不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean apply(String id, String refundId){
        boolean ok = dao.apply(id, refundId);
        if(ok){
            WxRefund t = get(id);
            orderService.roll(t.getOutTradeNo());
        }
        return ok;
    }

}
