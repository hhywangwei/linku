package com.linku.server.api.manage.goods.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 增加商品库存
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("增加商品库存")
public class GoodsIncStockForm {
    @NotBlank
    @ApiModelProperty(value = "商品编号", required = true)
    private String id;
    @ApiModelProperty(value = "增加数量")
    private Integer count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
