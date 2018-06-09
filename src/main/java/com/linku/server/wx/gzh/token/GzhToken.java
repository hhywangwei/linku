package com.linku.server.wx.gzh.token;

/**
 * 访问微信凭证
 *
 * @author WangWei
 */
public class GzhToken {
    private final String key;
    private final String token;
    private final long expireTime;

    public GzhToken(String key, String token, long expireTime){

        this.key = key;
        this.token = token;
        this.expireTime = expireTime;
    }

    public String getKey() {
        return key;
    }

    public String getToken() {
        return token;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public boolean isExpire(){
        return System.currentTimeMillis() > expireTime;
    }
}
