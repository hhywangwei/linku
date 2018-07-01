package com.tuoshecx.server.configure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Token配置属性
 *
 * @author WangWei
 */
@ConfigurationProperties(prefix = "security.token")
public class TokenProperties {
    private Integer expiresInMinutes = 30;
    private String keyPrefix;

    public Integer getExpiresInMinutes() {
        return expiresInMinutes;
    }

    public void setExpiresInMinutes(Integer expiresInMinutes) {
        this.expiresInMinutes = expiresInMinutes;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
}
