package com.tuoshecx.server.wx.pay.client.request;

import com.tuoshecx.server.BaseException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * 微信企业支付参数,可实现商铺提现、退款等操作
 * <a href="https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2">微信企业付API接口</a>
 *
 * @author WangWei
 */
public class TransferRequest extends WxPayRequest {

    public enum CheckName {
        NO_CHECK, FORCE_CHECK, OPTION_CHECK
    }

    private final String partnerTradeNo;
    private final String openid;
    private final CheckName checkName;
    private final String reUserName;
    private final int amount;
    private final String deviceInfo;
    private final String desc;
    private final String spbillCreateIp;

    private TransferRequest(String appid, String mchid, String key, String partnerTradeNo, String openid,
                            CheckName checkName, String reUserName, int amount, String deviceInfo, String desc, String spbillCreateIp) {
        super(appid, mchid, key);

        this.partnerTradeNo = partnerTradeNo;
        this.openid = openid;
        this.checkName = checkName;
        this.reUserName = reUserName;
        this.amount = amount;
        this.deviceInfo = deviceInfo;
        this.desc = desc;
        this.spbillCreateIp = spbillCreateIp;
    }

    @Override
    protected void buildParameters(Map<String, String> parameters, String appid, String mchid) {
        putNotBlank(parameters, "mch_appid", appid);
        putNotBlank(parameters, "mchid", mchid);
        putCanBlank(parameters, "device_info", deviceInfo);
        putNotBlank(parameters, "partner_trade_no", partnerTradeNo);
        putNotBlank(parameters, "openid", openid);
        putNotBlank(parameters, "check_name", checkName.name());
        putCanBlank(parameters, "re_user_name", reUserName);
        putNotBlank(parameters, "amount", String.valueOf(amount));
        putNotBlank(parameters, "desc", desc);
        putNotBlank(parameters, "spbill_create_ip", spbillCreateIp);
    }

    public static class Builder {
        private String key;
        private String appid;
        private String mchid;
        private String partnerTradeNo;
        private String openid;
        private CheckName checkName;
        private String reUserName;
        private int amount;
        private String deviceInfo;
        private String desc;
        private String spbillCreateIp;

        public Builder(String appid, String mchid, String key, String partnerTradeNo, String openid, int amount){
            this.key = key;
            this.appid = appid;
            this.mchid = mchid;
            this.partnerTradeNo = partnerTradeNo;
            this.checkName = CheckName.NO_CHECK;
            this.openid = openid;
            this.amount = amount;
            this.desc = "商铺提现";
            this.spbillCreateIp = "8.8.8.8";
        }

        public Builder setDeviceInfo(String deviceInfo){
            this.deviceInfo = deviceInfo;
            return this;
        }

        public Builder setCheckName(CheckName checkName){
            Assert.notNull(checkName, "checkName is null");

            this.checkName = checkName;
            return this;
        }

        public Builder setReUserName(String reUserName){
            this.reUserName = reUserName;
            return this;
        }

        public Builder setDesc(String desc){
            Assert.hasText(desc, "desc is blank");

            this.desc = desc;
            return this;
        }

        public Builder setSpbillCreateIp(String ip){
            Assert.hasText(ip, "spbillCreateIp is blank");

            this.spbillCreateIp = ip;
            return this;
        }

        public TransferRequest build(){
            if((checkName == CheckName.FORCE_CHECK || checkName == CheckName.OPTION_CHECK) && StringUtils.isBlank(reUserName)){
                throw new BaseException("需要验证用户名,用户名不能为空");
            }

            return new TransferRequest(appid, mchid, key, partnerTradeNo, openid,
                    checkName, reUserName, amount, deviceInfo, desc, spbillCreateIp);
        }
    }
}
