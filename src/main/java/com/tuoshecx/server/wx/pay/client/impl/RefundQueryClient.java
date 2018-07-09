package com.tuoshecx.server.wx.pay.client.impl;

import com.tuoshecx.server.wx.pay.client.request.RefundQueryRequest;
import com.tuoshecx.server.wx.pay.client.response.RefundQueryResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 微信查询退款客户端API处理
 *
 * @author WangWei
 */
class RefundQueryClient extends WxPayClient<RefundQueryRequest, RefundQueryResponse> {

    RefundQueryClient(RestTemplate restTemplate) {
        super(restTemplate, "refundQuery");
    }

    @Override
    protected String buildUri() {
        return "https://api.mch.weixin.qq.com/pay/refundquery";
    }

    @Override
    protected RefundQueryResponse buildResponse(Map<String, String> data) {
        return new RefundQueryResponse(data);
    }
}
