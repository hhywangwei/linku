package com.linku.server.wx.small.client.impl;

import com.linku.server.wx.small.client.request.SendCustomMsgRequest;
import com.linku.server.wx.small.client.response.WxSmallResponse;
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
import java.util.stream.Collectors;

/**
 * 发生微信客服消息
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
class SendCustomMsgClient extends WxSmallClient<SendCustomMsgRequest, WxSmallResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendCustomMsgClient.class);
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    SendCustomMsgClient() {
        super("sendCustomMsg");
    }

    @Override
    protected Mono<byte[]> doRequest(WebClient client, SendCustomMsgRequest request) {
        String body = body(request);
        long contentLength = body.getBytes(UTF_8_CHARSET).length;

        LOGGER.debug("Request send template message is {}, content length {}", body, contentLength);

        return client.post()
                .uri(e -> buildUrl(e, request))
                .headers(e -> e.setContentLength(contentLength))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(body))
                .retrieve()
                .bodyToMono(byte[].class);
    }

    private URI buildUrl(UriBuilder builder, SendCustomMsgRequest request){
        return builder
                .scheme("https")
                .host("api.weixin.qq.com")
                .path("/cgi-bin/message/custom/send")
                .queryParam("access_token", request.getToken())
                .build();
    }

    @Override
    protected WxSmallResponse buildResponse(Map<String, Object> data) {
        return new WxSmallResponse(data);
    }

    private String body(SendCustomMsgRequest request){
        StringBuilder builder = new StringBuilder(200);

        builder.append("{\"touser\":\"").append(request.getOpenid()).append("\",");
        builder.append("\"msgtype\":\"").append(request.getMsgType()).append("\",");
        builder.append("\"").append(request.getMsgType()).append("\":\"").append("{");
        builder.append(buildContent(request.getContent()));
        builder.append("}}");
        return builder.toString();
    }

    private String buildContent(Map<String, String> content){
        return content.entrySet().stream()
                .map(k -> ("\"" + k.getKey() + "\":" + "\"" + k.getValue() + "\""))
                .collect(Collectors.joining(","));
    }
}