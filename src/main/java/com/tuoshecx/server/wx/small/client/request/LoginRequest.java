package com.tuoshecx.server.wx.small.client.request;

public class LoginRequest extends WxSmallRequest {
    private final String appid;
    private final String componentAppid;
    private final String componentToken;
    private final String code;

    public LoginRequest(String appid, String componentAppid, String componentToken, String code) {
        super("");
        this.appid = appid;
        this.componentAppid = componentAppid;
        this.componentToken = componentToken;
        this.code = code;
    }

    public String getComponentAppid() {
        return componentAppid;
    }

    public String getComponentToken() {
        return componentToken;
    }

    public String getCode() {
        return code;
    }

    public String getAppid() {
        return appid;
    }
}
