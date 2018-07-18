package com.tuoshecx.server.wx.small.client.response;

import java.util.Map;

public class SubmitAuditResponse extends WxSmallResponse {
    private final Integer auditId;

    public SubmitAuditResponse(Map<String, Object> data) {
        super(data);
        this.auditId = (Integer)data.getOrDefault("auditid", -1);
    }

    public Integer getAuditId() {
        return auditId;
    }
}
