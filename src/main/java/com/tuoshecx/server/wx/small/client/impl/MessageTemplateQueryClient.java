package com.tuoshecx.server.wx.small.client.impl;

import com.tuoshecx.server.wx.small.client.request.MessageTemplateQueryRequest;
import com.tuoshecx.server.wx.small.client.response.MessageTemplateQueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * 获取帐号下已存在的模板列表
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
 class MessageTemplateQueryClient extends WxSmallClient<MessageTemplateQueryRequest, MessageTemplateQueryResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageTemplateQueryClient.class);
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    MessageTemplateQueryClient() {
        super("messageTemplateQuery");
    }

    @Override
    protected Mono<byte[]> doRequest(WebClient client, MessageTemplateQueryRequest request) {
        String body = body(request);
        LOGGER.debug("Request message template query body is {}", body);

        byte[] bytes = body.getBytes(UTF_8_CHARSET);

        return client.post()
                .uri(builder -> buildUrl(builder, request))
                .headers(e -> e.setContentLength(bytes.length))
                .body(BodyInserters.fromObject(bytes))
                .retrieve()
                .bodyToMono(byte[].class);
    }

    private URI buildUrl(UriBuilder builder, MessageTemplateQueryRequest request){
        return builder
                .scheme("https")
                .host("api.weixin.qq.com")
                .path("/cgi-bin/wxopen/template/list")
                .queryParam("access_token", request.getToken())
                .build();
    }

    private String body(MessageTemplateQueryRequest request){
        StringBuilder builder = new StringBuilder(200);

        builder.append("{\"offset\":").append(request.getOffset()).append(",");
        builder.append("\"count\":").append(request.getCount()).append("}");
        return builder.toString();
    }

    @Override
    protected MessageTemplateQueryResponse buildResponse(Map<String, Object> data) {
        return new MessageTemplateQueryResponse(data);
    }
}
