package com.tuoshecx.server.api.client.wx.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 通过微信小程序获取用手机号提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("通过微信小程序获取用手机号提交数据")
public class PhoneForm {
    @NotBlank
    @ApiModelProperty(value = "敏感数据在内的完整用户信息的加密数据", required = true)
    private String encryptedData;
    @NotBlank
    @ApiModelProperty(value = "加密算法的初始向量", required = true)
    private String iv;

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }
}
