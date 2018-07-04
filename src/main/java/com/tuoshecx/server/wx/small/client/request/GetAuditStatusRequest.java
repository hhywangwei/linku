package com.tuoshecx.server.wx.small.client.request;

public class GetAuditStatusRequest extends WxSmallRequest {
    private final String auditId;

    public GetAuditStatusRequest(String token, String auditId) {
        super(token);
        this.auditId = auditId;
    }

    public String getAuditId() {
        return auditId;
    }
}
