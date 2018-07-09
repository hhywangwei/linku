package com.tuoshecx.server.wx.component.client;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.wx.component.client.impl.ComponentClients;
import com.tuoshecx.server.wx.component.client.request.*;
import com.tuoshecx.server.wx.component.client.response.*;
import com.tuoshecx.server.wx.component.token.ComponentTokenService;
import com.tuoshecx.server.wx.component.token.ComponentVerifyTicketService;
import com.tuoshecx.server.wx.configure.properties.WxComponentProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 微信第三方平台客户端业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
public class ComponentClientService {
    private final WxComponentProperties properties;
    private final ComponentClients clients;
    private final ComponentTokenService tokenService;
    private final ComponentVerifyTicketService verifyTicketService;

    @Autowired
    public ComponentClientService(WxComponentProperties properties, RestTemplate restTemplate,
                                  ComponentTokenService tokenService, ComponentVerifyTicketService verifyTicketService){

        this.properties = properties;
        this.tokenService = tokenService;
        this.verifyTicketService = verifyTicketService;
        this.clients = new ComponentClients(restTemplate);
    }

    public ObtainAccessTokenResponse obtainAccessToken(){
        ObtainAccessTokenRequest request = new ObtainAccessTokenRequest(properties.getAppid(),
                properties.getSecret(), verifyTicketService.get(properties.getAppid()));
        return clients.getObtainAccessTokenClient().request(request);
    }

    public ObtainAuthorizerInfoResponse obtainAuthorizerInfo(String authorizerAppid){
        ObtainAuthorizerInfoRequest request = new ObtainAuthorizerInfoRequest(getComponentToken(properties.getAppid()),
                properties.getAppid(), authorizerAppid);
        return clients.getObtainAuthorizerInfoClient().request(request);
    }

    public ObtainAuthorizerTokenResponse obtainAuthorizerToken(String authorizerAppid, String authorizerRefreshToken){
        ObtainAuthorizerTokenRequest request = new ObtainAuthorizerTokenRequest(getComponentToken(properties.getAppid()),
                properties.getAppid(), authorizerAppid, authorizerRefreshToken);
        return clients.getObtainAuthorizerTokenClient().request(request);
    }

    public ObtainPreAuthCodeResponse obtainPreAuthCode(){
        ObtainPreAuthCodeRequest request = new ObtainPreAuthCodeRequest(
                getComponentToken(properties.getAppid()), properties.getAppid());
        return clients.getObtainPreAuthCodeClient().request(request);
    }

    public ObtainQueryAuthResponse obtainQueryAuth(String authorizationCode){
        ObtainQueryAuthRequest request = new ObtainQueryAuthRequest(
                getComponentToken(properties.getAppid()), properties.getAppid(), authorizationCode);
        return clients.getObtainQueryAuthClient().request(request);
    }

    private String getComponentToken(String componentAppid){
        return tokenService.get(componentAppid).orElseThrow(() -> new BaseException(201, "Token不存在"));
    }
}
