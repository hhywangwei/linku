package com.tuoshecx.server.wx.pay.client.impl;

import com.tuoshecx.server.wx.pay.client.request.RefundRequest;
import com.tuoshecx.server.wx.pay.client.response.RefundResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 实现微信退款客户端API调用
 *
 * @author WangWei
 */
class RefundClient extends WxPayClient<RefundRequest, RefundResponse> {

    RefundClient(RestTemplate restTemplate) {
        super(restTemplate, "refund");
    }

    @Override
    protected String buildUri() {
        return "https://api.mch.weixin.qq.com/secapi/pay/refund";
    }

    @Override
    protected RefundResponse buildResponse(Map<String, String> data) {
        return new RefundResponse(data);
    }
}
