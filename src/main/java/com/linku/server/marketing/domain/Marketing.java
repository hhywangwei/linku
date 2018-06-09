package com.linku.server.marketing.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * 营销活动
 *
 * @author WangWei
 */
public class Marketing {
    @ApiModelProperty(value = "编号", required = true)
    private String id;
    @ApiModelProperty(value = "店铺编号", required = true)
    private String shopId;
    @ApiModelProperty(value = "商品编号", required = true)
    private String goodsId;
    @ApiModelProperty(value = "活动名称", required = true)
    private String name;
    @ApiModelProperty(value = "活动图标")
    private String icon;
    @ApiModelProperty(value = "描述图片")
    private String[] images;
    @ApiModelProperty(value = "描述摘要")
    private String summary;
    @ApiModelProperty(value = "描述")
    private String detail;
    @ApiModelProperty(value = "帮助文档")
    private String help;
    @ApiModelProperty(value = "活动价格", required = true)
    private Integer price;
    @ApiModelProperty(value = "开始时间", required = true)
    private Date startTime;
    @ApiModelProperty(value = "结束时间", required = true)
    private Date endTime;
    @ApiModelProperty(value = "true:活动开始", required = true)
    private Boolean open;
    @ApiModelProperty(value = "显示排序", required = true)
    private Integer showOrder = 9999;
    @ApiModelProperty(value = "更新时间", required = true)
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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
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

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
        Marketing marketing = (Marketing) o;
        return Objects.equals(id, marketing.id) &&
                Objects.equals(shopId, marketing.shopId) &&
                Objects.equals(goodsId, marketing.goodsId) &&
                Objects.equals(name, marketing.name) &&
                Objects.equals(icon, marketing.icon) &&
                Arrays.equals(images, marketing.images) &&
                Objects.equals(summary, marketing.summary) &&
                Objects.equals(detail, marketing.detail) &&
                Objects.equals(help, marketing.help) &&
                Objects.equals(price, marketing.price) &&
                Objects.equals(startTime, marketing.startTime) &&
                Objects.equals(endTime, marketing.endTime) &&
                Objects.equals(open, marketing.open) &&
                Objects.equals(showOrder, marketing.showOrder) &&
                Objects.equals(updateTime, marketing.updateTime) &&
                Objects.equals(createTime, marketing.createTime);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id, shopId, goodsId, name, icon, summary, detail, help, price, startTime, endTime, open, showOrder, updateTime, createTime);
        result = 31 * result + Arrays.hashCode(images);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("shopId", shopId)
                .append("goodsId", goodsId)
                .append("name", name)
                .append("icon", icon)
                .append("images", images)
                .append("summary", summary)
                .append("detail", detail)
                .append("help", help)
                .append("price", price)
                .append("startTime", startTime)
                .append("endTime", endTime)
                .append("open", open)
                .append("showOrder", showOrder)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
