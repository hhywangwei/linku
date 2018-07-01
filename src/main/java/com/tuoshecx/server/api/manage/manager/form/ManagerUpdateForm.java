package com.tuoshecx.server.api.manage.manager.form;

import com.tuoshecx.server.shop.domain.Manager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 修改店铺雇员信息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("更新店铺雇员信息提交数据")
public class ManagerUpdateForm {
    @NotBlank
    private String id;
    @Size(max = 20)
    @ApiModelProperty(value = "姓名")
    private String name;
    @Size(max = 200)
    @ApiModelProperty(value = "用户头像")
    private String icon;
    @Size(max = 20)
    @ApiModelProperty(value = "联系电话")
    private String phone;
    @ApiModelProperty(value = "权限角色")
    private String[] roles;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public Manager toDomain(){
        Manager t = new Manager();

        t.setId(id);
        t.setName(name);
        t.setHeadImg(icon);
        t.setPhone(phone);
        t.setRoles(roles);

        return t;
    }
}
