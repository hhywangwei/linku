package com.tuoshecx.server.api.manage.shop.form;

import com.tuoshecx.server.shop.domain.Shop;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 修改店铺提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel(value = "修改店铺信息")
public class ShopUpdateForm {
    @NotBlank @Size(max = 20)
    @ApiModelProperty(value = "店铺名称", required = true)
    private String name;
    @NotBlank
    @ApiModelProperty(value = "联系电话", required = true)
    private String phone;
    @NotBlank @Size(max = 20)
    @ApiModelProperty(value = "联系人", required = true)
    private String contact;
    @NotBlank
    @Size(max = 30)
    @ApiModelProperty(value = "省份")
    private String province;
    @NotBlank
    @Size(max = 20)
    @ApiModelProperty(value = "城市")
    private String city;
    @NotBlank
    @Size(max = 20)
    @ApiModelProperty(value = "县")
    private String county;
    @NotBlank @Size(max = 200)
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "地理位置定位")
    private String[] locations;
    @Size(max = 500)
    @ApiModelProperty(value = "店铺图标")
    private String icon;
    @Size(max = 2000)
    @ApiModelProperty(value = "显示图片")
    private String[] images;
    @ApiModelProperty(value = "店铺摘要")
    private String summary;
    @Size(max = 2000)
    @ApiModelProperty(value = "店铺描述")
    private String detail;
    @Size(max = 50)
    @ApiModelProperty(value = "工作时间")
    private String openTime;
    @ApiModelProperty(value = "提供服务")
    private String[] services;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String[] getLocations() {
        return locations;
    }

    public void setLocations(String[] locations) {
        this.locations = locations;
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

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String[] getServices() {
        return services;
    }

    public void setServices(String[] services) {
        this.services = services;
    }

    public Shop toDomain(String id){
        Shop t = new Shop();

        t.setId(id);
        t.setName(name);
        t.setPhone(phone);
        t.setContact(contact);
        t.setProvince(province);
        t.setCity(city);
        t.setCounty(county);
        t.setAddress(address);
        t.setLocations(locations);
        t.setIcon(icon);
        t.setImages(images);
        t.setSummary(summary);
        t.setDetail(detail);
        t.setOpenTime(openTime);
        t.setServices(services);
        t.setState(Shop.State.WAIT);

        return t;
    }
}
