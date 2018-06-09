package com.linku.server.api.client.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 注册用户提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel(value = "注册用户提交数据")
public class RegisterForm {
    @NotBlank
    @Size(max = 20)
    @Pattern(regexp = "[A-Za-z0-9_]*")
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @NotBlank
    @Size(min = 8, max = 20)
    @ApiModelProperty(value = "用户密码")
    private String password;
    @NotBlank
    @ApiModelProperty(value = "所属店铺编号")
    private String shopId;

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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
