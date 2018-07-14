package com.tuoshecx.server.wx.small.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.small.client.request.UpdateDomainRequest;
import com.tuoshecx.server.wx.small.client.response.WxSmallResponse;

import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 设置小程序服务器域名客户端
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class UpdateDomainClient extends WxSmallClient<UpdateDomainRequest, WxSmallResponse> {

    UpdateDomainClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper, "updateDomain");
    }

    @Override
    protected String buildUri(UpdateDomainRequest request) {
        return "https://api.weixin.qq.com/wxa/modify_domain?access_token={token}";
    }

    @Override
    protected Object[] uriParams(UpdateDomainRequest request) {
        return new Object[]{request.getToken()};
    }

    @Override
    protected String buildBody(UpdateDomainRequest request){
        StringBuilder builder = new StringBuilder(200);

        builder.append("{\"action\":\"").append(request.getAction()).append("\",");
        builder.append("\"requestdomain\":").append(buildArray(request.getRequestDomain())).append(",");
        builder.append("\"wsrequestdomain\":").append(buildArray(request.getWsRequestDomain())).append(",");
        builder.append("\"uploaddomain\":").append(buildArray(request.getUploadDomain())).append(",");
        builder.append("\"downloaddomain\":").append(buildArray(request.getDownloadDomain())).append("}");

        return builder.toString();
    }

    private String buildArray(String[] array){
        return "[" + Arrays.stream(array).map(e -> "\"" + e + "\"").collect(Collectors.joining(",")) + "]";
    }

    @Override
    protected WxSmallResponse buildResponse(Map<String, Object> data) {
        return new WxSmallResponse(data);
    }
}

