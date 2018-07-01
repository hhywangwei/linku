package com.tuoshecx.server.wx.component.client.request;

/**
 * 获取第三方平台预授权码请求
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class ObtainPreAuthCodeRequest extends ComponentRequest {
    private final String componentAppid;

    public ObtainPreAuthCodeRequest(String token, String componentApi) {
        super(token);
        this.componentAppid = componentApi;
    }

    public String getComponentAppid() {
        return componentAppid;
    }
}
