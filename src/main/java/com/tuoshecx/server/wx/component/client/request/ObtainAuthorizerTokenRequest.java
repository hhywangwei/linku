package com.tuoshecx.server.wx.component.client.request;

/**
 * 获取（刷新）授权公众号或小程序的接口调用凭据（令牌）请求
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class ObtainAuthorizerTokenRequest extends ComponentRequest{
    private final String componentAppid;
    private final String authorizerAppid;
    private final String authorizerRefreshToken;

    public ObtainAuthorizerTokenRequest(String token, String componentAppid, String authorizerAppid, String authorizerRefreshToken) {
        super(token);
        this.componentAppid = componentAppid;
        this.authorizerAppid = authorizerAppid;
        this.authorizerRefreshToken = authorizerRefreshToken;
    }

    public String getComponentAppid() {
        return componentAppid;
    }

    public String getAuthorizerAppid() {
        return authorizerAppid;
    }

    public String getAuthorizerRefreshToken() {
        return authorizerRefreshToken;
    }
}
