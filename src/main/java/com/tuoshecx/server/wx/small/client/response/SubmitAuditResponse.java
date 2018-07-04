package com.tuoshecx.server.wx.small.client.response;

import java.util.Map;

public class SubmitAuditResponse extends WxSmallResponse {
    private final String auditId;

    public SubmitAuditResponse(Map<String, Object> data) {
        super(data);
        this.auditId = (String)data.getOrDefault("auditid", "");
    }

    public String getAuditId() {
        return auditId;
    }
}
