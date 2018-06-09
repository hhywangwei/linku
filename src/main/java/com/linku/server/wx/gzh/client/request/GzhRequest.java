package com.linku.server.wx.gzh.client.request;

/**
 * 实现微信公众号API请求对象
 *
 * @author WangWei
 */
public class GzhRequest {
    private final String token;

    /**
     * 构造微信请求
     *
     * @param token 访问微信token
     */
    public GzhRequest(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
