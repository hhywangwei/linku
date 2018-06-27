package com.linku.server.wx.component.client.impl;

import com.linku.server.wx.component.client.ComponentHttpClient;
import com.linku.server.wx.component.client.request.ObtainPreAuthCodeRequest;
import com.linku.server.wx.component.client.response.ObtainPreAuthCodeResponse;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

class ObtainPreAuthCodeClient extends ComponentHttpClient<ObtainPreAuthCodeRequest, ObtainPreAuthCodeResponse> {

    ObtainPreAuthCodeClient() {
        super("obtain_pre_auth_code");
    }

    @Override
    protected Mono<byte[]> doRequest(WebClient client, ObtainPreAuthCodeRequest request) {
        byte[] body = buildBody(request).getBytes(charsetUTF8);
        return client.post()
                .uri(buildUri(builder -> builder.path("/cgi-bin/component/api_create_preauthcode")
                        .queryParam("component_access_token", request.getToken())
                        .build()))
                .contentLength(body.length)
                .body(BodyInserters.fromObject(body))
                .retrieve()
                .bodyToMono(byte[].class);
    }

    private String buildBody(ObtainPreAuthCodeRequest request){
        return String.format("{\"component_appid\":\"%s\"}", request.getComponentAppid());
    }
    @Override
    protected ObtainPreAuthCodeResponse buildResponse(Map<String, Object> data) {
        return new ObtainPreAuthCodeResponse(data);
    }
}
