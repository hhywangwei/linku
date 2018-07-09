package com.tuoshecx.server.wx.component.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.component.client.request.ObtainQueryAuthRequest;
import com.tuoshecx.server.wx.component.client.response.ObtainQueryAuthResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 查询托管公众号信息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class ObtainQueryAuthClient extends WxComponentClient<ObtainQueryAuthRequest, ObtainQueryAuthResponse> {

    ObtainQueryAuthClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper, "obtainQueryAuth");
    }

    @Override
    protected String buildUri(ObtainQueryAuthRequest request) {
        return "https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token={token}";
    }

    @Override
    protected Object[] uriParams(ObtainQueryAuthRequest request) {
        return new Object[]{request.getToken()};
    }

    @Override
    protected String buildBody(ObtainQueryAuthRequest request){
        return String.format("{\"component_appid\": \"%s\", \"authorization_code\": \"%s\"}",
                request.getComponentAppid(), request.getAuthorizationCode());
    }

    @Override
    protected ObtainQueryAuthResponse buildResponse(Map<String, Object> data) {
        return new ObtainQueryAuthResponse(data);
    }
}
