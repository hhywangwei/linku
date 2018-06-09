package com.linku.server.wx.configure.properties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微信支付配置属性
 *
 * @author WangWei
 */
@ConfigurationProperties(prefix = "wx.pay")
public class WxPayProperties {
    private String appid;
    private String mchid;
    private String key;
    private String refundKey;
    private String notifyUrl;
    private String billCreateIp;
    private String sign;
    private Keystore keystore;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRefundKey() {
        return refundKey;
    }

    public void setRefundKey(String refundKey) {
        this.refundKey = refundKey;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getBillCreateIp() {
        return billCreateIp;
    }

    public void setBillCreateIp(String billCreateIp) {
        this.billCreateIp = billCreateIp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Keystore getKeystore() {
        return keystore;
    }

    public void setKeystore(Keystore keystore) {
        this.keystore = keystore;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("appid", appid)
                .append("mchid", mchid)
                .append("key", key)
                .append("refundKey", refundKey)
                .append("notifyUrl", notifyUrl)
                .append("billCreateIp", billCreateIp)
                .append("sign", sign)
                .append("keystore", keystore)
                .toString();
    }

    public static class Keystore{
        private String uri;
        private String password;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("uri", uri)
                    .append("password", password)
                    .toString();
        }
    }
}
