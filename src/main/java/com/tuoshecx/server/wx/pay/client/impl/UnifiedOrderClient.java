package com.tuoshecx.server.wx.pay.client.impl;

import com.tuoshecx.server.wx.pay.client.request.UnifiedOrderRequest;
import com.tuoshecx.server.wx.pay.client.response.UnifiedOrderResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 微信支付统一下单API请求处理
 *
 * @author WangWei
 */
class UnifiedOrderClient extends WxPayClient<UnifiedOrderRequest, UnifiedOrderResponse> {

    UnifiedOrderClient(RestTemplate restTemplate) {
        super(restTemplate, "unifiedOrder");
    }

    @Override
    protected String buildUri() {
        return "https://api.mch.weixin.qq.com/pay/unifiedorder";
    }

    @Override
    protected UnifiedOrderResponse buildResponse(Map<String, String> data) {
        return new UnifiedOrderResponse(data);
    }
}
