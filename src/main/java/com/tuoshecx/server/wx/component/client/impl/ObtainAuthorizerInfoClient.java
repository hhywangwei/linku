package com.tuoshecx.server.wx.component.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.component.client.request.ObtainAuthorizerInfoRequest;
import com.tuoshecx.server.wx.component.client.response.ObtainAuthorizerInfoResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 获取微信代管公众号认证信息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class ObtainAuthorizerInfoClient extends WxComponentClient<ObtainAuthorizerInfoRequest, ObtainAuthorizerInfoResponse> {

    ObtainAuthorizerInfoClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper, "obtainAuthorizerInfo");
    }

    @Override
    protected String buildUri(ObtainAuthorizerInfoRequest request) {
        return "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info?component_access_token={token}";
    }

    @Override
    protected Object[] uriParams(ObtainAuthorizerInfoRequest request) {
        return new Object[]{request.getToken()};
    }

    @Override
    protected String buildBody(ObtainAuthorizerInfoRequest request){
        return String.format("{\"component_appid\": \"%s\", \"authorizer_appid\": \"%s\"}",
                request.getComponentAppid(), request.getAuthorizerAppid());
    }

    @Override
    protected ObtainAuthorizerInfoResponse buildResponse(Map<String, Object> data) {
        return new ObtainAuthorizerInfoResponse(data);
    }
}
