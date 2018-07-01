package com.tuoshecx.server.tencent.map.client.impl;

import com.tuoshecx.server.tencent.map.client.request.IpRequest;
import com.tuoshecx.server.tencent.map.client.response.IpResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Map;

class IpClient extends BaseMapClient<IpRequest, IpResponse> {

    IpClient() {
        super("ip");
    }

    @Override
    protected byte[] doRequest(WebClient client, IpRequest ipRequest) {
        return new byte[0];
    }

    private URI buildURI(UriBuilder builder, IpRequest request){
        builder.scheme("http")
                .host("apis.map.qq.com")
                .path("/ws/location/v1/ip")
                .queryParam("key", request.getKey())
                .queryParam("output", request.getOutput());

        if(StringUtils.isNotBlank(request.getIp())){
            builder.queryParam("ip", request.getIp());
        }

        return builder.build();
    }

    @Override
    protected IpResponse buildResponse(Map<String, Object> data) {
        return new IpResponse(data);
    }
}
