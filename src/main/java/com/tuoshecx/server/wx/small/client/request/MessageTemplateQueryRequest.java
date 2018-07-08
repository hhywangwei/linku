package com.tuoshecx.server.wx.small.client.request;

public class MessageTemplateQueryRequest extends WxSmallRequest{
    private final int offset;
    private final int count;

    public MessageTemplateQueryRequest(String token, int offset, int count) {
        super(token);
        this.offset = offset;
        this.count = count;
    }

    public int getOffset() {
        return offset;
    }

    public int getCount() {
        return count;
    }
}
