package com.tuoshecx.server.api.client.pay.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tuoshecx.server.common.utils.SecurityUtils;
import com.tuoshecx.server.wx.configure.properties.WxPayProperties;
import com.tuoshecx.server.wx.pay.utils.WxPayUtils;
import io.swagger.annotations.ApiModelProperty;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 微信预支付数据数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class PerPayVo {
    @ApiModelProperty(value = "微信公众号Appid")
    private final String appid;
    @ApiModelProperty(value = "随机字符串")
    private final String nonceStr;
    @JsonProperty("package")
    @ApiModelProperty(value = "预支付package")
    private final String payPackage;
    @ApiModelProperty(value = "签名类型")
    private final String signType;
    @ApiModelProperty(value = "时间戳")
    private final String timeStamp;
    @ApiModelProperty(value = "签名")
    private final String paySign;

    public PerPayVo(String appid, String key, String prePayId){
        this.appid = appid;
        this.nonceStr = SecurityUtils.randomStr(6);
        this.payPackage = String.format("prepay_id=%s", prePayId);
        this.signType = "MD5";
        this.timeStamp = String.valueOf(WxPayUtils.timestamp());
        this.paySign = buildSign(this, key);
    }

    public String getAppid() {
        return appid;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public String getPayPackage() {
        return payPackage;
    }

    public String getSignType() {
        return signType;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getPaySign() {
        return paySign;
    }

    private static String buildSign(PerPayVo vo, String payKey){
        Map<String, String> params = new LinkedHashMap<>(       10, 1);

        params.put("appId", vo.appid);
        params.put("nonceStr", vo.nonceStr);
        params.put("timeStamp", vo.timeStamp);
        params.put("package", vo.payPackage);
        params.put("signType", vo.signType);

        return WxPayUtils.sign(params, payKey);
    }
}
