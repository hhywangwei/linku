package com.tuoshecx.server.api.manage.ticket.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 使用电子券提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel(value = "使用电子券提交数据")
public class UseForm {
    @NotBlank
    @ApiModelProperty(value = "电子券编号", required = true)
    private String code;
    @ApiModelProperty(value = "验证码", required = true)
    private String valCode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValCode() {
        return valCode;
    }

    public void setValCode(String valCode) {
        this.valCode = valCode;
    }
}
