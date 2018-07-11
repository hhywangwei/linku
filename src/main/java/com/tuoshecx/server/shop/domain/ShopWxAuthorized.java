package com.tuoshecx.server.shop.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 店铺托管小程序信息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ShopWxAuthorized {
    @ApiModelProperty(value = "编号")
    private String id;
    @ApiModelProperty(value = "店铺编号")
    private String shopId;
    @ApiModelProperty(value = "微信appid")
    private String appid;
    @ApiModelProperty(value = "昵称")
    private String nickname;
    @ApiModelProperty(value = "头像")
    private String headImg;
    @ApiModelProperty(hidden = true)
    private Integer serviceTypeInfo;
    @ApiModelProperty(hidden = true )
    private Integer verifyTypeInfo;
    @ApiModelProperty(value = "授权方公众号的原始ID")
    private String username;
    @ApiModelProperty(value = "公众号的主体名称")
    private String name;
    private String businessInfo;
    @ApiModelProperty(value = "二维码图片的URL")
    private String qrcodeUrl;
    @ApiModelProperty(value = "授权信息")
    private String authorizationInfo;
    private Boolean authorization;
    @ApiModelProperty(value = "小程序配置信息")
    private String miniProgramInfo;
    @ApiModelProperty(value = "修改时间")
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Integer getServiceTypeInfo() {
        return serviceTypeInfo;
    }

    public void setServiceTypeInfo(Integer serviceTypeInfo) {
        this.serviceTypeInfo = serviceTypeInfo;
    }

    public Integer getVerifyTypeInfo() {
        return verifyTypeInfo;
    }

    public void setVerifyTypeInfo(Integer verifyTypeInfo) {
        this.verifyTypeInfo = verifyTypeInfo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessInfo() {
        return businessInfo;
    }

    public void setBusinessInfo(String businessInfo) {
        this.businessInfo = businessInfo;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public String getAuthorizationInfo() {
        return authorizationInfo;
    }

    public void setAuthorizationInfo(String authorizationInfo) {
        this.authorizationInfo = authorizationInfo;
    }

    public Boolean getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Boolean authorization) {
        this.authorization = authorization;
    }

    public String getMiniProgramInfo() {
        return miniProgramInfo;
    }

    public void setMiniProgramInfo(String miniProgramInfo) {
        this.miniProgramInfo = miniProgramInfo;
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
        ShopWxAuthorized that = (ShopWxAuthorized) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(shopId, that.shopId) &&
                Objects.equals(appid, that.appid) &&
                Objects.equals(nickname, that.nickname) &&
                Objects.equals(headImg, that.headImg) &&
                Objects.equals(serviceTypeInfo, that.serviceTypeInfo) &&
                Objects.equals(verifyTypeInfo, that.verifyTypeInfo) &&
                Objects.equals(username, that.username) &&
                Objects.equals(name, that.name) &&
                Objects.equals(businessInfo, that.businessInfo) &&
                Objects.equals(qrcodeUrl, that.qrcodeUrl) &&
                Objects.equals(authorizationInfo, that.authorizationInfo) &&
                Objects.equals(authorization, that.authorization) &&
                Objects.equals(miniProgramInfo, that.miniProgramInfo) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, shopId, appid, nickname, headImg, serviceTypeInfo, verifyTypeInfo, username, name, businessInfo, qrcodeUrl, authorizationInfo, authorization, miniProgramInfo, updateTime, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("shopId", shopId)
                .append("appid", appid)
                .append("nickname", nickname)
                .append("headImg", headImg)
                .append("serviceTypeInfo", serviceTypeInfo)
                .append("verifyTypeInfo", verifyTypeInfo)
                .append("username", username)
                .append("name", name)
                .append("businessInfo", businessInfo)
                .append("qrcodeUrl", qrcodeUrl)
                .append("authorizationInfo", authorizationInfo)
                .append("authorization", authorization)
                .append("miniProgramInfo", miniProgramInfo)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
