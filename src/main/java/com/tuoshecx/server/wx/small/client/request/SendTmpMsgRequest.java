package com.tuoshecx.server.wx.small.client.request;

import java.util.List;

/**
 * 发送模板消息请求
 *
 * @author WangWei
 */
public class SendTmpMsgRequest extends WxSmallRequest {

    private final String openid;
    private final String templateId;
    private final List<Item> items;
    private final String formId;
    private String page;
    private String color;
    private String emphasisKeyword;

    public SendTmpMsgRequest(String token, String openid, String templateId, List<Item> items, String formId) {
        super(token);
        this.openid = openid;
        this.templateId = templateId;
        this.items = items;
        this.formId = formId;
    }

    public String getOpenid() {
        return openid;
    }

    public String getTemplateId() {
        return templateId;
    }

    public List<Item> getItems() {
        return items;
    }

    public String getFormId(){
        return formId;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEmphasisKeyword() {
        return emphasisKeyword;
    }

    public void setEmphasisKeyword(String emphasisKeyword) {
        this.emphasisKeyword = emphasisKeyword;
    }

    public static class Item {
        private final String key;
        private final String value;
        private final String color;

        public Item(String key, String value){
            this(key, value, "#383939");
        }

        public Item(String key, String value, String color){
            this.key = key;
            this.value = value;
            this.color = color;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public String getColor() {
            return color;
        }
    }
}
