package com.tuoshecx.server.marketing.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 秒杀记录
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class SecondKillRecord {
    @ApiModelProperty(value = "编号", required = true)
    private String id;
    @ApiModelProperty(value = "所属店铺编号", required = true)
    private String shopId;
    @ApiModelProperty(value = "商品编号", required = true)
    private String goodsId;
    @ApiModelProperty(value = "营销活动编号", required = true)
    private String marketingId;
    @ApiModelProperty(value = "订单编号", required = true)
    private String orderId;
    @ApiModelProperty(value = "活动名称")
    private String name;
    @ApiModelProperty(value = "活动图片")
    private String icon;
    @ApiModelProperty(value = "价格", required = true)
    private Integer price;
    @ApiModelProperty(value = "购买用户编号", required = true)
    private String userId;
    @ApiModelProperty(value = "购买用户昵称")
    private String nickname;
    @ApiModelProperty(value = "购买用户头像")
    private String headImg;
    @ApiModelProperty(value = "联系电话")
    private String phone;
    @ApiModelProperty(value = "状态", required = true)
    private State state = State.WAIT;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public enum State {
        WAIT, PAY, CANCEL
    }

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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getMarketingId() {
        return marketingId;
    }

    public void setMarketingId(String marketingId) {
        this.marketingId = marketingId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
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
        if (!(o instanceof SecondKillRecord)) return false;
        SecondKillRecord that = (SecondKillRecord) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(shopId, that.shopId) &&
                Objects.equals(goodsId, that.goodsId) &&
                Objects.equals(marketingId, that.marketingId) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(icon, that.icon) &&
                Objects.equals(price, that.price) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(nickname, that.nickname) &&
                Objects.equals(headImg, that.headImg) &&
                Objects.equals(phone, that.phone) &&
                state == that.state &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, shopId, goodsId, marketingId, orderId, name, icon, price, userId, nickname, headImg, phone, state, updateTime, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("shopId", shopId)
                .append("goodsId", goodsId)
                .append("marketingId", marketingId)
                .append("orderId", orderId)
                .append("name", name)
                .append("icon", icon)
                .append("price", price)
                .append("userId", userId)
                .append("nickname", nickname)
                .append("headImg", headImg)
                .append("phone", phone)
                .append("state", state)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
