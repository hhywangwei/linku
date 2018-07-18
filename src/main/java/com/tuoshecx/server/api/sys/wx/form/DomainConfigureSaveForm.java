package com.tuoshecx.server.api.sys.wx.form;

import com.tuoshecx.server.wx.small.devops.domain.DomainConfigure;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

/**
 * 保存小程序服务端域名配置
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel(value = "新增域名配置")
public class DomainConfigureSaveForm {
    @NotEmpty
    @ApiModelProperty(value = "http请求合法域名", required = true)
    private String[] requestDomain;
    @NotEmpty
    @ApiModelProperty(value = "web socket请求合法域名", required = true)
    private String[] wsrequestDomain;
    @NotEmpty
    @ApiModelProperty(value = "上传请求合法域名", required = true)
    private String[] uploadDomain;
    @NotEmpty
    @ApiModelProperty(value = "下载请求合法域名", required = true)
    private String[] downloadDomain;
    @NotEmpty
    @ApiModelProperty(value = "小程序业务域名", required = true)
    private String[] webViewDomain;

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

    public DomainConfigure toDomain(){
        DomainConfigure t = new DomainConfigure();

        t.setRequestDomain(requestDomain);
        t.setWsrequestDomain(wsrequestDomain);
        t.setUploadDomain(uploadDomain);
        t.setDownloadDomain(downloadDomain);

        return t;
    }
}
