package com.tuoshecx.server.wx.small.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.small.client.request.WxSmallRequest;
import com.tuoshecx.server.wx.small.client.response.WxSmallResponse;

import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 发布已通过审核的小程序（仅供第三方代小程序调用）
 *
 * author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class ProgramReleaseClient extends WxSmallClient<WxSmallRequest, WxSmallResponse> {

    ProgramReleaseClient(RestTemplate restTemplate, ObjectMapper objectMapper){
        super(restTemplate, objectMapper, "programRelease");
    }

    @Override
    protected String buildBody(WxSmallRequest request) {
        return "{}";
    }

    @Override
    protected String buildUri(WxSmallRequest request) {
        return "https://api.weixin.qq.com/wxa/release?access_token={token}";
    }

    @Override
    protected Object[] uriParams(WxSmallRequest request) {
        return new Object[]{request.getToken()};
    }

    @Override
    protected WxSmallResponse buildResponse(Map<String, Object> data) {
        return new WxSmallResponse(data);
    }
}
