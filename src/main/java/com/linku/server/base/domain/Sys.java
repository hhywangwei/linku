package com.linku.server.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * 系统管理员
 *
 * <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("系统管理员")
public class Sys {
    @ApiModelProperty(value = "编号", required = true)
    private String id;
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @JsonIgnore
    @ApiModelProperty(value = "登陆密码", required = true)
    private String password;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "头像")
    private String headImg;
    @ApiModelProperty(value = "联系电话")
    private String phone;
    @ApiModelProperty(value = "联系邮件")
    private String email;
    @ApiModelProperty(value = "true:可用,false:不可用", required = true)
    private Boolean enable;
    @ApiModelProperty(value = "true：是系统管理员，不能删除。", required = true)
    private Boolean manager;
    @ApiModelProperty(value = "用户权限角色")
    private String[] roles;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean isEnable) {
        this.enable = isEnable;
    }

    public Boolean getManager() {
        return manager;
    }

    public void setManager(Boolean manager) {
        this.manager = manager;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
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
        Sys sys = (Sys) o;
        return Objects.equals(id, sys.id) &&
                Objects.equals(username, sys.username) &&
                Objects.equals(password, sys.password) &&
                Objects.equals(name, sys.name) &&
                Objects.equals(headImg, sys.headImg) &&
                Objects.equals(phone, sys.phone) &&
                Objects.equals(email, sys.email) &&
                Objects.equals(enable, sys.enable) &&
                Objects.equals(manager, sys.manager) &&
                Arrays.equals(roles, sys.roles) &&
                Objects.equals(createTime, sys.createTime) &&
                Objects.equals(updateTime, sys.updateTime);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id, username, password, name, headImg, phone, email, enable, manager, createTime, updateTime);
        result = 31 * result + Arrays.hashCode(roles);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("username", username)
                .append("password", password)
                .append("name", name)
                .append("headImg", headImg)
                .append("phone", phone)
                .append("email", email)
                .append("enable", enable)
                .append("manager", manager)
                .append("roles", roles)
                .append("createTime", createTime)
                .append("updateTime", updateTime)
                .toString();
    }
}
