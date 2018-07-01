package com.tuoshecx.server.wx.component.client.request;

/**
 * 获取第三方平台Access token
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class ObtainAccessTokenRequest extends ComponentRequest {
    private final String componentAppid;
    private final String componentSecret;
    private final String verifyTicket;

    public ObtainAccessTokenRequest(String componentAppid, String componentSecret, String verifyTicket) {
        super("");
        this.componentAppid = componentAppid;
        this.componentSecret = componentSecret;
        this.verifyTicket = verifyTicket;
    }

    public String getComponentAppid() {
        return componentAppid;
    }

    public String getComponentSecret() {
        return componentSecret;
    }

    public String getVerifyTicket() {
        return verifyTicket;
    }

    @Override
    public String toString() {
        return "ObtainAccessTokenRequest{" +
                "componentAppid='" + componentAppid + '\'' +
                ", componentSecret='" + componentSecret + '\'' +
                ", verifyTicket='" + verifyTicket + '\'' +
                '}';
    }
}
