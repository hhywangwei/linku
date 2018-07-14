package com.tuoshecx.server.wx.small.client.request;

public class SetWebViewDomainRequest extends WxSmallRequest {
    private final String action;
    private final String[] webViewDomain;

    public SetWebViewDomainRequest(String token, String action, String[] webViewDomain) {
        super(token);
        this.action = action;
        this.webViewDomain = webViewDomain;
    }

    public String getAction() {
        return action;
    }

    public String[] getWebViewDomain() {
        return webViewDomain;
    }
}
