package com.tuoshecx.server.wx.pay.client.impl;

import com.tuoshecx.server.wx.pay.client.request.TransferRequest;
import com.tuoshecx.server.wx.pay.client.response.TransferResponse;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Map;

/**
 * 微信转账客户端API请求
 *
 * @author WangWei
 */
class TransferClient extends WxPayBaseClient<TransferRequest, TransferResponse> {

    TransferClient(ClientHttpConnector connector) {
        super(connector, "/mmpaymkttransfers/promotion/transfers");
    }

    @Override
    protected byte[] doRequest(WebClient client, TransferRequest request) {
        return client.post()
                .uri("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers")
                .body(BodyInserters.fromObject(request.body()))
                .retrieve()
                .bodyToMono(byte[].class)
                .block(Duration.ofSeconds(30));
    }

    @Override
    protected TransferResponse buildResponse(Map<String, String> data) {
        return new TransferResponse(data);
    }
}
