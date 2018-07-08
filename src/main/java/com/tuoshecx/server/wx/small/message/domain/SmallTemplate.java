package com.tuoshecx.server.wx.small.message.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 微信消息模板信息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel(value = "微信消息模板信息")
public class SmallTemplate {
    @ApiModelProperty(value = "编号")
    private String id;
    @ApiModelProperty(value = "appid")
    private String appid;
    @ApiModelProperty(value = "微信全局编号")
    private String globalId;
    @ApiModelProperty(value = "调用key")
    private String callKey;
    @ApiModelProperty(value = "微信模板编号")
    private String templateId;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "消息模板内容")
    private String content;
    @ApiModelProperty(value = "例子")
    private String example;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "创建时间")
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

    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public String getCallKey() {
        return callKey;
    }

    public void setCallKey(String callKey) {
        this.callKey = callKey;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
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

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        SmallTemplate that = (SmallTemplate) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(appid, that.appid) &&
                Objects.equals(globalId, that.globalId) &&
                Objects.equals(callKey, that.callKey) &&
                Objects.equals(templateId, that.templateId) &&
                Objects.equals(title, that.title) &&
                Objects.equals(content, that.content) &&
                Objects.equals(example, that.example) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, appid, globalId, callKey, templateId, title, content, example, remark, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("appid", appid)
                .append("globalId", globalId)
                .append("callKey", callKey)
                .append("templateId", templateId)
                .append("title", title)
                .append("content", content)
                .append("example", example)
                .append("remark", remark)
                .append("createTime", createTime)
                .toString();
    }
}
