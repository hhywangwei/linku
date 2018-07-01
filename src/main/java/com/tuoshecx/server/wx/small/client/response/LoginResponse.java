package com.tuoshecx.server.wx.small.client.response;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;

/**
 * 微信小程序登陆输出
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class LoginResponse extends WxSmallResponse {
    private final String openid;
    private final String sessionKey;
    private final String unionid;

    public LoginResponse(Map<String, Object> data){
        super(data);
        this.openid = (String)data.get("openid");
        this.sessionKey = (String)data.get("session_key");
        this.unionid =(String)data.getOrDefault("unionid", "");
    }

    public String getOpenid() {
        return openid;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public String getUnionid() {
        return unionid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("openid", openid)
                .append("sessionKey", sessionKey)
                .append("unionid", unionid)
                .toString();
    }
}
