package com.tuoshecx.server.wx.configure.properties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微信支付配置属性
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ConfigurationProperties(prefix = "wx.pay")
public class WxPayProperties {
    private String mchid;
    private String key;
    private String notifyUrl;
    private String billCreateIp;
    private String payType;

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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("mchid", mchid)
                .append("key", key)
                .append("notifyUrl", notifyUrl)
                .append("billCreateIp", billCreateIp)
                .append("payType", payType)
                .toString();
    }
}
