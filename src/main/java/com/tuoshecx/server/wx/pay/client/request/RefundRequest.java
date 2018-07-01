package com.tuoshecx.server.wx.pay.client.request;

import org.springframework.util.Assert;

import java.util.Map;

/**
 * 构建退款请求消息
 *
 * @author WangWei
 */
public class RefundRequest extends WxPayRequest {
    private final String outTradeNo;
    private final String outRefundNo;
    private final int totalFee;
    private final int refundFee;
    private final String refundFeeType;
    private final String refundDesc;

    private RefundRequest(String appid, String mchid, String key, String outTradeNo, String outRefundNo,
                          int totalFee, int refundFee, String refundFeeType, String refundDesc) {

        super(appid, mchid, key);
        this.outTradeNo = outTradeNo;
        this.outRefundNo= outRefundNo;
        this.totalFee= totalFee;
        this.refundFee = refundFee;
        this.refundFeeType = refundFeeType;
        this.refundDesc = refundDesc;
    }

    @Override
    protected void buildParameters(Map<String, String> parameters, String appid, String mchid) {
        putNotBlank(parameters, "appid", appid);
        putNotBlank(parameters, "mch_id", mchid);
        putNotBlank(parameters, "out_trade_no", outTradeNo);
        putNotBlank(parameters, "out_refund_no", outRefundNo);
        putNotBlank(parameters, "total_fee", String.valueOf(totalFee));
        putNotBlank(parameters, "refund_fee", String.valueOf(refundFee));
        putCanBlank(parameters, "refund_fee_type", refundFeeType);
        putCanBlank(parameters, "refund_desc", refundDesc);
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public int getRefundFee() {
        return refundFee;
    }

    public String getRefundFeeType() {
        return refundFeeType;
    }

    public String getRefundDesc() {
        return refundDesc;
    }

    public static class Builder {
        private String key;
        private String appid;
        private String mchid;
        private final String outTradeNo;
        private final String outRefundNo;
        private final int totalFee;
        private final int refundFee;
        private String refundFeeType = "CNY";
        private String refundDesc = "";

        public Builder(String appid, String mchid, String key, String outTradeNo,
                       String outRefundNo, int totalFee, int refundFee){

            this.appid = appid;
            this.mchid = mchid;
            this.key = key;
            this.outTradeNo = outTradeNo;
            this.outRefundNo = outRefundNo;
            this.totalFee = totalFee;
            this.refundFee = refundFee;
        }

        public Builder setRefundFeeType(String refundFeeType){
            Assert.hasText(refundFeeType, "Refund fee type not empty");
            this.refundFeeType = refundFeeType;
            return this;
        }

        public Builder setRefundDesc(String refundDesc){
            Assert.hasText(refundDesc, "RefundDesc not empty");
            this.refundDesc = refundDesc;
            return this;
        }

        public RefundRequest build(){
            return new RefundRequest(appid, mchid, key, outTradeNo,
                    outRefundNo, totalFee, refundFee, refundFeeType, refundDesc);
        }
    }
}
