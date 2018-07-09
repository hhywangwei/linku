package com.tuoshecx.server.wx.pay.client.impl;

import com.tuoshecx.server.wx.pay.client.request.TransferRequest;
import com.tuoshecx.server.wx.pay.client.response.TransferResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 微信转账客户端API请求
 *
 * @author WangWei
 */
class TransferClient extends WxPayClient<TransferRequest, TransferResponse> {

    TransferClient(RestTemplate restTemplate) {
        super(restTemplate, "transfers");
    }

    @Override
    protected String buildUri() {
        return "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
    }

    @Override
    protected TransferResponse buildResponse(Map<String, String> data) {
        return new TransferResponse(data);
    }
}
