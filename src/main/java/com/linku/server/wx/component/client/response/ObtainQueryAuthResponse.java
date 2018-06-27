package com.linku.server.wx.component.client.response;

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
        Map<String, Object> info = (Map<String, Object>)data.get("authorization_info");
        this.authorizerAppid = (String)info.get("authorizer_appid");
        this.authorizerAccessToken = (String)info.get("authorizer_access_token");
        this.expiresIn = (Integer)info.get("expires_in");
        this.authorizerRefreshToken = (String)info.get("authorizer_refresh_token");
        this.func = func((List<Map<String, Object>>)info.get("func_info"));
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

