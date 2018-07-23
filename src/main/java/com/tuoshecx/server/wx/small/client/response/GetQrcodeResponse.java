package com.tuoshecx.server.wx.small.client.response;

import java.util.HashMap;
import java.util.Map;

public class GetQrcodeResponse extends WxSmallResponse {
    private final byte[] content;

    public GetQrcodeResponse(Map<String, Object> data) {
        super(data);
        this.content = new byte[0];
    }

    public GetQrcodeResponse(byte[] content){
        super(new HashMap<String, Object>(){{
            put("errcode", 0);
        }});
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }
}
