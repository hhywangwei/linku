package com.tuoshecx.server.api.sys.wx.vo;

import com.tuoshecx.server.wx.small.client.response.GetCategoryResponse;
import io.swagger.annotations.ApiModelProperty;

/**
 * 小程序目录输出
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ProgramCategoryVo {
    @ApiModelProperty("第一级目录")
    private final String firstClass;
    @ApiModelProperty("第二级目录")
    private final String secondClass;
    @ApiModelProperty("第三级目录")
    private final String thirdClass;
    @ApiModelProperty("第一级目录编号")
    private final Integer firstId;
    @ApiModelProperty("第二级目录编号")
    private final Integer secondId;
    @ApiModelProperty("第三级目录编号")
    private final Integer thirdId;

    public ProgramCategoryVo(GetCategoryResponse.ProgramCategory t){
        this.firstClass = t.getFirstClass();
        this.secondClass = t.getSecondClass();
        this.thirdClass = t.getThirdClass();
        this.firstId = t.getFirstId();
        this.secondId = t.getSecondId();
        this.thirdId = t.getThirdId();
    }

    public String getFirstClass() {
        return firstClass;
    }

    public String getSecondClass() {
        return secondClass;
    }

    public String getThirdClass() {
        return thirdClass;
    }

    public Integer getFirstId() {
        return firstId;
    }

    public Integer getSecondId() {
        return secondId;
    }

    public Integer getThirdId() {
        return thirdId;
    }
}
