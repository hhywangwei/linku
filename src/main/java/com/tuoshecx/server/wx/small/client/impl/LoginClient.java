package com.tuoshecx.server.wx.small.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.small.client.request.LoginRequest;
import com.tuoshecx.server.wx.small.client.response.LoginResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 小程登陆程序
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class LoginClient extends WxSmallClient<LoginRequest, LoginResponse> {
    private final RestTemplate restTemplate;

    LoginClient(RestTemplate restTemplate, ObjectMapper objectMapper){
        super(restTemplate, objectMapper, "loginClient");
        this.restTemplate = restTemplate;
    }

    @Override
    public LoginResponse request(LoginRequest request) {
        return doResponse(restTemplate.getForEntity(buildUri(request), byte[].class, uriParams(request)));
    }

    @Override
    protected String buildUri(LoginRequest loginRequest) {
        return "https://api.weixin.qq.com/sns/component/jscode2session?" +
                "appid={appid}&js_code={jsCode}&grant_type=authorization_code&" +
                "component_appid={componentAppid}&component_access_token={componentAccessToken}";
    }

    @Override
    protected Object[] uriParams(LoginRequest request) {
        return new Object[]{request.getAppid(), request.getCode(), request.getComponentAppid(), request.getComponentToken()};
    }

    @Override
    protected String buildBody(LoginRequest loginRequest) {
        //not instance
        return "";
    }

    @Override
    protected LoginResponse buildResponse(Map<String, Object> data) {
        return new LoginResponse(data);
    }
}
