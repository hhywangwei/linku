package com.tuoshecx.server.wx.small.client.impl;

import com.tuoshecx.server.wx.small.client.request.SetWebViewDomainRequest;
import com.tuoshecx.server.wx.small.client.response.WxSmallResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * 设置小程序业务域名（仅供第三方代小程序调用）
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class SetWebViewDomainClinet extends WxSmallClient<SetWebViewDomainRequest, WxSmallResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SetWebViewDomainClinet.class);
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    SetWebViewDomainClinet() {
        super("setWebViewDomain");
    }

    @Override
    protected Mono<byte[]> doRequest(WebClient client, SetWebViewDomainRequest request) {
        String body = body(request);
        LOGGER.debug("Request set web view domain body is {}", body);

        byte[] bytes = body.getBytes(UTF_8_CHARSET);

        return client.post()
                .uri(e -> buildUrl(e, request))
                .headers(e -> e.setContentLength(bytes.length))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(bytes))
                .retrieve()
                .bodyToMono(byte[].class);
    }

    private URI buildUrl(UriBuilder builder, SetWebViewDomainRequest request){
        return builder
                .scheme("https")
                .host("api.weixin.qq.com")
                .path("/wxa/setwebviewdomain")
                .queryParam("access_token", request.getToken())
                .build();
    }

    private String body(SetWebViewDomainRequest request){
        StringBuilder builder = new StringBuilder(50);

        builder.append("{\"action\":\"").append(request.getAction()).append("\",");
        builder.append("\"webviewdomain\":[\"").append(request.getWebViewDomain()).append("\",\"").append(request.getWebViewDomain()).append("\"]}");

        return builder.toString();
    }

    @Override
    protected WxSmallResponse buildResponse(Map<String, Object> data) {
        return null;
    }
}
