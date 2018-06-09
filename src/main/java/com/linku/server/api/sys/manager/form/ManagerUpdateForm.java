package com.linku.server.api.sys.manager.form;

import com.linku.server.base.domain.Sys;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 修改系统管理员提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("修改系统管理员提交数据")
public class ManagerUpdateForm {
    @NotBlank
    @ApiModelProperty(value = "管理编号")
    private String id;
    @Size(min = 1, max = 20)
    @ApiModelProperty(value =  "姓名")
    private String name;
    @Size(max = 300)
    @ApiModelProperty(value = "用户头像")
    private String headImg;
    @Size(max = 20)
    @ApiModelProperty(value = "联系电话")
    private String phone;
    @ApiModelProperty(value = "联系邮件")
    private String email;
    @ApiModelProperty(value = "拥有权限角色")
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

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public Sys toDomain(){
        Sys t = new Sys();

        t.setId(id);
        t.setName(StringUtils.defaultString(name));
        t.setPhone(StringUtils.defaultString(phone));
        t.setHeadImg(headImg);
        t.setEmail(StringUtils.defaultString(email));
        t.setRoles(roles);

        return t;
    }
}
