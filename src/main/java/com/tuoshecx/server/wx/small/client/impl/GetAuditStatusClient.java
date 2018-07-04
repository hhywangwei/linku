package com.tuoshecx.server.wx.small.client.impl;

import com.tuoshecx.server.wx.small.client.request.GetAuditStatusRequest;
import com.tuoshecx.server.wx.small.client.response.GetAuditStatusResponse;
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
 * 查询某个指定版本的审核状态（仅供第三方代小程序调用）
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class GetAuditStatusClient extends WxSmallClient<GetAuditStatusRequest, GetAuditStatusResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetAuditStatusClient.class);
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    GetAuditStatusClient(){
        super("getAuditStatus");
    }

    @Override
    protected Mono<byte[]> doRequest(WebClient client, GetAuditStatusRequest request) {
        String body = buildBody(request);
        LOGGER.debug("Request get audit status body is {}", body);
        byte[] bytes = body.getBytes(UTF_8_CHARSET);

        return client.post()
                .uri(e -> buildUrl(e, request))
                .headers(e -> e.setContentLength(bytes.length))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(bytes))
                .retrieve()
                .bodyToMono(byte[].class);
    }

    private URI buildUrl(UriBuilder builder, GetAuditStatusRequest request){
        return builder
                .scheme("https")
                .host("api.weixin.qq.com")
                .path("/wxa/get_auditstatus")
                .queryParam("access_token", request.getToken())
                .build();
    }

    private String buildBody(GetAuditStatusRequest request){
        return String.format("{\"auditid\":\"%s\"}", request.getAuditId());
    }

    @Override
    protected GetAuditStatusResponse buildResponse(Map<String, Object> data) {
        return new GetAuditStatusResponse(data);
    }
}
