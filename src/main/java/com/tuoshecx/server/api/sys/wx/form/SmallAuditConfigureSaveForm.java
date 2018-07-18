package com.tuoshecx.server.api.sys.wx.form;


import com.tuoshecx.server.wx.small.devops.domain.SmallAuditConfigure;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 新增小程序审核配置
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class SmallAuditConfigureSaveForm {
    @NotBlank
    @ApiModelProperty(value = "店铺编号")
    private String shopId;
    @NotBlank
    @ApiModelProperty(value = "标题")
    private String title;
    @NotBlank
    @ApiModelProperty(value = "标签")
    private String tag;
    @NotBlank
    @ApiModelProperty(value = "address")
    private String address;
    @NotNull
    @ApiModelProperty(value = "第一分类编号")
    private Integer firstId;
    @NotBlank
    @ApiModelProperty(value = "第一分类")
    private String firstClass;
    @NotNull
    @ApiModelProperty(value = "第二分类编号")
    private Integer secondId;
    @NotBlank
    @ApiModelProperty(value = "第二分类")
    private String secondClass;
    @ApiModelProperty(value = "第三分类编号")
    private Integer thirdId;
    @ApiModelProperty(value = "第三分类")
    private String thirdClass;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getFirstId() {
        return firstId;
    }

    public void setFirstId(Integer firstId) {
        this.firstId = firstId;
    }

    public String getFirstClass() {
        return firstClass;
    }

    public void setFirstClass(String firstClass) {
        this.firstClass = firstClass;
    }

    public Integer getSecondId() {
        return secondId;
    }

    public void setSecondId(Integer secondId) {
        this.secondId = secondId;
    }

    public String getSecondClass() {
        return secondClass;
    }

    public void setSecondClass(String secondClass) {
        this.secondClass = secondClass;
    }

    public Integer getThirdId() {
        return thirdId;
    }

    public void setThirdId(Integer thirdId) {
        this.thirdId = thirdId;
    }

    public String getThirdClass() {
        return thirdClass;
    }

    public void setThirdClass(String thirdClass) {
        this.thirdClass = thirdClass;
    }

    public SmallAuditConfigure toDomain(){
        SmallAuditConfigure t = new SmallAuditConfigure();

        t.setShopId(shopId);
        t.setTitle(title);
        t.setTag(tag);
        t.setAddress(address);
        t.setFirstId(firstId);
        t.setFirstClass(firstClass);
        t.setSecondId(secondId);
        t.setSecondClass(secondClass);
        t.setThirdId(thirdId);
        t.setThirdClass(thirdClass);

        return t;
    }
}
