package com.tuoshecx.server.order.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

/**
 * 订单明细
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("订单明细")
public class OrderItem {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("所属订单编号")
    private String orderId;
    @ApiModelProperty("商品编号")
    private String goodsId;
    @ApiModelProperty("商品名称")
    private String name;
    @ApiModelProperty("商品图标")
    private String icon;
    @ApiModelProperty("销售价格")
    private Integer price;
    @ApiModelProperty("购买商品数量")
    private Integer count;
    @ApiModelProperty("支付金额")
    private Integer total;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(id, orderItem.id) &&
                Objects.equals(orderId, orderItem.orderId) &&
                Objects.equals(goodsId, orderItem.goodsId) &&
                Objects.equals(name, orderItem.name) &&
                Objects.equals(icon, orderItem.icon) &&
                Objects.equals(price, orderItem.price) &&
                Objects.equals(count, orderItem.count) &&
                Objects.equals(total, orderItem.total);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, orderId, goodsId, name, icon, price, count, total);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("orderId", orderId)
                .append("goodsId", goodsId)
                .append("name", name)
                .append("icon", icon)
                .append("price", price)
                .append("count", count)
                .append("total", total)
                .toString();
    }
}
