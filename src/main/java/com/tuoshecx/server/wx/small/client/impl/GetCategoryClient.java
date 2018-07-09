package com.tuoshecx.server.wx.small.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.small.client.request.LoginRequest;
import com.tuoshecx.server.wx.small.client.request.WxSmallRequest;
import com.tuoshecx.server.wx.small.client.response.GetCategoryResponse;

import org.springframework.web.client.RestTemplate;

import java.util.Map;


class GetCategoryClient extends WxSmallClient <WxSmallRequest, GetCategoryResponse> {
    private final RestTemplate restTemplate;

    GetCategoryClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper, "getCategory");
        this.restTemplate = restTemplate;
    }

    @Override
    public GetCategoryResponse request(WxSmallRequest request) {
        return doResponse(restTemplate.getForEntity(buildUri(request), byte[].class, uriParams(request)));
    }

    @Override
    protected String buildUri(WxSmallRequest wxSmallRequest) {
        return "https://api.weixin.qq.com/wxa/get_category?access_token={token}";
    }

    @Override
    protected Object[] uriParams(WxSmallRequest request) {
        return new Object[]{request.getToken()};
    }

    @Override
    protected String buildBody(WxSmallRequest wxSmallRequest) {
        //not instance
        return null;
    }

    @Override
    protected GetCategoryResponse buildResponse(Map<String, Object> data) {
        return new GetCategoryResponse(data);
    }
}
