package com.tuoshecx.server.tencent.map.client.impl;

import com.tuoshecx.server.tencent.map.client.request.DistrictRequest;
import com.tuoshecx.server.tencent.map.client.response.DistrictResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.time.Duration;
import java.util.Map;

class DistrictClient extends BaseMapClient<DistrictRequest, DistrictResponse> {

    DistrictClient() {
        super("District");
    }

    @Override
    protected byte[] doRequest(WebClient client, DistrictRequest request) {
        return client.get().uri(builder -> buildURI(builder, request))
                .retrieve()
                .bodyToMono(byte[].class)
                .block(Duration.ofSeconds(30));
    }

    private URI buildURI(UriBuilder builder, DistrictRequest request){

        builder.scheme("http")
                .host("apis.map.qq.com")
                .path(path(request))
                .queryParam("key", request.getKey())
                .queryParam("output", request.getOutput());

        if(StringUtils.isNotBlank(request.getId())){
            builder.queryParam("id", request.getId());
        }
        if(StringUtils.isNotBlank(request.getKeyword())){
            builder.queryParam("keyword", request.getKeyword());
        }
        return builder.build();
    }

    private String path(DistrictRequest request){
        if(StringUtils.isNotBlank(request.getKeyword())){
            return "/ws/district/v1/search";
        }
        return "/ws/district/v1/getchildren";
    }

    @Override
    protected DistrictResponse buildResponse(Map<String, Object> data) {
        return new DistrictResponse(data);
    }
}
