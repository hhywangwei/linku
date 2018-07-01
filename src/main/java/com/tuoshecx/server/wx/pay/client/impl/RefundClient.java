package com.tuoshecx.server.wx.pay.client.impl;

import com.tuoshecx.server.wx.pay.client.request.RefundRequest;
import com.tuoshecx.server.wx.pay.client.response.RefundResponse;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Map;

/**
 * 实现微信退款客户端API调用
 *
 * @author WangWei
 */
class RefundClient extends WxPayBaseClient<RefundRequest, RefundResponse> {

    RefundClient(ClientHttpConnector connector) {
        super(connector, "/secapi/pay/refund");
    }

    @Override
    protected RefundResponse buildResponse(Map<String, String> data) {
        return new RefundResponse(data);
    }

    @Override
    protected byte[] doRequest(WebClient client, RefundRequest request) {
        return client.post()
                .uri("https://api.mch.weixin.qq.com/secapi/pay/refund")
                .body(BodyInserters.fromObject(request.body()))
                .retrieve()
                .bodyToMono(byte[].class)
                .block(Duration.ofSeconds(30));
    }
}
