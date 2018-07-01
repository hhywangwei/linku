package com.tuoshecx.server.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 用户
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("用户")
public class User {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("店铺编号")
    private String shopId;
    @JsonIgnore
    @ApiModelProperty("微信小程序appid")
    private String appid;
    @ApiModelProperty("微信openid")
    private String openid;
    @ApiModelProperty("微信unionid")
    private String unionid;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty(value = "登陆密码", hidden = true)
    @JsonIgnore
    private String password;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("昵称")
    private String nickname;
    @ApiModelProperty("联系电话")
    private String phone;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("头像")
    private String headImg;
    @ApiModelProperty("省份")
    private String province;
    @ApiModelProperty("城市")
    private String city;
    @ApiModelProperty("国家")
    private String country;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("创建时间")
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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(shopId, user.shopId) &&
                Objects.equals(appid, user.appid) &&
                Objects.equals(openid, user.openid) &&
                Objects.equals(unionid, user.unionid) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                Objects.equals(nickname, user.nickname) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(sex, user.sex) &&
                Objects.equals(headImg, user.headImg) &&
                Objects.equals(province, user.province) &&
                Objects.equals(city, user.city) &&
                Objects.equals(country, user.country) &&
                Objects.equals(updateTime, user.updateTime) &&
                Objects.equals(createTime, user.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, shopId, appid, openid, unionid, username, password, name, nickname, phone, sex, headImg, province, city, country, updateTime, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("shopId", shopId)
                .append("appid", appid)
                .append("openid", openid)
                .append("unionid", unionid)
                .append("username", username)
                .append("password", password)
                .append("name", name)
                .append("nickname", nickname)
                .append("phone", phone)
                .append("sex", sex)
                .append("headImg", headImg)
                .append("province", province)
                .append("city", city)
                .append("country", country)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
