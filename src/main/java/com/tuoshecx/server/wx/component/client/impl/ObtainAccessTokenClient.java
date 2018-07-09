package com.tuoshecx.server.wx.component.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.component.client.request.ObtainAccessTokenRequest;
import com.tuoshecx.server.wx.component.client.response.ObtainAccessTokenResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 *  实现获取微信第三方平台Access token
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
class ObtainAccessTokenClient extends WxComponentClient<ObtainAccessTokenRequest, ObtainAccessTokenResponse> {

    ObtainAccessTokenClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper, "obtainAccessToken");
    }

    @Override
    protected String buildUri(ObtainAccessTokenRequest obtainAccessTokenRequest) {
        return "https://api.weixin.qq.com/cgi-bin/component/api_component_token";
    }

    @Override
    protected Object[] uriParams(ObtainAccessTokenRequest obtainAccessTokenRequest) {
        return new Object[0];
    }

    @Override
    protected String buildBody(ObtainAccessTokenRequest request){
        return String.format("{\"component_appid\": \"%s\", \"component_appsecret\": \"%s\", \"component_verify_ticket\": \"%s\"}",
                request.getComponentAppid(), request.getComponentSecret(), request.getVerifyTicket());
    }

    @Override
    protected ObtainAccessTokenResponse buildResponse(Map<String, Object> data) {
        return new ObtainAccessTokenResponse(data);
    }
}
