package com.tuoshecx.server.wx.small.client.response;

import java.util.Map;

public class MessageTemplateAddResponse extends WxSmallResponse {
    private final String templateId;

    public MessageTemplateAddResponse(Map<String, Object> data) {
        super(data);
        this.templateId = (String)data.getOrDefault("template_id", "");
    }

    public String getTemplateId() {
        return templateId;
    }
}
