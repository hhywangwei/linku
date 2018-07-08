package com.tuoshecx.server.wx.small.message.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 发送的模板消息
 *
 * @author WangWei
 */
public class SendMessage {
    private String id;
    private String appid;
    private String openid;
    private String callKey;
    private String formId;
    private String page;
    private String title;
    private String content;
    private String color;
    private String emphasisKeyword;
    private String state;
    private String error;
    private Integer retry;
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getCallKey() {
        return callKey;
    }

    public void setCallKey(String callKey) {
        this.callKey = callKey;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getRetry() {
        return retry;
    }

    public void setRetry(Integer retry) {
        this.retry = retry;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendMessage that = (SendMessage) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(appid, that.appid) &&
                Objects.equals(openid, that.openid) &&
                Objects.equals(callKey, that.callKey) &&
                Objects.equals(formId, that.formId) &&
                Objects.equals(page, that.page) &&
                Objects.equals(title, that.title) &&
                Objects.equals(content, that.content) &&
                Objects.equals(color, that.color) &&
                Objects.equals(emphasisKeyword, that.emphasisKeyword) &&
                Objects.equals(state, that.state) &&
                Objects.equals(error, that.error) &&
                Objects.equals(retry, that.retry) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, appid, openid, callKey, formId, page, title, content, color, emphasisKeyword, state, error, retry, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("appid", appid)
                .append("openid", openid)
                .append("callKey", callKey)
                .append("formId", formId)
                .append("page", page)
                .append("title", title)
                .append("content", content)
                .append("color", color)
                .append("emphasisKeyword", emphasisKeyword)
                .append("state", state)
                .append("error", error)
                .append("retry", retry)
                .append("createTime", createTime)
                .toString();
    }
}
