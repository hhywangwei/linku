package com.linku.server.shop.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * 店铺信息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("店铺信息")
public class Shop {
    @ApiModelProperty(value = "编号", required = true)
    private String id;
    @ApiModelProperty(value = "店铺姓名", required = true)
    private String name;
    @ApiModelProperty(value = "联系电话", required = true)
    private String phone;
    @ApiModelProperty(value = "联系人", required = true)
    private String contact;
    @ApiModelProperty(value = "省份", required = true)
    private String province;
    @ApiModelProperty(value = "城市", required = true)
    private String city;
    @ApiModelProperty(value = "县", required = true)
    private String county;
    @ApiModelProperty(value = "所在地址", required = true)
    private String address;
    @ApiModelProperty(value = "地理位置")
    private String[] locations;
    @ApiModelProperty(value = "店铺图标")
    private String icon;
    @ApiModelProperty(value = "显示图片")
    private String[] images;
    @ApiModelProperty(value = "店铺摘要")
    private String summary;
    @ApiModelProperty(value = "店铺描述")
    private String detail;
    @ApiModelProperty(value = "开门时间")
    private String openTime;
    @ApiModelProperty(value = "提供服务")
    private String[] services;
    @ApiModelProperty(value = "店铺状态", required = true)
    private State state;
    @ApiModelProperty(value = "true:试用,false:正式店铺", required = true)
    private Boolean tryUse;
    @ApiModelProperty(value = "试用开始时间")
    private Date tryFromTime;
    @ApiModelProperty(value = "试用结束时间")
    private Date tryToTime;
    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;
    @ApiModelProperty(value = "修改时间", required = true)
    private Date updateTime;

    public enum State{
        WAIT, OPEN, CLOSE
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Boolean getTryUse() {
        return tryUse;
    }

    public void setTryUse(Boolean tryUse) {
        this.tryUse = tryUse;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shop shop = (Shop) o;
        return Objects.equals(id, shop.id) &&
                Objects.equals(name, shop.name) &&
                Objects.equals(phone, shop.phone) &&
                Objects.equals(contact, shop.contact) &&
                Objects.equals(province, shop.province) &&
                Objects.equals(city, shop.city) &&
                Objects.equals(county, shop.county) &&
                Objects.equals(address, shop.address) &&
                Arrays.equals(locations, shop.locations) &&
                Objects.equals(icon, shop.icon) &&
                Arrays.equals(images, shop.images) &&
                Objects.equals(summary, shop.summary) &&
                Objects.equals(detail, shop.detail) &&
                Objects.equals(openTime, shop.openTime) &&
                Arrays.equals(services, shop.services) &&
                state == shop.state &&
                Objects.equals(tryUse, shop.tryUse) &&
                Objects.equals(tryFromTime, shop.tryFromTime) &&
                Objects.equals(tryToTime, shop.tryToTime) &&
                Objects.equals(createTime, shop.createTime) &&
                Objects.equals(updateTime, shop.updateTime);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id, name, phone, contact, province, city, county, address, icon, summary, detail, openTime, state, tryUse, tryFromTime, tryToTime, createTime, updateTime);
        result = 31 * result + Arrays.hashCode(locations);
        result = 31 * result + Arrays.hashCode(images);
        result = 31 * result + Arrays.hashCode(services);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("phone", phone)
                .append("contact", contact)
                .append("province", province)
                .append("city", city)
                .append("county", county)
                .append("address", address)
                .append("locations", locations)
                .append("icon", icon)
                .append("images", images)
                .append("summary", summary)
                .append("detail", detail)
                .append("openTime", openTime)
                .append("services", services)
                .append("state", state)
                .append("tryUse", tryUse)
                .append("tryFromTime", tryFromTime)
                .append("tryToTime", tryToTime)
                .append("createTime", createTime)
                .append("updateTime", updateTime)
                .toString();
    }
}
