package com.tuoshecx.server.wx.small.devops.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 小程序发布日志
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel(value = "小程序发布日志")
public class SmallDeployLog {
    @ApiModelProperty(value = "编号")
    private String id;
    @ApiModelProperty(value = "发布编号")
    private String deployId;
    @ApiModelProperty(value = "发布动作")
    private String action;
    @ApiModelProperty(value = "发布消息")
    private String message;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeployId() {
        return deployId;
    }

    public void setDeployId(String deployId) {
        this.deployId = deployId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
        SmallDeployLog that = (SmallDeployLog) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(deployId, that.deployId) &&
                Objects.equals(action, that.action) &&
                Objects.equals(message, that.message) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, deployId, action, message, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("deployId", deployId)
                .append("action", action)
                .append("message", message)
                .append("createTime", createTime)
                .toString();
    }
}
