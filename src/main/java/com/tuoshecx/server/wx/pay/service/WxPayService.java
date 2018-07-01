package com.tuoshecx.server.wx.pay.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.common.id.IdGenerators;
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
    private final WxUnifiedOrderService orderService;
    private final WxUnifiedOrderNotifyService notifyService;
    private final WxRefundService refundService;
    private final WxPayClientService clientService;

    @Autowired
    public WxPayService(WxPayProperties properties, WxUnifiedOrderService orderService,
                        WxUnifiedOrderNotifyService notifyService, WxRefundService refundService,
                        WxPayClientService clientService) {

        this.properties = properties;
        this.orderService = orderService;
        this.clientService = clientService;
        this.notifyService = notifyService;
        this.refundService = refundService;
    }

    public String prePay(String userId, String outTradeNo, TradeType tradeType){
        WxUnifiedOrder o = orderService.save(userId, outTradeNo, tradeType);
        try{
            UnifiedOrderRequest request = buildPerPayRequest(properties, o);
            UnifiedOrderResponse response = clientService.unifiedOrder(request);
            return response.getPrepayId();
        }catch (Exception e){
            logger.error("Wx prePay fail, error is {}", e.getMessage());
            throw new BaseException("支付失败");
        }
    }

    private UnifiedOrderRequest buildPerPayRequest(WxPayProperties properties, WxUnifiedOrder order){

        TradeType tradeType = TradeType.valueOf(order.getTradeType());
        String body = StringUtils.isNotBlank(order.getBody()) ? order.getBody() : properties.getSign();
        UnifiedOrderRequest.Builder builder = new UnifiedOrderRequest.Builder(
                properties.getAppid(), properties.getMchid(), properties.getKey(), body,
                order.getOutTradeNo(), order.getTotalFee(), properties.getNotifyUrl(),
                tradeType, order.getOpenid());

        return tradeType == TradeType.NATIVE ?
                builder.setAttach(order.getAttach()).setProductId(order.getId()).build() :
                builder.setAttach(order.getAttach()).build();
    }

    /**
     * 微信支付通知业务服务
     *
     * @param data 支付腿上数据
     * @return true:成功
     */
    public boolean payNotify(Map<String, String> data){
        WxUnifiedOrderNotify t = new WxUnifiedOrderNotify(data);
        t.setId(IdGenerators.uuid());
        notifyService.save(t);
        return orderService.pay(t.getOutTradeNo(), t.getTransactionId(), t.getTotalFee());
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
            RefundRequest request = buildRefundRequest(properties, t);
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

    private RefundRequest buildRefundRequest(WxPayProperties properties, WxRefund t){
        return new RefundRequest.Builder(properties.getAppid(), properties.getMchid(),
                properties.getKey(), t.getOutTradeNo(), t.getId(), t.getTotalFee(), t.getTotalFee())
                .setRefundDesc(t.getRefundDesc())
                .build();
    }
}
