package com.tuoshecx.server.wx.component.client.impl;

import com.tuoshecx.server.wx.component.client.ComponentHttpClient;
import com.tuoshecx.server.wx.component.client.request.*;
import com.tuoshecx.server.wx.component.client.response.*;

/**
 * 微信第三方平台客户端
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class ComponentClients {

    private final ComponentHttpClient<ObtainAccessTokenRequest, ObtainAccessTokenResponse> obtainAccessTokenClient;
    private final ComponentHttpClient<ObtainAuthorizerInfoRequest, ObtainAuthorizerInfoResponse> obtainAuthorizerInfoClient;
    private final ComponentHttpClient<ObtainAuthorizerTokenRequest, ObtainAuthorizerTokenResponse> obtainAuthorizerTokenClient;
    private final ComponentHttpClient<ObtainPreAuthCodeRequest, ObtainPreAuthCodeResponse> obtainPreAuthCodeClient;
    private final ComponentHttpClient<ObtainQueryAuthRequest, ObtainQueryAuthResponse> obtainQueryAuthClient;

    public ComponentClients(){
        this.obtainAccessTokenClient = new ObtainAccessTokenClient();
        this.obtainAuthorizerInfoClient = new ObtainAuthorizerInfoClient();
        this.obtainAuthorizerTokenClient = new ObtainAuthorizerTokenClient();
        this.obtainPreAuthCodeClient = new ObtainPreAuthCodeClient();
        this.obtainQueryAuthClient = new ObtainQueryAuthClient();
    }

    public ComponentHttpClient<ObtainAccessTokenRequest, ObtainAccessTokenResponse> getObtainAccessTokenClient() {
        return obtainAccessTokenClient;
    }

    public ComponentHttpClient<ObtainAuthorizerInfoRequest, ObtainAuthorizerInfoResponse> getObtainAuthorizerInfoClient() {
        return obtainAuthorizerInfoClient;
    }

    public ComponentHttpClient<ObtainAuthorizerTokenRequest, ObtainAuthorizerTokenResponse> getObtainAuthorizerTokenClient() {
        return obtainAuthorizerTokenClient;
    }

    public ComponentHttpClient<ObtainPreAuthCodeRequest, ObtainPreAuthCodeResponse> getObtainPreAuthCodeClient() {
        return obtainPreAuthCodeClient;
    }

    public ComponentHttpClient<ObtainQueryAuthRequest, ObtainQueryAuthResponse> getObtainQueryAuthClient() {
        return obtainQueryAuthClient;
    }
}
