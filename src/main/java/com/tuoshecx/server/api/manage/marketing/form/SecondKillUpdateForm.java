package com.tuoshecx.server.api.manage.marketing.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.marketing.domain.SecondKill;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 修改秒杀活动提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("修改秒杀活动提交数据")
public class SecondKillUpdateForm {
    @NotBlank
    private String id;
    @NotBlank
    @ApiModelProperty(value = "商品编号", required = true)
    private String goodsId;
    @NotBlank @Size(max = 20)
    @ApiModelProperty(value = "活动名称", required = true)
    private String name;
    @Size(max = 200)
    @ApiModelProperty(value = "活动图标")
    private String icon;
    @Size(max = 2000)
    @ApiModelProperty(value = "描述图片")
    private String[] images;
    @Size(max = 200)
    private String summary;
    @Size(max = 3000)
    @ApiModelProperty(value = "描述")
    private String detail;
    @Size(max = 500)
    @ApiModelProperty(value = "帮助文档")
    private String help;
    @NotNull
    @ApiModelProperty(value = "活动价格", required = true)
    private Integer price;
    @NotNull @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间", required = true)
    private Date startTime;
    @NotNull @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间", required = true)
    private Date endTime;
    @ApiModelProperty(value = "显示排序")
    private Integer showOrder = 9999;

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

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public SecondKill toDomain(String shopId) {
        SecondKill t = new SecondKill();

        t.setId(id);
        if(endTime.before(startTime)){
            throw new BaseException("结束时间不能小于开始时间");
        }
        Date now = new Date();
        boolean isOpen = (startTime.before(now) && endTime.after(now));
        t.setShopId(shopId);
        t.setName(name);
        t.setIcon(icon);
        t.setImages(images);
        t.setSummary(summary);
        t.setDetail(detail);
        t.setGoodsId(getGoodsId());
        t.setHelp(help);
        t.setPrice(price);
        t.setStartTime(startTime);
        t.setEndTime(endTime);
        t.setShowOrder(showOrder);
        t.setOpen(isOpen);

        return t;
    }
}
