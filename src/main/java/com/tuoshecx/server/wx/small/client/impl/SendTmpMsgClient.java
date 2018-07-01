package com.tuoshecx.server.wx.small.client.impl;

import com.tuoshecx.server.wx.small.client.request.SendTmpMsgRequest;
import com.tuoshecx.server.wx.small.client.response.WxSmallResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 发送微信小程序模板消息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class SendTmpMsgClient extends WxSmallClient<SendTmpMsgRequest, WxSmallResponse> {
    private static final Logger logger = LoggerFactory.getLogger(SendTmpMsgClient.class);
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    SendTmpMsgClient() {
        super( "sendTemplateMessage");
    }

    @Override
    protected Mono<byte[]> doRequest(WebClient client, SendTmpMsgRequest request) {
        String body = body(request);
        long contentLength = body.getBytes(UTF_8_CHARSET).length;

        logger.debug("Request send template message is {}, content length {}", body, contentLength);

        return client.post()
                .uri(e -> buildUrl(e, request))
                .headers(e -> e.setContentLength(contentLength))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(body))
                .retrieve()
                .bodyToMono(byte[].class);
    }

    private URI buildUrl(UriBuilder builder, SendTmpMsgRequest request){
        return builder
                .scheme("https")
                .host("api.weixin.qq.com")
                .path("/cgi-bin/message/wxopen/template/send")
                .queryParam("access_token", request.getToken())
                .build();
    }

    @Override
    protected WxSmallResponse buildResponse(Map<String, Object> data) {
        return new WxSmallResponse(data);
    }

    private String body(SendTmpMsgRequest request){
        StringBuilder builder = new StringBuilder(200);

        builder.append("{\"touser\":\"").append(request.getOpenid()).append("\",");
        builder.append("\"template_id\":\"").append(request.getTemplateId()).append("\",");
        builder.append("\"form_id\":\"").append(request.getFormId()).append("\",");
        if(StringUtils.isNotBlank(request.getColor())){
            builder.append("\"color\":\"").append(request.getColor()).append("\",");
        }
        if(StringUtils.isNotBlank(request.getEmphasisKeyword())){
            builder.append("\"emphasis_keyword\":\"").append(request.getEmphasisKeyword()).append("\",");
        }
        if(StringUtils.isNotBlank(request.getPage())){
            builder.append("\"page\":\"").append(request.getPage()).append("\",");
        }
        builder.append("\"data\":{").append(data(request.getItems())).append("}}");

        return builder.toString();
    }

    private String data(List<SendTmpMsgRequest.Item> items){
        return items.stream().
                map(e -> String.format("\"%s\": {\"value\":\"%s\", \"color\":\"%s\"}", e.getKey(), e.getValue(), e.getColor())).
                collect(Collectors.joining(","));
    }
}
