package com.tuoshecx.server.wx.component.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.component.client.request.ObtainPreAuthCodeRequest;
import com.tuoshecx.server.wx.component.client.response.ObtainPreAuthCodeResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 获取托管公众号预验证码
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class ObtainPreAuthCodeClient extends WxComponentClient<ObtainPreAuthCodeRequest, ObtainPreAuthCodeResponse> {

    ObtainPreAuthCodeClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper, "obtainPreAuthCode");
    }

    @Override
    protected String buildUri(ObtainPreAuthCodeRequest request) {
        return "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token={token}";
    }

    @Override
    protected Object[] uriParams(ObtainPreAuthCodeRequest request) {
        return new Object[]{request.getToken()};
    }

    @Override
    protected String buildBody(ObtainPreAuthCodeRequest request){
        return  String.format("{\"component_appid\":\"%s\"}", request.getComponentAppid());
    }

    @Override
    protected ObtainPreAuthCodeResponse buildResponse(Map<String, Object> data) {
        return new ObtainPreAuthCodeResponse(data);
    }
}
