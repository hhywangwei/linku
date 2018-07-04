package com.tuoshecx.server.wx.small.client.request;

public class ProgramCommitRequest extends WxSmallRequest {
    private final String templateId;
    private final String userVersion;
    private final String userDesc;
    private final String extJson;

    public ProgramCommitRequest(String token, String templateId, String userVersion, String userDesc, String extJson) {
        super(token);
        this.templateId = templateId;
        this.userVersion = userVersion;
        this.userDesc = userDesc;
        this.extJson = extJson;
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getUserVersion() {
        return userVersion;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public String getExtJson() {
        return extJson;
    }
}
