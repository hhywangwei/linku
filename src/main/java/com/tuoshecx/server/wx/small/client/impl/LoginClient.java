package com.tuoshecx.server.wx.small.client.impl;

import com.tuoshecx.server.wx.small.client.request.LoginRequest;
import com.tuoshecx.server.wx.small.client.response.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;

/**
 * 小程登陆程序
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class LoginClient extends WxSmallClient<LoginRequest, LoginResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginClient.class);

    LoginClient(){
        super("loginClient");
    }

    @Override
    protected Mono<byte[]> doRequest(WebClient client, LoginRequest request) {
        return client.get()
                .uri(builder ->buildURI(builder, request))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .bodyToMono(byte[].class);
    }

    private URI buildURI(UriBuilder builder, LoginRequest request){
        URI uri = builder.scheme("https")
                .host("api.weixin.qq.com")
                .path("/sns/component/jscode2session")
                .queryParam("appid", request.getAppid() )
                .queryParam("js_code", request.getCode())
                .queryParam("grant_type", "authorization_code")
                .queryParam("component_appid", request.getComponentAppid())
                .queryParam("component_access_token", request.getComponentToken())
                .build();

        LOGGER.debug("Wx small login uri is {}", uri.toString());

        return uri;
    }

    @Override
    protected LoginResponse buildResponse(Map<String, Object> data) {
        return new LoginResponse(data);
    }
}
