package com.linku.server.goods.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 组合商品明细
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("组合商品明细")
public class GroupItem {
    @ApiModelProperty(value = "编号", required = true)
    private String id;
    @ApiModelProperty(value = "商品编号", required = true)
    private String goodsId;
    @ApiModelProperty(value = "被组合商品编号", required = true)
    private String itemGoodsId;
    @ApiModelProperty(value = "商品名称", required = true)
    private String name;
    @ApiModelProperty(value = "商品图标")
    private String icon;
    @ApiModelProperty(value = "商品数量", required = true)
    private Integer count;
    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getItemGoodsId() {
        return itemGoodsId;
    }

    public void setItemGoodsId(String itemGoodsId) {
        this.itemGoodsId = itemGoodsId;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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
        GroupItem groupItem = (GroupItem) o;
        return Objects.equals(id, groupItem.id) &&
                Objects.equals(goodsId, groupItem.goodsId) &&
                Objects.equals(itemGoodsId, groupItem.itemGoodsId) &&
                Objects.equals(name, groupItem.name) &&
                Objects.equals(icon, groupItem.icon) &&
                Objects.equals(count, groupItem.count) &&
                Objects.equals(createTime, groupItem.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, goodsId, itemGoodsId, name, icon, count, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("goodsId", goodsId)
                .append("itemGoodsId", itemGoodsId)
                .append("name", name)
                .append("icon", icon)
                .append("count", count)
                .append("createTime", createTime)
                .toString();
    }
}
