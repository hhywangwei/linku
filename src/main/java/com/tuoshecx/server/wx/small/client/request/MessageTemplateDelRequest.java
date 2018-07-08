package com.tuoshecx.server.wx.small.client.request;

public class MessageTemplateDelRequest extends WxSmallRequest {
    private final String templateId;

    public MessageTemplateDelRequest(String token, String templateId) {
        super(token);
        this.templateId = templateId;
    }

    public String getTemplateId() {
        return templateId;
    }
}
