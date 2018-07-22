package com.tuoshecx.server.wx.small.message.sender;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SmallTemplateMessage{

    public static class Builder{
        private final String openid;
        private final String appid;
        private final String callKey;
        private final String formId;
        private final List<ContentItem> contentItems;
        private String page;
        private String color;
        private String emphasisKeyword;

        public Builder(String openid, String appid, String callKey, String formId) {
            this.openid = openid;
            this.appid = appid;
            this.callKey = callKey;
            this.formId = formId;
            this.contentItems = new ArrayList<>();
        }

        public Builder setPage(String page){
            this.page = page;
            return this;
        }

        public Builder setColor(String color){
            this.color = color;
            return this;
        }

        public Builder setEmphasisKeyword(String emphasisKeyword){
            this.emphasisKeyword = emphasisKeyword;
            return this;
        }

        public Builder addContentItem(String keyword, String value){
            return addContentItem(keyword, value, "");
        }

        public Builder addContentItem(String keyword, String value, String color){
            contentItems.add(new ContentItem(keyword, value, color));
            return this;
        }

        public SmallTemplateMessage build(){
            return new SmallTemplateMessage(openid, appid, callKey, formId, contentItems, page, color, emphasisKeyword);
        }
    }

    private final String openid;
    private final String appid;
    private final String callKey;
    private final String formId;
    private final List<ContentItem> contentItems;
    private final String page;
    private final String color;
    private final String emphasisKeyword;

    private SmallTemplateMessage(String openid, String appid, String callKey, String formId,
                     List<ContentItem> contentItems, String page,
                     String color, String emphasisKeyword) {

        this.openid = openid;
        this.appid = appid;
        this.callKey = callKey;
        this.formId = formId;
        this.contentItems = Collections.unmodifiableList(contentItems);
        this.page = page;
        this.color = color;
        this.emphasisKeyword = emphasisKeyword;
    }

    public String getOpenid() {
        return openid;
    }

    public String getAppid() {
        return appid;
    }

    public String getCallKey() {
        return callKey;
    }

    public String getFormId() {
        return formId;
    }

    public List<ContentItem> getContentItems() {
        return contentItems;
    }

    public String getPage() {
        return page;
    }

    public String getColor() {
        return color;
    }

    public String getEmphasisKeyword() {
        return emphasisKeyword;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("openid", openid)
                .append("appid", appid)
                .append("callKey", callKey)
                .append("formId", formId)
                .append("contentItems", contentItems)
                .append("page", page)
                .append("color", color)
                .append("emphasisKeyword", emphasisKeyword)
                .toString();
    }

    public static class ContentItem  {
        private final String keyword;
        private final String value;
        private final String color;

        ContentItem(String keyword, String value, String color) {
            this.keyword = keyword;
            this.value = value;
            this.color = color;
        }

        public String getKeyword() {
            return keyword;
        }

        public String getValue() {
            return value;
        }

        public String getColor() {
            return color;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("keyword", keyword)
                    .append("value", value)
                    .append("color", color)
                    .toString();
        }
    }
}
