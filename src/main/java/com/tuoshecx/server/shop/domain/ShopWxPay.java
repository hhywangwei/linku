package com.tuoshecx.server.shop.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 店铺微信小程序支付信息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ShopWxPay {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("店铺编号")
    private String shopId;
    @ApiModelProperty("微信支付mchId")
    private String mchId;
    @ApiModelProperty("微信appid")
    private String appid;
    @ApiModelProperty("微信支付key")
    private String key;
    @ApiModelProperty("创建时间")
    private Date createTime;

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

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopWxPay shopWxPay = (ShopWxPay) o;
        return Objects.equals(id, shopWxPay.id) &&
                Objects.equals(shopId, shopWxPay.shopId) &&
                Objects.equals(mchId, shopWxPay.mchId) &&
                Objects.equals(appid, shopWxPay.appid) &&
                Objects.equals(key, shopWxPay.key) &&
                Objects.equals(createTime, shopWxPay.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, shopId, mchId, appid, key, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("shopId", shopId)
                .append("mchId", mchId)
                .append("appid", appid)
                .append("key", key)
                .append("createTime", createTime)
                .toString();
    }
}
