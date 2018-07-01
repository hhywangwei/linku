package com.linku.server.wx.small.client.impl;

import com.linku.server.common.client.AsyncHttpClient;
import com.linku.server.wx.component.token.ComponentTokenService;
import com.linku.server.wx.configure.properties.WxComponentProperties;
import com.linku.server.wx.small.client.request.SendCustomMsgRequest;
import com.linku.server.wx.small.client.request.SendTmpMsgRequest;
import com.linku.server.wx.small.client.response.LoginResponse;
import com.linku.server.wx.small.client.response.WxSmallResponse;

/**
 * 微信小程序API接口请求处理
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class WxSmallClients {
    private final AsyncHttpClient<String, LoginResponse> loginClient;
    private final AsyncHttpClient<SendTmpMsgRequest, WxSmallResponse> sendTmpMsgClient;
    private final AsyncHttpClient<SendCustomMsgRequest, WxSmallResponse> sendCustomMsgClient;

    public WxSmallClients(WxComponentProperties properties, ComponentTokenService tokenService) {
        this.loginClient = new LoginClient(properties, tokenService);
        this.sendTmpMsgClient = new SendTmpMsgClient();
        this.sendCustomMsgClient = new SendCustomMsgClient();
    }

    /**
     * 用户登陆请求客户端
     *
     * @return {@link LoginClient}
     */
    public AsyncHttpClient<String, LoginResponse> loginClient(){
        return loginClient;
    }

    /**
     * 发送模板消息
     *
     * @return {@link SendTmpMsgClient}
     */
    public AsyncHttpClient<SendTmpMsgRequest, WxSmallResponse> sendTmpMsgClient(){
        return sendTmpMsgClient;
    }

    /**
     *  发送客户消息
     *
     * @return {@link SendCustomMsgClient}
     */
    public AsyncHttpClient<SendCustomMsgRequest, WxSmallResponse> sendCustomMsgClient() {
        return sendCustomMsgClient;
    }
}
