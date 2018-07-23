package com.tuoshecx.server.ticket.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 电子券
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("电子券")
public class Eticket {
    @ApiModelProperty(value = "编号", required = true)
    private String id;
    @ApiModelProperty(value = "电子券编码", required = true)
    private String code;
    @ApiModelProperty(value = "所属店铺编号", required = true)
    private String shopId;
    @ApiModelProperty(value = "用户编号", required = true)
    private String userId;
    @ApiModelProperty(value = "用户姓名")
    private String name;
    @ApiModelProperty(value = "用户头像")
    private String headImg;
    @ApiModelProperty(value = "订单编号", required = true)
    private String orderId;
    @ApiModelProperty(value = "商品编号", required = true)
    private String goodsId;
    @ApiModelProperty(value = "商品名称")
    private String goodsName;
    @ApiModelProperty(value = "商品图标")
    private String goodsIcon;
    @ApiModelProperty(value = "状态")
    private State state = State.WAIT;
    @ApiModelProperty(value = "版本号", hidden = true)
    @JsonIgnore
    private Integer version;
    @ApiModelProperty(value = "有效开始时间", required = true)
    private Date fromDate;
    @ApiModelProperty(value = "有效结束时间", required = true)
    private Date toDate;
    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;
    @ApiModelProperty(value =  "使用时间")
    private Date useTime;

    public enum State {
        WAIT, USE, EXPIRE, PRESENT
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsIcon() {
        return goodsIcon;
    }

    public void setGoodsIcon(String goodsIcon) {
        this.goodsIcon = goodsIcon;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Eticket eticket = (Eticket) o;
        return Objects.equals(id, eticket.id) &&
                Objects.equals(code, eticket.code) &&
                Objects.equals(shopId, eticket.shopId) &&
                Objects.equals(userId, eticket.userId) &&
                Objects.equals(name, eticket.name) &&
                Objects.equals(headImg, eticket.headImg) &&
                Objects.equals(orderId, eticket.orderId) &&
                Objects.equals(goodsId, eticket.goodsId) &&
                Objects.equals(goodsName, eticket.goodsName) &&
                Objects.equals(goodsIcon, eticket.goodsIcon) &&
                state == eticket.state &&
                Objects.equals(version, eticket.version) &&
                Objects.equals(fromDate, eticket.fromDate) &&
                Objects.equals(toDate, eticket.toDate) &&
                Objects.equals(createTime, eticket.createTime) &&
                Objects.equals(useTime, eticket.useTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, code, shopId, userId, name, headImg, orderId, goodsId, goodsName, goodsIcon, state, version, fromDate, toDate, createTime, useTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("code", code)
                .append("shopId", shopId)
                .append("userId", userId)
                .append("name", name)
                .append("headImg", headImg)
                .append("orderId", orderId)
                .append("goodsId", goodsId)
                .append("goodsName", goodsName)
                .append("goodsIcon", goodsIcon)
                .append("state", state)
                .append("version", version)
                .append("fromDate", fromDate)
                .append("toDate", toDate)
                .append("createTime", createTime)
                .append("useTime", useTime)
                .toString();
    }
}
