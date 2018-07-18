package com.tuoshecx.server.wx.small.client.request;

public class GetAuditStatusRequest extends WxSmallRequest {
    private final Integer auditId;

    public GetAuditStatusRequest(String token, Integer auditId) {
        super(token);
        this.auditId = auditId;
    }

    public Integer getAuditId() {
        return auditId;
    }
}
