package com.tuoshecx.server.wx.component.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.component.client.request.ObtainAuthorizerTokenRequest;
import com.tuoshecx.server.wx.component.client.response.ObtainAuthorizerTokenResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 获取托管公众号Token
 *
 * @author <a href="mailto:hhywangwei@gmail.com>WangWei</a>
 */
class ObtainAuthorizerTokenClient extends WxComponentClient<ObtainAuthorizerTokenRequest, ObtainAuthorizerTokenResponse> {

    ObtainAuthorizerTokenClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper, "obtainAuthorizerToken");
    }

    @Override
    protected String buildUri(ObtainAuthorizerTokenRequest request) {
        return "https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token={token}";
    }

    @Override
    protected Object[] uriParams(ObtainAuthorizerTokenRequest request) {
        return new Object[]{request.getToken()};
    }

    @Override
    protected String buildBody(ObtainAuthorizerTokenRequest request){
        return String.format("{\"component_appid\": \"%s\", " + "\"authorizer_appid\": \"%s\", \"authorizer_refresh_token\": \"%s\"}",
                request.getComponentAppid(), request.getAuthorizerAppid(),request.getAuthorizerRefreshToken());
    }
    @Override
    protected ObtainAuthorizerTokenResponse buildResponse(Map<String, Object> data) {
        return new ObtainAuthorizerTokenResponse(data);
    }
}
