package com.linku.server.wx.pay.client.response;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 微信输出对象
 *
 * @author WangWei
 */
public class WxPayResponse {
    private final String code;
    private final String message;
    private final String appid;
    private final String mchid;
    private final String deviceInfo;
    private final String nonceStr;
    private final String resultCode;
    private final String errCode;
    private final String errCodeDes;
    private final boolean success;

    WxPayResponse(Map<String, String> data){
        this.code = data.getOrDefault("return_code", "FAIL");
        this.message = data.getOrDefault("return_msg", "");
        this.appid = data.getOrDefault("appid", "");
        this.mchid = data.getOrDefault("mch_id", "");
        this.deviceInfo = data.getOrDefault("device_info", "");
        this.nonceStr = data.getOrDefault("nonce_str", "");
        this.resultCode = data.getOrDefault("result_code", "FAIL");
        this.errCode = data.getOrDefault("err_code", "");
        this.errCodeDes = data.getOrDefault("err_code_des", "");
        this.success = StringUtils.equals(code, "SUCCESS") && StringUtils.equals(resultCode, "SUCCESS");
    }

    public String getCode() {
        return code;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getAppid() {
        return appid;
    }

    public String getMchid() {
        return mchid;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }

}
