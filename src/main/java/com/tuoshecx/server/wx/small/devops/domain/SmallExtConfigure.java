package com.tuoshecx.server.wx.small.devops.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 微信小程序发布自定义配置
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class SmallExtConfigure {
    private String id;
    private Integer templateId;
    private String ext;
    private String extPages;
    private String pages;
    private String window;
    private String tabBar;
    private Boolean debug;
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmallExtConfigure that = (SmallExtConfigure) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(templateId, that.templateId) &&
                Objects.equals(ext, that.ext) &&
                Objects.equals(extPages, that.extPages) &&
                Objects.equals(pages, that.pages) &&
                Objects.equals(window, that.window) &&
                Objects.equals(tabBar, that.tabBar) &&
                Objects.equals(debug, that.debug) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, templateId, ext, extPages, pages, window, tabBar, debug, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("templateId", templateId)
                .append("ext", ext)
                .append("extPages", extPages)
                .append("pages", pages)
                .append("window", window)
                .append("tabBar", tabBar)
                .append("debug", debug)
                .append("createTime", createTime)
                .toString();
    }
}
