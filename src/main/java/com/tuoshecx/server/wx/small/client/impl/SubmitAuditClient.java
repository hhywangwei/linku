package com.tuoshecx.server.wx.small.client.impl;

import com.tuoshecx.server.wx.small.client.request.SubmitAuditRequest;
import com.tuoshecx.server.wx.small.client.response.SubmitAuditResponse;
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
import java.util.stream.Collectors;

/**
 * 将第三方提交的代码包提交审核（仅供第三方开发者代小程序调用）
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class SubmitAuditClient extends WxSmallClient<SubmitAuditRequest, SubmitAuditResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubmitAuditClient.class);
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    SubmitAuditClient() {
        super("submitAuditClient");
    }

    @Override
    protected Mono<byte[]> doRequest(WebClient client, SubmitAuditRequest request) {
        String body = body(request);
        LOGGER.debug("Request submit audit body is {}", body);

        byte[] bytes = body.getBytes(UTF_8_CHARSET);

        return client.post()
                .uri(e -> buildUrl(e, request))
                .headers(e -> e.setContentLength(bytes.length))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(bytes))
                .retrieve()
                .bodyToMono(byte[].class);
    }

    private URI buildUrl(UriBuilder builder, SubmitAuditRequest request){
        return builder
                .scheme("https")
                .host("api.weixin.qq.com")
                .path("/wxa/submit_audit")
                .queryParam("access_token", request.getToken())
                .build();
    }

    private String body(SubmitAuditRequest request){
        StringBuilder builder = new StringBuilder(200);
        builder.append("{");
        builder.append("\"item_list\":[");
        builder.append(request.getItems().stream().map(this::toItem).collect(Collectors.joining(",")));
        builder.append("]}");
        return builder.toString();
    }

    private String toItem(SubmitAuditRequest.SubmitAuditItem item){
        StringBuilder builder = new StringBuilder(50);

        builder.append("{\"address\":\"").append(item.getAddress()).append("\",");
        builder.append("\"tag\":\"").append(item.getTag()).append("\",");
        builder.append("\"first_class\":\"").append(item.getFirstClass()).append("\",");
        builder.append("\"first_id\":").append(item.getFirstId()).append(",");
        if(StringUtils.isNotBlank(item.getSecondClass())){
            builder.append("\"second_class\":\"").append(item.getSecondClass()).append("\",");
            builder.append("\"second_id\":").append(item.getSecondId()).append(",");
        }
        if(StringUtils.isNotBlank(item.getThirdClass())){
            builder.append("\"third_class\":\"").append(item.getThirdClass()).append("\",");
            builder.append("\"third_id\":").append(item.getThirdId()).append(",");
        }
        builder.append("\"title\":\"").append(item.getTitle()).append("\"}");

        return builder.toString();
    }
    @Override
    protected SubmitAuditResponse buildResponse(Map<String, Object> data) {
        return new SubmitAuditResponse(data);
    }
}
