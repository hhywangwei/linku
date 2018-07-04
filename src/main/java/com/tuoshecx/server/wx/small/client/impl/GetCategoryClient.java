package com.tuoshecx.server.wx.small.client.impl;

import com.tuoshecx.server.wx.small.client.request.WxSmallRequest;
import com.tuoshecx.server.wx.small.client.response.GetCategoryResponse;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;


class GetCategoryClient extends WxSmallClient <WxSmallRequest, GetCategoryResponse> {

    GetCategoryClient() {
        super("getCategory");
    }

    @Override
    protected Mono<byte[]> doRequest(WebClient client, WxSmallRequest request) {
        return client.get()
                .uri(builder -> buildUri(builder, request))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .bodyToMono(byte[].class);
    }

    private URI buildUri(UriBuilder builder, WxSmallRequest request){
        return builder.scheme("https")
                .host("api.weixin.qq.com")
                .path("/wxa/get_category")
                .queryParam("access_token", request.getToken())
                .build();
    }

    @Override
    protected GetCategoryResponse buildResponse(Map<String, Object> data) {
        return new GetCategoryResponse(data);
    }
}
