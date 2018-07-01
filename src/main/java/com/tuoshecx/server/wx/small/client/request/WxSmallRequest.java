package com.tuoshecx.server.wx.small.client.request;

/**
 * 微信小程序请求
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class WxSmallRequest {
    private final String token;

    /**
     * 构造微信请求
     *
     * @param token 访问微信token
     */
    public WxSmallRequest(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
