package com.tuoshecx.server.api.sys.wx.form;

import com.tuoshecx.server.shop.domain.ShopWxPay;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 新增店铺微信支付配置
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("新增店铺微信支付配置")
public class ShopWxPaySaveForm {
    @NotBlank
    @ApiModelProperty(value = "店铺编号", required = true)
    private String shopId;
    @NotBlank
    @ApiModelProperty(value = "支付mchId", required = true)
    private String mchId;
    @ApiModelProperty(value = "支付key")
    private String key;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ShopWxPay toDomain(){
        ShopWxPay t = new ShopWxPay();

        t.setShopId(shopId);
        t.setMchId(mchId);
        t.setKey(key);

        return t;
    }
}
