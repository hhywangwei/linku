package com.linku.server.wx.component.client.impl;

import com.linku.server.wx.component.client.ComponentHttpClient;
import com.linku.server.wx.component.client.request.ObtainQueryAuthRequest;
import com.linku.server.wx.component.client.response.ObtainQueryAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

class ObtainQueryAuthClient extends ComponentHttpClient<ObtainQueryAuthRequest, ObtainQueryAuthResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObtainQueryAuthClient.class);

    ObtainQueryAuthClient() {
        super("obtain_query_auth");
    }

    @Override
    protected Mono<byte[]> doRequest(WebClient client, ObtainQueryAuthRequest request) {
        byte[] body= buildBody(request).getBytes(charsetUTF8);

        return client.post()
                .uri(buildUri(builder -> builder.path("/cgi-bin/component/api_query_auth")
                        .queryParam("component_access_token", request.getToken()).build()))
                .contentLength(body.length)
                .body(BodyInserters.fromObject(body))
                .retrieve()
                .bodyToMono(byte[].class);
    }

    private String buildBody(ObtainQueryAuthRequest request){
        String body = String.format("{\"component_appid\": \"%s\", \"authorization_code\": \"%s\"}",
                request.getComponentAppid(), request.getAuthorizationCode());

        LOGGER.debug("Obtain query auth request body is {}", body);
        return body;
    }

    @Override
    protected ObtainQueryAuthResponse buildResponse(Map<String, Object> data) {
        return new ObtainQueryAuthResponse(data);
    }
}
