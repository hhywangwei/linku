package com.linku.server.api.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 上传文件输出对象
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("上传文件输出对象")
public class UploadVo {
    @ApiModelProperty(value = "上传文件访问地址", required = true)
    private final String url;

    public UploadVo(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
