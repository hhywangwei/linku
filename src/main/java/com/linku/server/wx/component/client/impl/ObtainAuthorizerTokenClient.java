package com.linku.server.wx.component.client.impl;

import com.linku.server.wx.component.client.ComponentHttpClient;
import com.linku.server.wx.component.client.request.ObtainAuthorizerTokenRequest;
import com.linku.server.wx.component.client.response.ObtainAccessTokenResponse;
import com.linku.server.wx.component.client.response.ObtainAuthorizerTokenResponse;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

class ObtainAuthorizerTokenClient extends ComponentHttpClient<ObtainAuthorizerTokenRequest, ObtainAuthorizerTokenResponse> {

    ObtainAuthorizerTokenClient() {
        super("obtain_authorizer_token");
    }

    @Override
    protected Mono<byte[]> doRequest(WebClient client, ObtainAuthorizerTokenRequest request) {
        byte[] body = buildBody(request).getBytes(charsetUTF8);

        return client.post()
                .uri(buildUri(builder -> builder.path("/cgi-bin/component/api_authorizer_token")
                        .queryParam("component_access_token", request.getToken()).build()))
                .contentLength(body.length)
                .body(BodyInserters.fromObject(body))
                .retrieve()
                .bodyToMono(byte[].class);
    }

    private String buildBody(ObtainAuthorizerTokenRequest request){
        return String.format("{\"component_access_token\": \"%s\", " + "\"authorizer_appid\": \"%s\", \"authorizer_refresh_token\": \"%s\"}",
                request.getComponentAppid(), request.getAuthorizerAppid(),request.getAuthorizerRefreshToken());
    }
    @Override
    protected ObtainAuthorizerTokenResponse buildResponse(Map<String, Object> data) {
        return new ObtainAuthorizerTokenResponse(data);
    }
}
