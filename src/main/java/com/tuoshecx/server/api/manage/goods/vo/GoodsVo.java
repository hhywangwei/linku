package com.tuoshecx.server.api.manage.goods.vo;

import com.tuoshecx.server.goods.domain.Goods;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * 商品信息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("商品信息")
public class GoodsVo {
    @ApiModelProperty(value = "商品编号")
    private final String id;
    @ApiModelProperty(value = "所属店铺编号")
    private final String shopId;
    @ApiModelProperty(value = "商品名称")
    private final String name;
    @ApiModelProperty(value = "商品图标")
    private final String icon;
    @ApiModelProperty(value = "商品描述图片")
    private final String[] images;
    @ApiModelProperty(value = "商品摘要")
    private final String summary;
    @ApiModelProperty(value = "商品描述")
    private final String detail;
    @ApiModelProperty(value = "商品价格")
    private final Integer price;
    @ApiModelProperty(value = "商品实际价格")
    private final Integer realPrice;
    @ApiModelProperty(value = "商品折扣")
    private final Integer discount;
    @ApiModelProperty(value = "商品分类")
    private final String catalog;
    @ApiModelProperty(value = "商品标签")
    private final String tag;
    @ApiModelProperty(value = "销售量")
    private final Integer sell;
    @ApiModelProperty(value = "库存")
    private final Integer stock;
    @ApiModelProperty(value = "是否是组合商品，true:组合商品")
    private final Boolean isGroup;
    @ApiModelProperty(value = "是否是上架商品，true:上架商品")
    private final Boolean isOpen;
    @ApiModelProperty(value = "显示排序")
    private final Integer showOrder;
    @ApiModelProperty(value = "商品是否删除")
    private final Boolean isDelete;
    @ApiModelProperty(value = "更新时间")
    private final Date updateTime;
    @ApiModelProperty(value = "创建时间")
    private final Date createTime;
    @ApiModelProperty(value = "组合的商品")
    private final List<GroupItemVo> groupItems;

    public GoodsVo(Goods t, List<GroupItemVo> groupItems){
        this.id = t.getId();
        this.shopId = t.getShopId();
        this.name = t.getName();
        this.icon = t.getIcon();
        this.images = t.getImages();
        this.summary = t.getSummary();
        this.detail = t.getDetail();
        this.price = t.getPrice();
        this.realPrice = t.getRealPrice();
        this.discount = t.getDiscount();
        this.catalog = t.getCatalog();
        this.tag = t.getTag();
        this.sell = t.getSell();
        this.stock = t.getStock();
        this.isGroup = t.getGroup();
        this.isOpen = t.getOpen();
        this.showOrder = t.getShowOrder();
        this.isDelete = t.getDelete();
        this.updateTime = t.getUpdateTime();
        this.createTime = t.getCreateTime();
        this.groupItems = groupItems;
    }

    public String getId() {
        return id;
    }

    public String getShopId() {
        return shopId;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String[] getImages() {
        return images;
    }

    public String getSummary() {
        return summary;
    }

    public String getDetail() {
        return detail;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getRealPrice() {
        return realPrice;
    }

    public Integer getDiscount() {
        return discount;
    }

    public String getCatalog() {
        return catalog;
    }

    public String getTag() {
        return tag;
    }

    public Integer getSell() {
        return sell;
    }

    public Integer getStock() {
        return stock;
    }

    public Boolean getGroup() {
        return isGroup;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public List<GroupItemVo> getGroupItems() {
        return groupItems;
    }

    public static class GroupItemVo{
        private final Goods goods;
        private final int count;

        public GroupItemVo(Goods goods, int count) {
            this.goods = goods;
            this.count = count;
        }

        public Goods getGoods() {
            return goods;
        }

        public int getCount() {
            return count;
        }
    }
}
