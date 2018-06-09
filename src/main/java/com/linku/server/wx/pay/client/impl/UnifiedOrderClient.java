package com.linku.server.wx.pay.client.impl;

import com.linku.server.wx.pay.client.request.UnifiedOrderRequest;
import com.linku.server.wx.pay.client.response.UnifiedOrderResponse;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Map;

/**
 * 微信支付统一下单API请求处理
 *
 * @author WangWei
 */
class UnifiedOrderClient extends WxPayBaseClient<UnifiedOrderRequest, UnifiedOrderResponse> {

    UnifiedOrderClient(ClientHttpConnector connector) {
        super(connector, "/pay/unifiedorder");
    }

    @Override
    protected byte[] doRequest(WebClient client, UnifiedOrderRequest request) {
        return client.post()
                .uri("https://api.mch.weixin.qq.com/pay/unifiedorder")
                .body(BodyInserters.fromObject(request.body()))
                .retrieve()
                .bodyToMono(byte[].class)
                .block(Duration.ofSeconds(30));
    }

    @Override
    protected UnifiedOrderResponse buildResponse(Map<String, String> data) {
        return new UnifiedOrderResponse(data);
    }

}
