package com.tuoshecx.server.api.client.wx.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 保存用户提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("保存用户提交数据")
public class SaveUserForm {
    @NotBlank
    @ApiModelProperty(value = "不包括敏感信息的原始数据字符串，用于计算签名", required = true)
    private String rawData;
    @NotBlank
    @ApiModelProperty(value = "signature", required = true)
    private String signature;
    @NotBlank
    @ApiModelProperty(value = "敏感数据在内的完整用户信息的加密数据", required = true)
    private String encryptedData;
    @NotBlank
    @ApiModelProperty(value = "加密算法的初始向量", required = true)
    private String iv;

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

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
