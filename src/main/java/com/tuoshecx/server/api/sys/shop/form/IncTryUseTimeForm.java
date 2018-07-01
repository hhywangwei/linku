package com.tuoshecx.server.api.sys.shop.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 延迟试用时间提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("延迟试用时间提交数据")
public class IncTryUseTimeForm {
    @NotBlank
    @ApiModelProperty(value = "店铺编号", required = true)
    private String id;
    @NotNull
    @ApiModelProperty(value = "试用结束时间(yyyy-MM-dd)", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date tryToTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTryToTime() {
        return tryToTime;
    }

    public void setTryToTime(Date tryToTime) {
        this.tryToTime = tryToTime;
    }
}
