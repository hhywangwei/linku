package com.linku.server.api.manage.manager.form;

import com.linku.server.shop.domain.Manager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 新增店铺雇员
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel(value = "新增店铺雇员提交数据")
public class ManagerSaveForm {
    @NotBlank
    @Size(max = 20) @Pattern(regexp = "[A-Za-z0-9_]*")
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @NotBlank
    @Size(min = 8, max = 20) @Pattern(regexp = "[A-Za-z0-9_@$\\-]*")
    @ApiModelProperty(value = "用户密码", required = true)
    private String password;
    @Size(max = 20)
    @ApiModelProperty(value = "姓名")
    private String name;
    @Size(max = 200)
    @ApiModelProperty(value = "用户头像")
    private String headImg;
    @Size(max = 20)
    @ApiModelProperty(value = "联系电话")
    private String phone;
    @ApiModelProperty(value = "权限角色")
    private String[] roles;

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

    public Manager toDomain(String shopId){
        Manager t = new Manager();

        t.setShopId(shopId);
        t.setUsername(username);
        t.setPassword(password);
        t.setName(name);
        t.setHeadImg(headImg);
        t.setPhone(phone);
        t.setRoles(roles);
        t.setManager(false);

        return t;
    }
}
