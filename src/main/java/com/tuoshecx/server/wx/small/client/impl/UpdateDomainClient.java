package com.tuoshecx.server.wx.small.client.impl;

import com.tuoshecx.server.wx.small.client.request.UpdateDomainRequest;
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
 * 设置小程序服务器域名客户端
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class UpdateDomainClient extends WxSmallClient<UpdateDomainRequest, WxSmallResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateDomainClient.class);
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    UpdateDomainClient() {
        super("updateDomain");
    }

    @Override
    protected Mono<byte[]> doRequest(WebClient client, UpdateDomainRequest request) {
        String body = body(request);
        LOGGER.debug("Request Update domain body is {}", body);

        byte[] bytes = body.getBytes(UTF_8_CHARSET);

        return client.post()
                .uri(e -> buildUrl(e, request))
                .headers(e -> e.setContentLength(bytes.length))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(bytes))
                .retrieve()
                .bodyToMono(byte[].class);
    }

    private URI buildUrl(UriBuilder builder, UpdateDomainRequest request){
        return builder
                .scheme("https")
                .host("api.weixin.qq.com")
                .path("/wxa/modify_domain")
                .queryParam("access_token", request.getToken())
                .build();
    }

    private String body(UpdateDomainRequest request){
        StringBuilder builder = new StringBuilder(200);

        builder.append("{\"action\":\"").append(request.getAction()).append("\",");
        builder.append("\"requestdomain\":[\"").append(request.getRequestDomain()).append("\",\"").append(request.getRequestDomain()).append("\"],");
        builder.append("\"wsrequestdomain\":[\"").append(request.getWsRequestDomain()).append("\",\"").append(request.getWsRequestDomain()).append("\"],");
        builder.append("\"uploaddomain\":[\"").append(request.getUploadDomain()).append("\",\"").append(request.getUploadDomain()).append("\"],");
        builder.append("\"downloaddomain\":[\"").append(request.getDownloadDomain()).append("\",\"").append(request.getDownloadDomain()).append("\"]}");

        return builder.toString();
    }

    @Override
    protected WxSmallResponse buildResponse(Map<String, Object> data) {
        return new WxSmallResponse(data);
    }
}

