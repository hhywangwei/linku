package com.tuoshecx.server.wx.small.client.impl;

import com.tuoshecx.server.wx.small.client.request.ProgramCommitRequest;
import com.tuoshecx.server.wx.small.client.response.WxSmallResponse;
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
 *  为授权的小程序帐号上传小程序代码
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class ProgramCommitClient extends WxSmallClient<ProgramCommitRequest, WxSmallResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramCommitClient.class);
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    ProgramCommitClient() {
        super("programCommit");
    }

    @Override
    protected Mono<byte[]> doRequest(WebClient client, ProgramCommitRequest request) {
        String body = body(request);
        LOGGER.debug("Request program commit body is {}", body);

        byte[] bytes = body.getBytes(UTF_8_CHARSET);

        return client.post()
                .uri(e -> buildUrl(e, request))
                .headers(e -> e.setContentLength(bytes.length))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(bytes))
                .retrieve()
                .bodyToMono(byte[].class);
    }

    private URI buildUrl(UriBuilder builder, ProgramCommitRequest request){
        return builder
                .scheme("https")
                .host("api.weixin.qq.com")
                .path("/wxa/commit")
                .queryParam("access_token", request.getToken())
                .build();
    }

    private String body(ProgramCommitRequest request){
        StringBuilder builder = new StringBuilder(200);

        builder.append("{\"template_id\":").append(request.getTemplateId()).append(",");
        builder.append("\"ext_json\":\"").append(request.getExtJson()).append("\",");
        builder.append("\"user_version\":\"").append(request.getUserVersion()).append("\",");
        builder.append("\"user_desc\":\"").append(request.getUserDesc()).append("\"}");
        return builder.toString();
    }

    @Override
    protected WxSmallResponse buildResponse(Map<String, Object> data) {
        return new WxSmallResponse(data);
    }
}
