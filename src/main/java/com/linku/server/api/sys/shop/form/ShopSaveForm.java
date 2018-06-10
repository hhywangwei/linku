package com.linku.server.api.sys.shop.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.linku.server.shop.domain.Shop;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 新增店铺提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel(value = "新增店铺提交数据")
public class ShopSaveForm {
    @NotBlank
    @Size(max = 20)
    @ApiModelProperty(value = "店铺名称", required = true)
    private String name;
    @NotBlank
    @ApiModelProperty(value = "联系电话", required = true)
    private String phone;
    @NotBlank @Size(max = 20)
    @ApiModelProperty(value = "联系人", required = true)
    private String contact;
    @NotBlank
    @Size(max = 15)
    @ApiModelProperty(value = "省份")
    private String province;
    @Size(max = 20)
    @ApiModelProperty(value = "省份名称")
    private String provinceName;
    @NotBlank
    @Size(max = 15)
    @ApiModelProperty(value = "城市")
    private String city;
    @Size(max =20)
    @ApiModelProperty(value = "城市名称")
    private String cityName;
    @NotBlank
    @Size(max = 15)
    @ApiModelProperty(value = "县")
    private String county;
    @Size(max = 20)
    @ApiModelProperty(value = "县名称")
    private String countyName;
    @NotBlank
    @Size(max = 200)
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "定位")
    private String[] locations;
    @Size(max = 500)
    @ApiModelProperty(value = "店铺图标")
    private String icon;
    @ApiModelProperty(value = "显示图片")
    private String[] images;
    @Size(max = 2000)
    @ApiModelProperty(value = "店铺描述")
    private String detail;
    @Size(max = 50)
    @ApiModelProperty(value = "工作时间")
    private String openTime;
    @ApiModelProperty(value = "提供服务")
    private String[] services;
    @ApiModelProperty(value = "试用开始时间(yyyy-MM-dd)")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date tryFromTime;
    @ApiModelProperty(value = "试用结束时间(yyyy-MM-dd)")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date tryToTime;
    @NotBlank @Size(max = 20)
    @ApiModelProperty(value = "店铺管理者", required = true)
    private String manager;
    @NotBlank @Size(min = 6, max = 20)
    @ApiModelProperty(value = "店铺管理者密码", required = true)
    private String password;

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

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
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

    public Date getTryFromTime() {
        return tryFromTime;
    }

    public void setTryFromTime(Date tryFromTime) {
        this.tryFromTime = tryFromTime;
    }

    public Date getTryToTime() {
        return tryToTime;
    }

    public void setTryToTime(Date tryToTime) {
        this.tryToTime = tryToTime;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Shop toDomain(){
        Shop t = new Shop();

        t.setName(name);
        t.setPhone(phone);
        t.setContact(contact);
        t.setProvince(province);
        t.setProvinceName(provinceName);
        t.setCity(city);
        t.setCityName(cityName);
        t.setCounty(county);
        t.setCountyName(countyName);
        t.setAddress(address);
        t.setLocations(locations);
        t.setIcon(icon);
        t.setImages(images);
        t.setDetail(detail);
        t.setOpenTime(openTime);
        t.setServices(services);
        t.setState(Shop.State.WAIT);
        boolean isTry = tryToTime != null;
        t.setTryUse(isTry);
        t.setTryFromTime(tryFromTime);
        t.setTryToTime(tryToTime);

        return t;
    }
}
