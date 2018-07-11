package com.tuoshecx.server.wx.small.devops.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Objects;

/**
 * 小程序服务器域名配置
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("小程序业务域配置")
public class DomainConfigure {
    @ApiModelProperty(value = "编号")
    private String id;
    @ApiModelProperty(value = "http请求合法域名")
    private String[] requestDomain;
    @ApiModelProperty(value = "web socket请求合法域名")
    private String[] wsrequestDomain;
    @ApiModelProperty(value = "上传请求合法域名")
    private String[] uploadDomain;
    @ApiModelProperty(value = "下载请求合法域名")
    private String[] downloadDomain;
    @ApiModelProperty(value = "小程序业务域名")
    private String[] webViewDomain;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getRequestDomain() {
        return requestDomain;
    }

    public void setRequestDomain(String[] requestDomain) {
        this.requestDomain = requestDomain;
    }

    public String[] getWsrequestDomain() {
        return wsrequestDomain;
    }

    public void setWsrequestDomain(String[] wsrequestDomain) {
        this.wsrequestDomain = wsrequestDomain;
    }

    public String[] getUploadDomain() {
        return uploadDomain;
    }

    public void setUploadDomain(String[] uploadDomain) {
        this.uploadDomain = uploadDomain;
    }

    public String[] getDownloadDomain() {
        return downloadDomain;
    }

    public void setDownloadDomain(String[] downloadDomain) {
        this.downloadDomain = downloadDomain;
    }

    public String[] getWebViewDomain() {
        return webViewDomain;
    }

    public void setWebViewDomain(String[] webViewDomain) {
        this.webViewDomain = webViewDomain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainConfigure that = (DomainConfigure) o;
        return Objects.equals(id, that.id) &&
                Arrays.equals(requestDomain, that.requestDomain) &&
                Arrays.equals(wsrequestDomain, that.wsrequestDomain) &&
                Arrays.equals(uploadDomain, that.uploadDomain) &&
                Arrays.equals(downloadDomain, that.downloadDomain) &&
                Arrays.equals(webViewDomain, that.webViewDomain);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id);
        result = 31 * result + Arrays.hashCode(requestDomain);
        result = 31 * result + Arrays.hashCode(wsrequestDomain);
        result = 31 * result + Arrays.hashCode(uploadDomain);
        result = 31 * result + Arrays.hashCode(downloadDomain);
        result = 31 * result + Arrays.hashCode(webViewDomain);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("requestDomain", requestDomain)
                .append("wsrequestDomain", wsrequestDomain)
                .append("uploadDomain", uploadDomain)
                .append("downloadDomain", downloadDomain)
                .append("webViewDomain", webViewDomain)
                .toString();
    }
}
