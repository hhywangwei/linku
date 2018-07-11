package com.tuoshecx.server.wx.small.devops.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 小程序部署信息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("部署小程序信息")
public class SmallDeploy {
    @ApiModelProperty(value = "编号")
    private String id;
    @ApiModelProperty(value = "店铺编号")
    private String shopId;
    @ApiModelProperty(value = "公众号appid")
    private String appid;
    @ApiModelProperty(value = "是否设置域名")
    private Boolean setDomain;
    @ApiModelProperty(value = "发布小程序模板编号")
    private Integer templateId;
    @ApiModelProperty(value = "部署状态", allowableValues = "wait, audit, pass, refuse")
    private String state;
    @ApiModelProperty(value = "发布备注")
    private String remark;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

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

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Boolean getSetDomain() {
        return setDomain;
    }

    public void setSetDomain(Boolean setDomain) {
        this.setDomain = setDomain;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        SmallDeploy that = (SmallDeploy) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(shopId, that.shopId) &&
                Objects.equals(appid, that.appid) &&
                Objects.equals(setDomain, that.setDomain) &&
                Objects.equals(templateId, that.templateId) &&
                Objects.equals(state, that.state) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, shopId, appid, setDomain, templateId, state, remark, updateTime, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("shopId", shopId)
                .append("appid", appid)
                .append("setDomain", setDomain)
                .append("templateId", templateId)
                .append("state", state)
                .append("remark", remark)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
