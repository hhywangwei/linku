package com.tuoshecx.server.wx.component.client.response;

import java.util.Map;

/**
 * 获取（刷新）授权公众号或小程序的接口调用凭据（令牌）输出
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class ObtainAuthorizerTokenResponse extends ComponentResponse {
    private final String authorizerAccessToken;
    private final String authorizerRefreshToken;
    private final Integer expiresIn;

    public ObtainAuthorizerTokenResponse(Map<String, Object> data) {
        super(data);
        this.authorizerAccessToken = (String) data.getOrDefault("authorizer_access_token", "");
        this.authorizerRefreshToken = (String) data.getOrDefault("authorizer_refresh_token", "");
        this.expiresIn = (Integer) data.getOrDefault("expires_in", -1);
    }

    public String getAuthorizerAccessToken() {
        return authorizerAccessToken;
    }

    public String getAuthorizerRefreshToken() {
        return authorizerRefreshToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }
}