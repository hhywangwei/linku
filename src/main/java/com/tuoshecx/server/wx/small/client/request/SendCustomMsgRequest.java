package com.tuoshecx.server.wx.small.client.request;

import java.util.HashMap;
import java.util.Map;

public class SendCustomMsgRequest extends WxSmallRequest{
    private final String openid;
    private final String msgType;
    private final Map<String, String> content;

    private SendCustomMsgRequest(String token, String openid, String msgType, Map<String, String> content) {
        super(token);
        this.openid = openid;
        this.msgType = msgType;
        this.content = content;
    }

    public String getOpenid() {
        return openid;
    }

    public String getMsgType() {
        return msgType;
    }

    public Map<String, String> getContent() {
        return content;
    }

    public static SendCustomMsgRequest buildText(String token, String toUser, String content){
        Map<String, String> data = new HashMap<String, String>(){{
            put("content", content);
        }};
        return new SendCustomMsgRequest(token, toUser, "text", data);
    }

    public static SendCustomMsgRequest buildImage(String token, String toUser, String mediaId){
        Map<String, String> data = new HashMap<String, String>(){{
           put("media_id", mediaId);
        }};
        return new SendCustomMsgRequest(token, toUser, "image", data);
    }

    public static SendCustomMsgRequest buildLink(String token, String toUser, String title, String description, String url, String thumbUrl){
        Map<String, String> data = new HashMap<String, String>(){{
            put("title", title);
            put("description", description);
            put("url", url);
            put("thumb_url", thumbUrl);
        }};
        return new SendCustomMsgRequest(token, toUser, "link", data);
    }

    public static SendCustomMsgRequest buildSmallCard(String token, String toUser, String title, String pagePath, String thumbMediaId){
        Map<String, String> data = new HashMap<String, String>(){{
           put("title", title);
           put("pagepath", pagePath);
           put("thumb_media_id", thumbMediaId);
        }};
        return new SendCustomMsgRequest(token, toUser, "miniprogrampage", data);
    }
}
