package com.tuoshecx.server.wx.component.client.impl;

import com.tuoshecx.server.wx.component.client.ComponentHttpClient;
import com.tuoshecx.server.wx.component.client.request.ObtainAuthorizerTokenRequest;
import com.tuoshecx.server.wx.component.client.response.ObtainAuthorizerTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

class ObtainAuthorizerTokenClient extends ComponentHttpClient<ObtainAuthorizerTokenRequest, ObtainAuthorizerTokenResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObtainAuthorizerTokenClient.class);

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
        String body = String.format("{\"component_appid\": \"%s\", " + "\"authorizer_appid\": \"%s\", \"authorizer_refresh_token\": \"%s\"}",
                request.getComponentAppid(), request.getAuthorizerAppid(),request.getAuthorizerRefreshToken());
        LOGGER.debug("Obtain authorizer token request body is {}", body);
        return body;
    }
    @Override
    protected ObtainAuthorizerTokenResponse buildResponse(Map<String, Object> data) {
        return new ObtainAuthorizerTokenResponse(data);
    }
}
