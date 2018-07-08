package com.tuoshecx.server.wx.configure.properties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微信第三方平台配置
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ConfigurationProperties(prefix = "wx.component")
public class WxComponentProperties {
    private String appid;
    private String secret;
    private String validateToken;
    private String encodingAesKey;
    private String redirectUri;
    private String successUri;
    private String failUri;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getValidateToken() {
        return validateToken;
    }

    public void setValidateToken(String validateToken) {
        this.validateToken = validateToken;
    }

    public String getEncodingAesKey() {
        return encodingAesKey;
    }

    public void setEncodingAesKey(String encodingAesKey) {
        this.encodingAesKey = encodingAesKey;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getSuccessUri() {
        return successUri;
    }

    public void setSuccessUri(String successUri) {
        this.successUri = successUri;
    }

    public String getFailUri() {
        return failUri;
    }

    public void setFailUri(String failUri) {
        this.failUri = failUri;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("appid", appid)
                .append("secret", secret)
                .append("validateToken", validateToken)
                .append("encodingAesKey", encodingAesKey)
                .append("redirectUri", redirectUri)
                .append("successUri", successUri)
                .append("failUri", failUri)
                .toString();
    }
}
