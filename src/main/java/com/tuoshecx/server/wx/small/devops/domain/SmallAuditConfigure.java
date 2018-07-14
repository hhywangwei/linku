package com.tuoshecx.server.wx.small.devops.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 小程序审核配置
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class SmallAuditConfigure {
    private String id;
    private String shopId;
    private String appid;
    private String title;
    private String tag;
    private String address;
    private Integer firstId;
    private String firstClass;
    private Integer secondId;
    private String secondClass;
    private Integer thirdId;
    private String thirdClass;
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

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
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
        SmallAuditConfigure that = (SmallAuditConfigure) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(shopId, that.shopId) &&
                Objects.equals(appid, that.appid) &&
                Objects.equals(title, that.title) &&
                Objects.equals(tag, that.tag) &&
                Objects.equals(address, that.address) &&
                Objects.equals(firstId, that.firstId) &&
                Objects.equals(firstClass, that.firstClass) &&
                Objects.equals(secondId, that.secondId) &&
                Objects.equals(secondClass, that.secondClass) &&
                Objects.equals(thirdId, that.thirdId) &&
                Objects.equals(thirdClass, that.thirdClass) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, shopId, appid, title, tag, address, firstId, firstClass, secondId, secondClass, thirdId, thirdClass, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("shopId", shopId)
                .append("appid", appid)
                .append("title", title)
                .append("tag", tag)
                .append("address", address)
                .append("firstId", firstId)
                .append("firstClass", firstClass)
                .append("secondId", secondId)
                .append("secondClass", secondClass)
                .append("thirdId", thirdId)
                .append("thirdClass", thirdClass)
                .append("createTime", createTime)
                .toString();
    }
}
