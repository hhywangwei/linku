package com.linku.server.wx.small.client.impl;

import com.linku.server.common.client.AsyncHttpClient;
import com.linku.server.wx.configure.properties.WxProperties;
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

    public WxSmallClients(WxProperties properties) {
        this.loginClient = new LoginClient(properties);
        this.sendTmpMsgClient = new SendTmpMsgClient(properties);
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
}
