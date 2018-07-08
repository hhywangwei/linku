package com.tuoshecx.server.wx.small.client.request;

import java.util.List;

public class MessageTemplateAddRequest extends WxSmallRequest {
    private final String id;
    private final List<Integer> keywordIds;

    public MessageTemplateAddRequest(String token, String id, List<Integer> keywordIds) {
        super(token);
        this.id = id;
        this.keywordIds = keywordIds;
    }

    public String getId() {
        return id;
    }

    public List<Integer> getKeywordIds() {
        return keywordIds;
    }
}
