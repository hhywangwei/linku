package com.tuoshecx.server.wx.small.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.small.client.request.UpdateDomainRequest;
import com.tuoshecx.server.wx.small.client.response.WxSmallResponse;

import org.springframework.web.client.RestTemplate;

import java.util.Map;

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

