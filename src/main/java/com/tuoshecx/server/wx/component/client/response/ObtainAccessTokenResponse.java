package com.tuoshecx.server.wx.component.client.response;

import java.util.Map;

/**
 * 微信第三方平台获取Access token输出
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class ObtainAccessTokenResponse extends ComponentResponse {
    private final String componentAccessToken;
    private final Integer expiresIn;

    public ObtainAccessTokenResponse(Map<String, Object> data) {
        super(data);
        this.componentAccessToken = (String)data.getOrDefault("component_access_token", "");
        this.expiresIn = (Integer)data.getOrDefault("expires_in", -1);
    }

    public String getComponentAccessToken() {
        return componentAccessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }
}
