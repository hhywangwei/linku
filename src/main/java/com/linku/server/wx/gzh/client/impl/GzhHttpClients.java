package com.linku.server.wx.gzh.client.impl;

import com.linku.server.wx.configure.properties.WxProperties;
import com.linku.server.wx.gzh.client.GzhHttpClient;
import com.linku.server.wx.gzh.client.request.ObtainCredentialRequest;
import com.linku.server.wx.gzh.client.response.ObtainCredentialResponse;

/**
 * 访问微信API Factory
 *
 * @author WangWei
 */
public class GzhHttpClients {

    private final GzhHttpClient<ObtainCredentialRequest, ObtainCredentialResponse> obtainCredentialClient;

    public GzhHttpClients(WxProperties properties){
        obtainCredentialClient = new ObtainCredentialHttpClient(properties, "/cgi-bin/token");
    }

    public GzhHttpClient<ObtainCredentialRequest, ObtainCredentialResponse> obtainCredentialClient(){
        return obtainCredentialClient;
    }
}
