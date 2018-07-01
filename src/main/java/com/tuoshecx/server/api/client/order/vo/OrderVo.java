package com.tuoshecx.server.api.client.order.vo;

import com.tuoshecx.server.order.domain.Order;
import com.tuoshecx.server.order.domain.OrderItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 订单输出数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel(value = "订单输出数据")
public class OrderVo {
    @ApiModelProperty(value = "订单信息", required = true)
    private final Order order;
    @ApiModelProperty(value = "订单明细", required = true)
    private final List<OrderItem> items;

    public OrderVo(Order order, List<OrderItem> items){
        this.order = order;
        this.items = items;
    }

    public Order getOrder() {
        return order;
    }

    public List<OrderItem> getItems() {
        return items;
    }
}
