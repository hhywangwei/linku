package com.linku.server.wx.small.client.impl;

import com.linku.server.wx.configure.properties.WxProperties;
import com.linku.server.wx.small.client.response.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
/**
 * 小程登陆程序
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class LoginClient extends WxSmallClient<String, LoginResponse> {
    private static final Logger logger = LoggerFactory.getLogger(LoginClient.class);

    LoginClient(WxProperties properties){
        super(properties, "loginClient");
    }

    @Override
    protected Mono<byte[]> doRequest(WebClient client, WxProperties p, String t) {
        return client.get()
                .uri(builder -> builder
                        .scheme("https")
                        .host("api.weixin.qq.com")
                        .path("/sns/jscode2session")
                        .queryParam("appid", p.getAppid())
                        .queryParam("secret", p.getSecret())
                        .queryParam("js_code", t)
                        .queryParam("grant_type", "authorization_code")
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .bodyToMono(byte[].class);
    }

    @Override
    protected LoginResponse buildResponse(Map<String, Object> data) {
        return new LoginResponse(data);
    }
}
