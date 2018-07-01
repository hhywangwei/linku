package com.tuoshecx.server.goods.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * 商品
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("商品")
public class Goods {
    @ApiModelProperty(value = "编号", required = true)
    private String id;
    @ApiModelProperty(value = "店铺编号", required = true)
    private String shopId;
    @ApiModelProperty(value = "商品名称", required = true)
    private String name;
    @ApiModelProperty(value = "商品图标")
    private String icon;
    @ApiModelProperty(value = "显示图片")
    private String[] images;
    @ApiModelProperty(value = "商品摘要")
    private String summary;
    @ApiModelProperty(value = "商品描述")
    private String detail;
    @ApiModelProperty(value = "标价，单位分", required = true)
    private Integer price;
    @ApiModelProperty(value = "售价，单位分", required = true)
    private Integer realPrice;
    @ApiModelProperty(value = "折扣")
    private Integer discount;
    @ApiModelProperty(value = "商品分类")
    private String catalog;
    @ApiModelProperty(value = "标签")
    private String tag;
    @ApiModelProperty(value = "销售数量", required = true)
    private Integer sell;
    @ApiModelProperty(value = "库存", required = true)
    private Integer stock;
    @ApiModelProperty(value = "true:组合商品", required = true)
    private Boolean group;
    @ApiModelProperty(value = "true:商品上级", required = true)
    private Boolean open;
    @ApiModelProperty(value = "显示排序", required = true)
    private Integer showOrder;
    @ApiModelProperty(value = "true:商品已删除", required = true)
    private Boolean delete;
    @ApiModelProperty(value = "版本号", hidden = true)
    private Integer version;
    @ApiModelProperty(value = "修改时间", required = true)
    private Date updateTime;
    @ApiModelProperty(value = "创建时间", required = true)
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

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(Integer realPrice) {
        this.realPrice = realPrice;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getSell() {
        return sell;
    }

    public void setSell(Integer sell) {
        this.sell = sell;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Boolean getGroup() {
        return group;
    }

    public void setGroup(Boolean group) {
        this.group = group;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        Goods goods = (Goods) o;
        return Objects.equals(id, goods.id) &&
                Objects.equals(shopId, goods.shopId) &&
                Objects.equals(name, goods.name) &&
                Objects.equals(icon, goods.icon) &&
                Arrays.equals(images, goods.images) &&
                Objects.equals(summary, goods.summary) &&
                Objects.equals(detail, goods.detail) &&
                Objects.equals(price, goods.price) &&
                Objects.equals(realPrice, goods.realPrice) &&
                Objects.equals(discount, goods.discount) &&
                Objects.equals(catalog, goods.catalog) &&
                Objects.equals(tag, goods.tag) &&
                Objects.equals(sell, goods.sell) &&
                Objects.equals(stock, goods.stock) &&
                Objects.equals(group, goods.group) &&
                Objects.equals(open, goods.open) &&
                Objects.equals(showOrder, goods.showOrder) &&
                Objects.equals(delete, goods.delete) &&
                Objects.equals(version, goods.version) &&
                Objects.equals(updateTime, goods.updateTime) &&
                Objects.equals(createTime, goods.createTime);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id, shopId, name, icon, summary, detail, price, realPrice, discount, catalog, tag, sell, stock, group, open, showOrder, delete, version, updateTime, createTime);
        result = 31 * result + Arrays.hashCode(images);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("shopId", shopId)
                .append("name", name)
                .append("icon", icon)
                .append("images", images)
                .append("summary", summary)
                .append("detail", detail)
                .append("price", price)
                .append("realPrice", realPrice)
                .append("discount", discount)
                .append("catalog", catalog)
                .append("tag", tag)
                .append("sell", sell)
                .append("stock", stock)
                .append("group", group)
                .append("open", open)
                .append("showOrder", showOrder)
                .append("delete", delete)
                .append("version", version)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
