package com.tuoshecx.server.wx.small.client.impl;

import com.tuoshecx.server.wx.small.client.request.MessageTemplateAddRequest;
import com.tuoshecx.server.wx.small.client.response.MessageTemplateAddResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 添加微信消息模板
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class MessageTemplateAddClient extends WxSmallClient<MessageTemplateAddRequest, MessageTemplateAddResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageTemplateAddClient.class);
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    MessageTemplateAddClient() {
        super("messageTemplateAdd");
    }

    @Override
    protected Mono<byte[]> doRequest(WebClient client, MessageTemplateAddRequest request) {
        String body = body(request);
        LOGGER.debug("Request message template add body is {}", body);

        byte[] bytes = body.getBytes(UTF_8_CHARSET);

        return client.post()
                .uri(builder -> buildUrl(builder, request))
                .headers(e -> e.setContentLength(bytes.length))
                .body(BodyInserters.fromObject(bytes))
                .retrieve()
                .bodyToMono(byte[].class);
    }

    private URI buildUrl(UriBuilder builder, MessageTemplateAddRequest request){
        return builder
                .scheme("https")
                .host("api.weixin.qq.com")
                .path("/cgi-bin/wxopen/template/add")
                .queryParam("access_token", request.getToken())
                .build();
    }

    private String body(MessageTemplateAddRequest request){
        StringBuilder builder = new StringBuilder(200);

        builder.append("{\"id\":\"").append(request.getId()).append("\",");
        builder.append("\"keyword_id_list\":[");
        builder.append(request.getKeywordIds().stream().map(String::valueOf).collect(Collectors.joining(",")));
        builder.append("]}");
        return builder.toString();
    }

    @Override
    protected MessageTemplateAddResponse buildResponse(Map<String, Object> data) {
        return new MessageTemplateAddResponse(data);
    }
}
