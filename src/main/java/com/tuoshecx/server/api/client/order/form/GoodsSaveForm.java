package com.tuoshecx.server.api.client.order.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 购买商品提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class GoodsSaveForm {
    @NotEmpty
    @ApiModelProperty(value = "购买商品编号集合", required = true)
    private List<String> goodsIds;
    @NotEmpty
    @ApiModelProperty(value = "购买商品数量", required = true)
    private List<Integer> counts;

    public List<String> getGoodsIds() {
        return goodsIds;
    }

    public void setGoodsIds(List<String> goodsIds) {
        this.goodsIds = goodsIds;
    }

    public List<Integer> getCounts() {
        return counts;
    }

    public void setCounts(List<Integer> counts) {
        this.counts = counts;
    }
}
