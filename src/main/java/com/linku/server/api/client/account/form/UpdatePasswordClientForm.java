package com.linku.server.api.client.account.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 更新用户密码
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("更新用户密码")
public class UpdatePasswordClientForm {
    @NotBlank
    @ApiModelProperty("用户密码")
    private String password;
    @NotBlank @Size(min = 8, max = 20)
    @ApiModelProperty("用户新密码")
    private String newPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
