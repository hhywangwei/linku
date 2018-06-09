package com.linku.server.api.manage.goods.form;

import com.linku.server.goods.domain.Goods;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 更新商品提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("更新商品提交数据")
public class GoodsUpdateForm extends GoodsSaveForm{
    @NotBlank
    @ApiModelProperty(value = "商品编号", required = true)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Goods toDomain(String shopId) {
        Goods t = super.toDomain(shopId);
        t.setId(id);
        return t;
    }
}
