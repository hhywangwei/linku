package com.tuoshecx.server.api.manage.goods.form;

import com.tuoshecx.server.goods.domain.Catalog;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;

/**
 * 修改商品提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("修改商品提交数据")
public class CatalogUpdateForm extends CatalogSaveForm {
    @NotBlank
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Catalog toDomain(String shopId) {
        Catalog t = super.toDomain(shopId);
        t.setId(id);
        return t;
    }
}
