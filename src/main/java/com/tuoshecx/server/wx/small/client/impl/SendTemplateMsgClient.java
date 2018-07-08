package com.tuoshecx.server.wx.small.client.impl;

import com.tuoshecx.server.wx.small.client.request.SendTemplateMsgRequest;
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
import java.util.Map;

/**
 * 发送微信小程序模板消息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class SendTemplateMsgClient extends WxSmallClient<SendTemplateMsgRequest, WxSmallResponse> {
    private static final Logger logger = LoggerFactory.getLogger(SendTemplateMsgClient.class);
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    SendTemplateMsgClient() {
        super( "sendTemplateMessage");
    }

    @Override
    protected Mono<byte[]> doRequest(WebClient client, SendTemplateMsgRequest request) {
        String body = body(request);
        logger.debug("Request send template message is {}", body);

        byte[] bytes = body.getBytes(UTF_8_CHARSET);

        return client.post()
                .uri(e -> buildUrl(e, request))
                .headers(e -> e.setContentLength(bytes.length))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(bytes))
                .retrieve()
                .bodyToMono(byte[].class);
    }

    private URI buildUrl(UriBuilder builder, SendTemplateMsgRequest request){
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

    private String body(SendTemplateMsgRequest request){
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
        builder.append("\"data\":").append(request.getData()).append("}");

        return builder.toString();
    }
}
