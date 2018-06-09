package com.linku.server.order.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 订单
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("订单")
public class Order {
    @ApiModelProperty(value = "编号", required = true)
    private String id;
    @ApiModelProperty(value = "店铺编号", required = true)
    private String shopId;
    @ApiModelProperty(value = "用户编号", required = true)
    private String userId;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "联系电话")
    private String phone;
    @ApiModelProperty(value = "购买数量", required = true)
    private Integer count;
    @ApiModelProperty(value = "订单金额，单位分", required = true)
    private Integer total;
    @ApiModelProperty(value = "订单实际支付金额，单位分", required = true)
    private Integer payTotal;
    @ApiModelProperty(value = "订单描述")
    private String detail;
    @ApiModelProperty(value = "订单状态", required = true)
    private State state;
    @ApiModelProperty(value = "营销活动编号")
    private String marketingId;
    @ApiModelProperty(value = "营销活动类型")
    private String marketingType;
    @ApiModelProperty(value = "乐观锁", hidden = true)
    private Integer version;
    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;
    @ApiModelProperty(value = "支付时间")
    private Date payTime;
    @ApiModelProperty(value = "取消数据")
    private Date cancelTime;

    public enum State {
        WAIT, PAY, CANCEL, ROLL
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPayTotal() {
        return payTotal;
    }

    public void setPayTotal(Integer payTotal) {
        this.payTotal = payTotal;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getMarketingId() {
        return marketingId;
    }

    public void setMarketingId(String marketingId) {
        this.marketingId = marketingId;
    }

    public String getMarketingType() {
        return marketingType;
    }

    public void setMarketingType(String marketingType) {
        this.marketingType = marketingType;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(shopId, order.shopId) &&
                Objects.equals(userId, order.userId) &&
                Objects.equals(name, order.name) &&
                Objects.equals(phone, order.phone) &&
                Objects.equals(count, order.count) &&
                Objects.equals(total, order.total) &&
                Objects.equals(payTotal, order.payTotal) &&
                Objects.equals(detail, order.detail) &&
                state == order.state &&
                Objects.equals(marketingId, order.marketingId) &&
                marketingType == order.marketingType &&
                Objects.equals(version, order.version) &&
                Objects.equals(createTime, order.createTime) &&
                Objects.equals(payTime, order.payTime) &&
                Objects.equals(cancelTime, order.cancelTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, shopId, userId, name, phone, count, total, payTotal, detail, state, marketingId, marketingType, version, createTime, payTime, cancelTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("shopId", shopId)
                .append("userId", userId)
                .append("name", name)
                .append("phone", phone)
                .append("count", count)
                .append("total", total)
                .append("payTotal", payTotal)
                .append("detail", detail)
                .append("state", state)
                .append("marketingId", marketingId)
                .append("marketingType", marketingType)
                .append("version", version)
                .append("createTime", createTime)
                .append("payTime", payTime)
                .append("cancelTime", cancelTime)
                .toString();
    }
}
