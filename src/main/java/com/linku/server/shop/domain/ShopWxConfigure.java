package com.linku.server.shop.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 店铺托管小程序配置信息
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class ShopWxConfigure {
    private String id;
    private String shopId;
    private String appid;
    private String nickname;
    private String headImg;
    private Integer serviceTypeInfo;
    private Integer verifyTypeInfo;
    private String username;
    private String name;
    private String businessInfo;
    private String qrcodeUrl;
    private String authorizationInfo;
    private Boolean authorization;
    private String miniProgramInfo;
    private Date updateTime;
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
        ShopWxConfigure that = (ShopWxConfigure) o;
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
