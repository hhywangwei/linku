package com.linku.server.shop.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

/**
 * 店铺微信配置信息
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("店铺微信配置信息")
public class WxConfigure {
    @ApiModelProperty(value = "编号")
    private String id;
    @ApiModelProperty(value = "店铺编号")
    private String shopId;
    @ApiModelProperty(value = "微信appid")
    private String appid;

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

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WxConfigure that = (WxConfigure) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(shopId, that.shopId) &&
                Objects.equals(appid, that.appid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, shopId, appid);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("shopId", shopId)
                .append("appid", appid)
                .toString();
    }
}
