package com.tuoshecx.server.wx.component.client.request;

/**
 * 使用授权码换取公众号或小程序的接口调用凭据和授权信息请求
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class ObtainQueryAuthRequest extends ComponentRequest {
    private final String componentAppid;
    private final String authorizationCode;

    public ObtainQueryAuthRequest(String token, String componentAppid, String authorizationCode) {
        super(token);
        this.componentAppid = componentAppid;
        this.authorizationCode = authorizationCode;
    }

    public String getComponentAppid() {
        return componentAppid;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }
}
