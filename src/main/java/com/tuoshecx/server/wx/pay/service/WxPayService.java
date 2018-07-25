package com.tuoshecx.server.wx.pay.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.order.event.PaySuccessEvent;
import com.tuoshecx.server.order.event.PaySuccessPublisher;
import com.tuoshecx.server.order.service.OrderService;
import com.tuoshecx.server.shop.domain.ShopWxPay;
import com.tuoshecx.server.shop.service.ShopWxPayService;
import com.tuoshecx.server.wx.configure.properties.WxPayProperties;
import com.tuoshecx.server.wx.pay.client.TradeType;
import com.tuoshecx.server.wx.pay.client.WxPayClientService;
import com.tuoshecx.server.wx.pay.client.request.RefundRequest;
import com.tuoshecx.server.wx.pay.client.request.UnifiedOrderRequest;
import com.tuoshecx.server.wx.pay.client.response.RefundResponse;
import com.tuoshecx.server.wx.pay.client.response.UnifiedOrderResponse;
import com.tuoshecx.server.wx.pay.domain.WxRefund;
import com.tuoshecx.server.wx.pay.domain.WxUnifiedOrder;
import com.tuoshecx.server.wx.pay.domain.WxUnifiedOrderNotify;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 微信支付业务服务
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@Service
public class WxPayService {
    private static final Logger logger = LoggerFactory.getLogger(WxPayService.class);

    private final WxPayProperties properties;
    private final WxUnifiedOrderService wxUnifiedOrderService;
    private final WxUnifiedOrderNotifyService notifyService;
    private final WxRefundService refundService;
    private final WxPayClientService clientService;
    private final ShopWxPayService shopWxPayService;
    private final OrderService orderService;
    private final PaySuccessPublisher publisher;

    @Autowired
    public WxPayService(WxPayProperties properties, WxUnifiedOrderService wxUnifiedOrderService,
                        WxUnifiedOrderNotifyService notifyService, WxRefundService refundService,
                        WxPayClientService clientService, ShopWxPayService shopWxPayService,
                        OrderService orderService, PaySuccessPublisher publisher) {

        this.properties = properties;
        this.wxUnifiedOrderService = wxUnifiedOrderService;
        this.clientService = clientService;
        this.notifyService = notifyService;
        this.refundService = refundService;
        this.shopWxPayService = shopWxPayService;
        this.orderService = orderService;
        this.publisher = publisher;
    }

    public WxUnifiedOrder prePay(String userId, String outTradeNo, TradeType tradeType){
        WxUnifiedOrder o = wxUnifiedOrderService.save(userId, orderService.get(outTradeNo), tradeType);
        ShopWxPay shopWxPay = shopWxPayService.getShopId(o.getShopId());
        try{
            UnifiedOrderRequest request = buildPerPayRequest(properties, shopWxPay, o);
            UnifiedOrderResponse response = clientService.unifiedOrder(request);
            wxUnifiedOrderService.updatePrePay(o.getId(), response.getPrepayId());
            return wxUnifiedOrderService.get(o.getId());
        }catch (Exception e){
            logger.error("Wx prePay fail, error is {}", e.getMessage());
            throw new BaseException("支付失败");
        }
    }

    private UnifiedOrderRequest buildPerPayRequest(WxPayProperties properties, ShopWxPay shopWxPay, WxUnifiedOrder order){

        TradeType tradeType = TradeType.valueOf(order.getTradeType());
        UnifiedOrderRequest.Builder builder;
        if(isShop(properties.getPayType())){
            builder = new UnifiedOrderRequest.Builder(
                    shopWxPay.getAppid(), shopWxPay.getMchId(), shopWxPay.getKey(), order.getBody(),
                    order.getOutTradeNo(), order.getTotalFee(), properties.getNotifyUrl(),
                    tradeType, order.getOpenid(),properties.getBillCreateIp());
        }else{
            builder = new UnifiedOrderRequest.Builder(
                    shopWxPay.getAppid(), properties.getMchid(), shopWxPay.getMchId(), shopWxPay.getKey(), order.getBody(),
                    order.getOutTradeNo(), order.getTotalFee(), properties.getNotifyUrl(),
                    tradeType, order.getOpenid(), properties.getBillCreateIp());
        }

        return tradeType == TradeType.NATIVE ?
                builder.setAttach(order.getAttach()).setProductId(order.getId()).build() :
                builder.setAttach(order.getAttach()).build();
    }

    private boolean isShop(String payType){
        return StringUtils.equals(payType, "shop");
    }

    /**
     * 微信支付通知业务服务
     *
     * @param data 支付数据
     * @return true:成功
     */
    public boolean payNotify(Map<String, String> data){
        WxUnifiedOrderNotify t = new WxUnifiedOrderNotify(data);
        t.setId(IdGenerators.uuid());
        notifyService.save(t);
        boolean ok = wxUnifiedOrderService.pay(t.getOutTradeNo(), t.getTransactionId(), t.getTotalFee());
        if(ok){
            publisher.publishEvent(new PaySuccessEvent(t.getOutTradeNo()));
        }
        return ok;
    }

    /**
     * 微信退款
     *
     * @param outTradeNo 订单编号
     * @param refundDesc 退款原因
     * @return
     */
    public WxRefund refund(String outTradeNo, String refundDesc){
        WxRefund t = refundService.refund(outTradeNo, refundDesc);

        if(!isWait(t.getState())){
            return t;
        }

        try{
            ShopWxPay shopWxPay = shopWxPayService.getShopId(t.getShopId());
            RefundRequest request = buildRefundRequest(properties, shopWxPay, t);
            RefundResponse response = clientService.refund(request);
            if(!response.isSuccess()){
                throw new BaseException(response.getErrCodeDes());
            }
            refundService.apply(response.getOutRefundNo(), response.getRefundId());
            return refundService.get(t.getId());
        }catch (Exception e){
            logger.error("Wx refund fail, error is {}", e.getMessage());
            throw new BaseException("退款失败");
        }
    }

    private boolean isWait(String state){
        return StringUtils.equals(state, "wait");
    }

    private RefundRequest buildRefundRequest(WxPayProperties properties, ShopWxPay shopWxPay, WxRefund t){
        if(isShop(properties.getPayType())){
            return new RefundRequest.Builder(shopWxPay.getAppid(), shopWxPay.getMchId(),
                shopWxPay.getKey(), t.getOutTradeNo(), t.getId(), t.getTotalFee(), t.getTotalFee())
                .setRefundDesc(t.getRefundDesc())
                .build();
        }else{
            return new RefundRequest.Builder(shopWxPay.getAppid(), properties.getMchid(), shopWxPay.getMchId(),
                    shopWxPay.getKey(), t.getOutTradeNo(), t.getId(), t.getTotalFee(), t.getTotalFee())
                    .setRefundDesc(t.getRefundDesc())
                    .build();
        }
    }
}
