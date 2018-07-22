package com.tuoshecx.server.wx.pay.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.shop.domain.Shop;
import com.tuoshecx.server.shop.domain.ShopWxPay;
import com.tuoshecx.server.shop.service.ShopService;
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
import com.tuoshecx.server.wx.small.message.sender.WxTemplateMessageSender;
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
    private final WxUnifiedOrderService orderService;
    private final WxUnifiedOrderNotifyService notifyService;
    private final WxRefundService refundService;
    private final WxPayClientService clientService;
    private final ShopWxPayService shopWxPayService;
    private final ShopService shopService;
    private final WxTemplateMessageSender sender;

    @Autowired
    public WxPayService(WxPayProperties properties, WxUnifiedOrderService orderService,
                        WxUnifiedOrderNotifyService notifyService, WxRefundService refundService,
                        WxPayClientService clientService, ShopWxPayService shopWxPayService,
                        ShopService shopService, WxTemplateMessageSender sender) {

        this.properties = properties;
        this.orderService = orderService;
        this.clientService = clientService;
        this.notifyService = notifyService;
        this.refundService = refundService;
        this.shopWxPayService = shopWxPayService;
        this.shopService = shopService;
        this.sender = sender;
    }

    public WxUnifiedOrder prePay(String userId, String outTradeNo, TradeType tradeType){
        WxUnifiedOrder o = orderService.save(userId, outTradeNo, tradeType);
        ShopWxPay shopWxPay = shopWxPayService.getShopId(o.getShopId());
        try{
            UnifiedOrderRequest request = buildPerPayRequest(properties, shopWxPay, o);
            UnifiedOrderResponse response = clientService.unifiedOrder(request);
            orderService.updatePrePay(o.getId(), response.getPrepayId());
            return orderService.get(o.getId());
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
        boolean ok = orderService.pay(t.getOutTradeNo(), t.getTransactionId(), t.getTotalFee());
        if(ok){
            sendOrderPayNotify(t.getOutTradeNo());
        }
        return ok;
    }

    private void sendOrderPayNotify(String outTradeNo){
        WxUnifiedOrder o = orderService.getOutTradeNo(outTradeNo);
        Shop shop = shopService.get(o.getShopId());

        sender.sendOrderPaySuccess(o.getOpenid(), o.getAppid(),o.getPrepay(),
                shop.getName(), o.getBody(), o.getTotalFee(), o.getPayTime());
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
