package com.tuoshecx.server.api.sys.wx.form;

import com.tuoshecx.server.shop.domain.ShopWxPay;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 修改店铺微信支付配置
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("修改店铺微信支付配置")
public class ShopWxPayUpdateForm extends ShopWxPaySaveForm {
    @NotBlank
    @ApiModelProperty("编号")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public ShopWxPay toDomain() {
        ShopWxPay t = super.toDomain();

        t.setId(id);

        return t;
    }
}
