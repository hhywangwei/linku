package com.tuoshecx.server.wx.small.client.impl;

import com.tuoshecx.server.wx.small.client.request.MessageTemplateDelRequest;
import com.tuoshecx.server.wx.small.client.request.ProgramCommitRequest;
import com.tuoshecx.server.wx.small.client.response.WxSmallResponse;
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
 *  删除微信消息模板
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class MessageTemplateDelClient extends WxSmallClient<MessageTemplateDelRequest, WxSmallResponse>{
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageTemplateDelClient.class);
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    MessageTemplateDelClient(){
        super("messageTemplateDel");
    }

    @Override
    protected Mono<byte[]> doRequest(WebClient client, MessageTemplateDelRequest request) {
        String body = buildBody(request);
        LOGGER.debug("Request message template del body is {}", body);

        byte[] bytes = body.getBytes(UTF_8_CHARSET);

        return client.post()
                .uri(builder -> buildUrl(builder, request))
                .headers(e -> e.setContentLength(bytes.length))
                .body(BodyInserters.fromObject(bytes))
                .retrieve()
                .bodyToMono(byte[].class);
    }

    private URI buildUrl(UriBuilder builder, MessageTemplateDelRequest request){
        return builder
                .scheme("https")
                .host("api.weixin.qq.com")
                .path("/cgi-bin/wxopen/template/del")
                .queryParam("access_token", request.getToken())
                .build();
    }

    private String buildBody(MessageTemplateDelRequest request){
        return String.format("{\"template_id\": \"%s\"}", request.getTemplateId());
    }

    @Override
    protected WxSmallResponse buildResponse(Map<String, Object> data) {
        return new WxSmallResponse(data);
    }
}
