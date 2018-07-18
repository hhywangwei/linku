package com.tuoshecx.server.api.sys.wx.form;

import com.tuoshecx.server.wx.small.devops.domain.SmallExtConfigure;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 修改小程序配置
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class SmallExtConfigureUpdateForm extends SmallExtConfigureSaveForm {
    @NotBlank
    @ApiModelProperty(value = "编号")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public SmallExtConfigure toDomain() {
        SmallExtConfigure t = super.toDomain();
        t.setId(id);

        return t;
    }
}
