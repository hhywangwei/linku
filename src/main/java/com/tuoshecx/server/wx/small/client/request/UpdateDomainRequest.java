package com.tuoshecx.server.wx.small.client.request;

/**
 * 设置小程序服务器域名请求
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class UpdateDomainRequest extends WxSmallRequest{
    private final String action;
    private final String[] requestDomain;
    private final String[] wsRequestDomain;
    private final String[] uploadDomain;
    private final String[] downloadDomain;

    public UpdateDomainRequest(String token, String action, String[] requestDomain,
                               String[] wsRequestDomain, String[] uploadDomain, String[] downloadDomain) {
        super(token);
        this.action = action;
        this.requestDomain = requestDomain;
        this.wsRequestDomain = wsRequestDomain;
        this.uploadDomain = uploadDomain;
        this.downloadDomain = downloadDomain;
    }

    public String getAction() {
        return action;
    }

    public String[] getRequestDomain() {
        return requestDomain;
    }

    public String[] getWsRequestDomain() {
        return wsRequestDomain;
    }

    public String[] getUploadDomain() {
        return uploadDomain;
    }

    public String[] getDownloadDomain() {
        return downloadDomain;
    }
}
