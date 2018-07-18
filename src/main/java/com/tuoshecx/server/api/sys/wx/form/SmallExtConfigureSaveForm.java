package com.tuoshecx.server.api.sys.wx.form;

import com.tuoshecx.server.wx.small.devops.domain.SmallExtConfigure;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 *  新增小程序配置
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class SmallExtConfigureSaveForm {
    @NotNull
    @ApiModelProperty(value = "发布模板编号")
    private Integer templateId;
    @ApiModelProperty(value = "ext配置")
    private String ext;
    @ApiModelProperty(value = "extPages配置")
    private String extPages;
    @ApiModelProperty(value = "pages配置")
    private String pages;
    @ApiModelProperty(value = "window配置")
    private String window;
    @ApiModelProperty(value = "tabBar配置")
    private String tabBar;
    @ApiModelProperty(value = "debug配置")
    private Boolean debug;

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getExtPages() {
        return extPages;
    }

    public void setExtPages(String extPages) {
        this.extPages = extPages;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getWindow() {
        return window;
    }

    public void setWindow(String window) {
        this.window = window;
    }

    public String getTabBar() {
        return tabBar;
    }

    public void setTabBar(String tabBar) {
        this.tabBar = tabBar;
    }

    public Boolean getDebug() {
        return debug;
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    public SmallExtConfigure toDomain(){
        SmallExtConfigure t = new SmallExtConfigure();

        t.setTemplateId(templateId);
        t.setExt(ext);
        t.setExtPages(extPages);
        t.setPages(pages);
        t.setWindow(window);
        t.setTabBar(tabBar);
        t.setDebug(debug);

        return t;
    }
}
