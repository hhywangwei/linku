package com.linku.server.shop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * 店铺管理员
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("店铺管理员")
public class Manager {
    @ApiModelProperty(value = "编号", required = true)
    private String id;
    @ApiModelProperty(value = "店铺编号", required = true)
    private String shopId;
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @JsonIgnore
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "用户头像")
    private String headImg;
    @ApiModelProperty(value = "联系电话")
    private String phone;
    @ApiModelProperty(value = "用户权限角色")
    private String[] roles;
    @ApiModelProperty(value = "true：是系统管理员，不能删除。", required = true)
    private Boolean manager;
    @ApiModelProperty(value = "true:可用,false:不可用", required = true)
    private Boolean enable;
    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;
    @ApiModelProperty(value = "修改时间", required = true)
    private Date updateTime;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public Boolean getManager() {
        return manager;
    }

    public void setManager(Boolean manager) {
        this.manager = manager;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean isEnable) {
        this.enable = isEnable;
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
        Manager employee = (Manager) o;
        return Objects.equals(id, employee.id) &&
                Objects.equals(shopId, employee.shopId) &&
                Objects.equals(username, employee.username) &&
                Objects.equals(password, employee.password) &&
                Objects.equals(name, employee.name) &&
                Objects.equals(headImg, employee.headImg) &&
                Objects.equals(phone, employee.phone) &&
                Arrays.equals(roles, employee.roles) &&
                Objects.equals(manager, employee.manager) &&
                Objects.equals(enable, employee.enable) &&
                Objects.equals(updateTime, employee.updateTime) &&
                Objects.equals(createTime, employee.createTime);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id, shopId, username, password, name, headImg, phone, manager, enable, updateTime, createTime);
        result = 31 * result + Arrays.hashCode(roles);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("shopId", shopId)
                .append("username", username)
                .append("password", password)
                .append("name", name)
                .append("headImg", headImg)
                .append("phone", phone)
                .append("roles", roles)
                .append("manager", manager)
                .append("enable", enable)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
