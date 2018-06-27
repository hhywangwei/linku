package com.linku.server.wx.component.client.impl;

import com.linku.server.wx.component.client.ComponentHttpClient;
import com.linku.server.wx.component.client.request.ObtainAccessTokenRequest;
import com.linku.server.wx.component.client.response.ObtainAccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 *  实现获取微信第三方平台Access token
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
class ObtainAccessTokenClient extends ComponentHttpClient<ObtainAccessTokenRequest, ObtainAccessTokenResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObtainAccessTokenClient.class);

    ObtainAccessTokenClient() {
        super("obtain_access_token");
    }

    @Override
    protected Mono<byte[]> doRequest(WebClient client, ObtainAccessTokenRequest obtainAccessTokenRequest) {
        byte[] body = buildBody(obtainAccessTokenRequest).getBytes(charsetUTF8);
        int contentLength = body.length;
        return client.post()
                .uri(buildUri(builder -> builder.path("/cgi-bin/component/api_component_token").build()))
                .contentLength(contentLength)
                .body(BodyInserters.fromObject(body))
                .retrieve()
                .bodyToMono(byte[].class);
    }

    private String buildBody(ObtainAccessTokenRequest request){
        String body = String.format("{\"component_appid\": \"%s\", \"component_appsecret\": \"%s\", \"component_verify_ticket\": \"%s\"}",
                request.getComponentAppid(), request.getComponentSecret(), request.getVerifyTicket());
        LOGGER.debug("Obtain component access token request body is {}", body);
        return body;
    }
    @Override
    protected ObtainAccessTokenResponse buildResponse(Map<String, Object> data) {
        return new ObtainAccessTokenResponse(data);
    }
}
