package com.tuoshecx.server.wx.component.client.request;

/**
 * 微信第三方平台请求
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class ComponentRequest {
    private final String token;

    public ComponentRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "ComponentRequest{" +
                "token='" + token + '\'' +
                '}';
    }
}
