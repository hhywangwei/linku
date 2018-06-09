package com.linku.server.wx.gzh.client.impl;

import com.linku.server.wx.configure.properties.WxProperties;
import com.linku.server.wx.gzh.client.GzhHttpClient;
import com.linku.server.wx.gzh.client.request.ObtainCredentialRequest;
import com.linku.server.wx.gzh.client.response.ObtainCredentialResponse;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 获取微信公众平台访问凭证
 * 
 * @author WangWei
 */
class ObtainCredentialHttpClient extends GzhHttpClient<ObtainCredentialRequest, ObtainCredentialResponse> {

    ObtainCredentialHttpClient(WxProperties properties, String apiName) {
        super(properties, apiName);
    }

    @Override
    protected Mono<byte[]> doRequest(WebClient client, WxProperties p, ObtainCredentialRequest obtainCredentialRequest) {
        return client.get()
                .uri(buildUri(builder -> builder.path("/cgi-bin/token")
                        .queryParam("appid", p.getAppid())
                        .queryParam("secret", p.getSecret())
                        .queryParam("grant_type", "client_credential")
                        .build()
                ))
                .retrieve()
                .bodyToMono(byte[].class);
    }

    @Override
    protected ObtainCredentialResponse buildResponse(Map<String, Object> data) {
        return new ObtainCredentialResponse(data);
    }
}
