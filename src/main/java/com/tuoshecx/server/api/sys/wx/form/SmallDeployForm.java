package com.tuoshecx.server.api.sys.wx.form;

import javax.validation.constraints.NotBlank;

/**
 * 发布小程序提交数据
 *
 * @author <a href="mailto:hhywangwei">WangWei</a>
 */
public class SmallDeployForm {
    @NotBlank
    private String shopId;
    private Integer templateId;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }
}
