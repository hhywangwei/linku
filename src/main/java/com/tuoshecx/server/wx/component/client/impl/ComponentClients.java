package com.tuoshecx.server.wx.component.client.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.common.client.HttpClient;
import com.tuoshecx.server.wx.component.client.request.*;
import com.tuoshecx.server.wx.component.client.response.*;
import org.springframework.web.client.RestTemplate;

/**
 * 微信第三方平台客户端
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class ComponentClients {

    private final HttpClient<ObtainAccessTokenRequest, ObtainAccessTokenResponse> obtainAccessTokenClient;
    private final HttpClient<ObtainAuthorizerInfoRequest, ObtainAuthorizerInfoResponse> obtainAuthorizerInfoClient;
    private final HttpClient<ObtainAuthorizerTokenRequest, ObtainAuthorizerTokenResponse> obtainAuthorizerTokenClient;
    private final HttpClient<ObtainPreAuthCodeRequest, ObtainPreAuthCodeResponse> obtainPreAuthCodeClient;
    private final HttpClient<ObtainQueryAuthRequest, ObtainQueryAuthResponse> obtainQueryAuthClient;

    public ComponentClients(RestTemplate restTemplate){
        ObjectMapper objectMapper = initObjectMapper();
        this.obtainAccessTokenClient = new ObtainAccessTokenClient(restTemplate, objectMapper);
        this.obtainAuthorizerInfoClient = new ObtainAuthorizerInfoClient(restTemplate, objectMapper);
        this.obtainAuthorizerTokenClient = new ObtainAuthorizerTokenClient(restTemplate, objectMapper);
        this.obtainPreAuthCodeClient = new ObtainPreAuthCodeClient(restTemplate, objectMapper);
        this.obtainQueryAuthClient = new ObtainQueryAuthClient(restTemplate, objectMapper);
    }

    private ObjectMapper initObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        return mapper;
    }

    public HttpClient<ObtainAccessTokenRequest, ObtainAccessTokenResponse> getObtainAccessTokenClient() {
        return obtainAccessTokenClient;
    }

    public HttpClient<ObtainAuthorizerInfoRequest, ObtainAuthorizerInfoResponse> getObtainAuthorizerInfoClient() {
        return obtainAuthorizerInfoClient;
    }

    public HttpClient<ObtainAuthorizerTokenRequest, ObtainAuthorizerTokenResponse> getObtainAuthorizerTokenClient() {
        return obtainAuthorizerTokenClient;
    }

    public HttpClient<ObtainPreAuthCodeRequest, ObtainPreAuthCodeResponse> getObtainPreAuthCodeClient() {
        return obtainPreAuthCodeClient;
    }

    public HttpClient<ObtainQueryAuthRequest, ObtainQueryAuthResponse> getObtainQueryAuthClient() {
        return obtainQueryAuthClient;
    }


}
