package com.linku.server.wx.gzh.client.response;

import java.util.Map;

/**
 * 获取微信凭证输出对象
 *
 * @author WangWei
 */
public class ObtainCredentialResponse extends GzhResponse {
    private final String token;
    private final int expires;

    public ObtainCredentialResponse(Map<String, Object> data) {
        super(data);
        this.token = (String)data.get("access_token");
        this.expires = (Integer) data.get("expires_in");
    }

    public String getToken() {
        return token;
    }

    public int getExpires() {
        return expires;
    }
}
