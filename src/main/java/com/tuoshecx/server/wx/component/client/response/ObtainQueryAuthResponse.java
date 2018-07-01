package com.tuoshecx.server.wx.component.client.response;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ObtainQueryAuthResponse extends ComponentResponse {
    private final String authorizerAppid;
    private final String authorizerAccessToken;
    private final Integer expiresIn;
    private final String authorizerRefreshToken;
    private final Integer[] func;

    @SuppressWarnings("unchecked")
    public ObtainQueryAuthResponse(Map<String, Object> data) {
        super(data);
        Map<String, Object> info = (Map<String, Object>)data.getOrDefault("authorization_info", Collections.emptyMap());
        this.authorizerAppid = (String)info.getOrDefault("authorizer_appid", "");
        this.authorizerAccessToken = (String)info.getOrDefault("authorizer_access_token", "");
        this.expiresIn = (Integer)info.getOrDefault("expires_in", -1);
        this.authorizerRefreshToken = (String)info.getOrDefault("authorizer_refresh_token", "");
        this.func = func((List<Map<String, Object>>)info.getOrDefault("func_info", Collections.emptyMap()));
    }

    @SuppressWarnings("unchecked")
    private Integer[] func(List<Map<String, Object>> data){
        return data.stream()
                .map(e -> (Map<String, Object>)e.get("funcscope_category"))
                .map(e -> (Integer)e.get("id"))
                .toArray(Integer[]::new);
    }

    public String getAuthorizerAppid() {
        return authorizerAppid;
    }

    public String getAuthorizerAccessToken() {
        return authorizerAccessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public String getAuthorizerRefreshToken() {
        return authorizerRefreshToken;
    }

    public Integer[] getFunc() {
        return func;
    }
}

