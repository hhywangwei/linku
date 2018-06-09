package com.linku.server.shop.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 店铺通知
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("店铺通知")
public class Notice {
    @ApiModelProperty(value = "编号", required = true)
    private String id;
    @ApiModelProperty(value = "店铺编号", required = true)
    private String shopId;
    @ApiModelProperty(value = "消息类型", required = true)
    private String type;
    @ApiModelProperty(value = "消息标题", required = true)
    private String title;
    @ApiModelProperty(value = "消息内容", required = true)
    private String content;
    @ApiModelProperty(value = "访问地址")
    private String uri;
    @ApiModelProperty(value = "消息来源")
    private String source;
    @ApiModelProperty(value = "true:已读消息", required = true)
    private Boolean read;
    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;
    @ApiModelProperty(value = "读消息时间")
    private Date readTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notice notice = (Notice) o;
        return Objects.equals(id, notice.id) &&
                Objects.equals(shopId, notice.shopId) &&
                Objects.equals(type, notice.type) &&
                Objects.equals(title, notice.title) &&
                Objects.equals(content, notice.content) &&
                Objects.equals(uri, notice.uri) &&
                Objects.equals(source, notice.source) &&
                Objects.equals(read, notice.read) &&
                Objects.equals(createTime, notice.createTime) &&
                Objects.equals(readTime, notice.readTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, shopId, type, title, content, uri, source, read, createTime, readTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("shopId", shopId)
                .append("type", type)
                .append("title", title)
                .append("content", content)
                .append("uri", uri)
                .append("source", source)
                .append("read", read)
                .append("createTime", createTime)
                .append("readTime", readTime)
                .toString();
    }
}