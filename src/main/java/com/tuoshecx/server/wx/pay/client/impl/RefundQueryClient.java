package com.tuoshecx.server.wx.pay.client.impl;

import com.tuoshecx.server.wx.pay.client.request.RefundQueryRequest;
import com.tuoshecx.server.wx.pay.client.response.RefundQueryResponse;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Map;

/**
 * 微信查询退款客户端API处理
 *
 * @author WangWei
 */
class RefundQueryClient extends WxPayBaseClient<RefundQueryRequest, RefundQueryResponse> {

    RefundQueryClient(ClientHttpConnector connector) {
        super(connector, "/pay/refundquery");
    }

    @Override
    protected byte[] doRequest(WebClient client, RefundQueryRequest request) {
        return client.post()
                .uri("https://api.mch.weixin.qq.com/pay/refundquery")
                .body(BodyInserters.fromObject(request.body()))
                .retrieve()
                .bodyToMono(byte[].class)
                .block(Duration.ofSeconds(30));
    }

    @Override
    protected RefundQueryResponse buildResponse(Map<String, String> data) {
        return new RefundQueryResponse(data);
    }
}
