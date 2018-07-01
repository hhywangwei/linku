package com.tuoshecx.server.wx.component.client.impl;

import com.tuoshecx.server.wx.component.client.ComponentHttpClient;
import com.tuoshecx.server.wx.component.client.request.ObtainAuthorizerInfoRequest;
import com.tuoshecx.server.wx.component.client.response.ObtainAuthorizerInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

class ObtainAuthorizerInfoClient extends ComponentHttpClient<ObtainAuthorizerInfoRequest, ObtainAuthorizerInfoResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObtainAuthorizerInfoClient.class);

    ObtainAuthorizerInfoClient() {
        super("obtain_authorizer_info");
    }

    @Override
    protected Mono<byte[]> doRequest(WebClient client, ObtainAuthorizerInfoRequest request) {
        byte[] body = buildBody(request).getBytes(charsetUTF8);

        return client.post()
                .uri(buildUri(builder -> builder.path("/cgi-bin/component/api_get_authorizer_info")
                        .queryParam("component_access_token", request.getToken())
                        .build()))
                .contentLength(body.length)
                .body(BodyInserters.fromObject(body))
                .retrieve()
                .bodyToMono(byte[].class);
    }

    private String buildBody(ObtainAuthorizerInfoRequest request){
        String body = String.format("{\"component_appid\": \"%s\", \"authorizer_appid\": \"%s\"}",
                request.getComponentAppid(), request.getAuthorizerAppid());
        LOGGER.debug("Obtain authorizer info request body is {}", body);
        return body;
    }

    @Override
    protected ObtainAuthorizerInfoResponse buildResponse(Map<String, Object> data) {
        return new ObtainAuthorizerInfoResponse(data);
    }
}
