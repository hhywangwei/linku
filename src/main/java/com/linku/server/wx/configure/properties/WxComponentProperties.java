package com.linku.server.wx.configure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微信第三方平台配置
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@ConfigurationProperties(prefix = "wx.component")
public class WxComponentProperties {
    private String appid;
    private String secret;
    private String validateToken;
    private String encodingAesKey;

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

    @Override
    public String toString() {
        return "WxComponentProperties{" +
                "appid='" + appid + '\'' +
                ", secret='" + secret + '\'' +
                ", validateToken='" + validateToken + '\'' +
                ", encodingAesKey='" + encodingAesKey + '\'' +
                '}';
    }
}
