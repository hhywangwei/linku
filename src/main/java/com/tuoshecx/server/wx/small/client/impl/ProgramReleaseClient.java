package com.tuoshecx.server.wx.small.client.impl;

import com.tuoshecx.server.wx.small.client.request.WxSmallRequest;
import com.tuoshecx.server.wx.small.client.response.WxSmallResponse;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * 发布已通过审核的小程序（仅供第三方代小程序调用）
 *
 * author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class ProgramReleaseClient extends WxSmallClient<WxSmallRequest, WxSmallResponse> {
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    ProgramReleaseClient(){
        super("programRelease");
    }

    @Override
    protected Mono<byte[]> doRequest(WebClient client, WxSmallRequest request) {
        String body = "{}";

        byte[] bytes = body.getBytes(UTF_8_CHARSET);

        return client.post()
                .uri(e -> buildUrl(e, request))
                .headers(e -> e.setContentLength(bytes.length))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(bytes))
                .retrieve()
                .bodyToMono(byte[].class);
    }

    private URI buildUrl(UriBuilder builder, WxSmallRequest request){
        return builder
                .scheme("https")
                .host("api.weixin.qq.com")
                .path("/wxa/release")
                .queryParam("access_token", request.getToken())
                .build();
    }

    @Override
    protected WxSmallResponse buildResponse(Map<String, Object> data) {
        return new WxSmallResponse(data);
    }
}
