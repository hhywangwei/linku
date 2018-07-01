package com.tuoshecx.server.wx.component.client.response;

import java.util.Map;

/**
 * 获取第三方平台预授权码输出
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class ObtainPreAuthCodeResponse extends ComponentResponse {
    private final String preAuthCode;
    private final Integer expiresIn;

    public ObtainPreAuthCodeResponse(Map<String, Object> data) {
        super(data);
        this.preAuthCode = (String)data.getOrDefault("pre_auth_code", "");
        this.expiresIn = (Integer)data.getOrDefault("expires_in", -1);
    }

    public String getPreAuthCode() {
        return preAuthCode;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }
}
