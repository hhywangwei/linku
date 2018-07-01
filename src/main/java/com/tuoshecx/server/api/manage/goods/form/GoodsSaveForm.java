package com.tuoshecx.server.api.manage.goods.form;

import com.tuoshecx.server.goods.domain.Goods;
import com.tuoshecx.server.goods.domain.GroupItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 新增商品提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel(value = "新增商品提交数据")
public class GoodsSaveForm {
    @NotBlank @Size(max = 20)
    @ApiModelProperty(value = "商品名", required = true)
    private String name;
    @Size(max = 200)
    @ApiModelProperty(value = "商品图标", required = true)
    private String icon;
    @ApiModelProperty(value = "商品图片")
    private String[] images;
    @ApiModelProperty(value = "商品描述摘要")
    private String summary;
    @ApiModelProperty(value = "商品描述")
    private String detail;
    @NotNull
    @ApiModelProperty(value = "商品价格", required = true)
    private Integer price;
    @NotNull @Min(value = 10) @Max(value = 100)
    @ApiModelProperty(value = "商品折扣", required = true)
    private Integer discount = 100;
    @ApiModelProperty(value = "商品分类")
    private String catalog;
    @Size(max = 20)
    @ApiModelProperty(value = "商品标签。例如，hot：热")
    private String tag;
    @NotNull @Min(0)
    @ApiModelProperty(value = "库存")
    private Integer stock;
    @ApiModelProperty(value = "显示排序")
    private Integer showOrder = 9999;
    @ApiModelProperty(value = "组合商品集合")
    @Valid
    private List<GroupItemForm> groupItems;

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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public List<GroupItemForm> getGroupItems() {
        return groupItems;
    }

    public void setGroupItems(List<GroupItemForm> groupItems) {
        this.groupItems = groupItems;
    }

    public Goods toDomain(String shopId){
        Goods t = new Goods();

        t.setShopId(shopId);
        t.setName(name);
        t.setIcon(icon);
        t.setCatalog(catalog);
        t.setSummary(summary);
        t.setDetail(detail);
        t.setDiscount(discount);
        t.setImages(images);
        t.setPrice(price);
        t.setDiscount(discount);
        t.setOpen(false);
        t.setSell(0);
        t.setStock(stock);
        t.setRealPrice((price * discount)/100);
        t.setShowOrder(showOrder);
        t.setDelete(false);
        t.setTag(tag);

        return t;
    }

    public List<GroupItem> toGroupItems(){
        if(groupItems == null){
            return Collections.emptyList();
        }

        return groupItems.stream()
                .map(this::toGroupItem)
                .collect(Collectors.toList());
    }

    private GroupItem toGroupItem(GroupItemForm e){
        GroupItem t = new GroupItem();
        t.setItemGoodsId(e.getGoodsId());
        t.setCount(e.getCount());
        return t;
    }

    public static class GroupItemForm {
        @NotBlank
        @ApiModelProperty(value = "组合商品编号", required = true)
        private String goodsId;
        @NotNull
        @ApiModelProperty(value = "商品数量", required = true)
        private int count;

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

    }
}
