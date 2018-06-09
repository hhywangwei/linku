package com.linku.server.wx.gzh.client.request;

/**
 * 获取微信公众号访问凭证
 *
 * @author WangWei
 */
public class ObtainCredentialRequest extends GzhRequest{
    private final String appid;
    private final String secret;

    public ObtainCredentialRequest(String appid, String secret) {
        super("");
        this.appid = appid;
        this.secret = secret;
    }

    public String getAppid() {
        return appid;
    }

    public String getSecret() {
        return secret;
    }
}
