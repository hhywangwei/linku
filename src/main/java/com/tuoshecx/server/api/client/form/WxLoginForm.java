package com.tuoshecx.server.api.client.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 微信小程序登陆提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("微信登陆提交数据")
public class WxLoginForm {
    @NotBlank
    @ApiModelProperty(value = "微信appid", required = true)
    private String appid;
    @NotBlank
    @ApiModelProperty(value = "code", required = true)
    private String code;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
