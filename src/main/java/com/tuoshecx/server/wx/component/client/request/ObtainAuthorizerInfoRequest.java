package com.tuoshecx.server.wx.component.client.request;

/**
 * 获取授权方的帐号基本信息请求
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class ObtainAuthorizerInfoRequest extends ComponentRequest {
    private final String componentAppid;
    private final String authorizerAppid;

    public ObtainAuthorizerInfoRequest(String token, String componentAppid, String authorizerAppid) {
        super(token);
        this.componentAppid = componentAppid;
        this.authorizerAppid = authorizerAppid;
    }

    public String getComponentAppid() {
        return componentAppid;
    }

    public String getAuthorizerAppid() {
        return authorizerAppid;
    }
}
