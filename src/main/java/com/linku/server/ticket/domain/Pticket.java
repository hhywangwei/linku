package com.linku.server.ticket.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 优惠券
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class Pticket {
    @ApiModelProperty(value = "编号", required = true)
    private String id;
    @ApiModelProperty(value = "优惠券编码", required = true)
    private String code;
    @ApiModelProperty(value = "所属店铺编号", required = true)
    private String shopId;
    @ApiModelProperty(value = "用户编号", required = true)
    private String userId;
    @ApiModelProperty(value = "用户姓名")
    private String name;
    @ApiModelProperty(value = "用户头像")
    private String headImg;
    @ApiModelProperty(value = "优惠金额", required = true)
    private Integer money;
    @ApiModelProperty(value = "状态")
    private State state = State.WAIT;
    @ApiModelProperty(value = "版本号", hidden = true)
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
        WAIT, USE, EXPIRE
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

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
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
        if (!(o instanceof Pticket)) return false;
        Pticket pticket = (Pticket) o;
        return Objects.equals(id, pticket.id) &&
                Objects.equals(code, pticket.code) &&
                Objects.equals(shopId, pticket.shopId) &&
                Objects.equals(userId, pticket.userId) &&
                Objects.equals(name, pticket.name) &&
                Objects.equals(headImg, pticket.headImg) &&
                Objects.equals(money, pticket.money) &&
                state == pticket.state &&
                Objects.equals(version, pticket.version) &&
                Objects.equals(fromDate, pticket.fromDate) &&
                Objects.equals(toDate, pticket.toDate) &&
                Objects.equals(createTime, pticket.createTime) &&
                Objects.equals(useTime, pticket.useTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, code, shopId, userId, name, headImg, money, state, version, fromDate, toDate, createTime, useTime);
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
                .append("money", money)
                .append("state", state)
                .append("version", version)
                .append("fromDate", fromDate)
                .append("toDate", toDate)
                .append("createTime", createTime)
                .append("useTime", useTime)
                .toString();
    }
}
