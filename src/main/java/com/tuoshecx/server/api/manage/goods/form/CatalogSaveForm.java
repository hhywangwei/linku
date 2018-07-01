package com.tuoshecx.server.api.manage.goods.form;

import com.tuoshecx.server.goods.domain.Catalog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 新增商品分类提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("新增商品分类提交数据")
public class CatalogSaveForm {
    @NotBlank @Size(max = 20)
    @ApiModelProperty(value = "商品分类名称", required = true)
    private String name;
    @ApiModelProperty(value = "商品分类图标")
    private String icon;
    @Min(0)
    @ApiModelProperty(value = "显示顺序")
    private Integer showOrder = 9999;

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

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public Catalog toDomain(String shopId){
        Catalog t = new Catalog();

        t.setShopId(shopId);
        t.setName(name);
        t.setIcon(icon);
        t.setShowOrder(showOrder);

        return t;
    }
}
