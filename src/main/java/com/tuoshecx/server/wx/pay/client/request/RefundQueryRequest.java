package com.tuoshecx.server.wx.pay.client.request;

import java.util.Map;

/**
 * 查询退款请求信息
 *
 * @author WangWei
 */
public class RefundQueryRequest extends WxPayRequest {
    private final String refundId;
    private final String outRefundNo;
    private final String outTradeNo;
    private final String transactionId;

    private RefundQueryRequest(String appid, String mchid, String subMchId, String key, String refundId,
                              String outRefundNo, String outTradeNo, String transactionId) {

        super(appid, mchid, key, subMchId);
        this.refundId = refundId;
        this.outRefundNo = outRefundNo;
        this.outTradeNo = outTradeNo;
        this.transactionId = transactionId;
    }

    @Override
    protected void buildParameters(Map<String, String> parameters, String appid, String mchid, boolean sub, String subMchId) {
        putNotBlank(parameters, "appid", appid);
        putNotBlank(parameters, "mch_id", mchid);
        if(sub){
            putNotBlank(parameters,"sub_appid", appid);
            putNotBlank(parameters, "sub_mch_id", subMchId);
        }
        putCanBlank(parameters, "refund_id", refundId);
        putCanBlank(parameters, "out_refund_no", outRefundNo);
        putCanBlank(parameters, "out_trade_no", outTradeNo);
        putCanBlank(parameters, "transaction_id", transactionId);
    }

    public static class Builder{
        private final String appid;
        private final String mchid;
        private final String subMchId;
        private final String key;
        private String refundId;
        private String outRefundNo;
        private String outTradeNo;
        private String transactionId;

        public Builder(String appid, String mchid, String key){
            this(appid, mchid, "", key);
        }

        public Builder(String appid, String mchid, String subMchId, String key){
            this.appid = appid;
            this.mchid = mchid;
            this.subMchId = subMchId;
            this.key = key;
        }

        public Builder setRefundId(String refundId){
            this.refundId = refundId;
            return this;
        }

        public Builder setOutRefundNo(String outRefundNo){
            this.outRefundNo = outRefundNo;
            return this;
        }

        public Builder setOutTradeNo(String outTradeNo){
            this.outTradeNo = outTradeNo;
            return this;
        }

        public Builder setTransactionId(String transactionId){
            this.transactionId = transactionId;
            return this;
        }

        public RefundQueryRequest build(){
            return new RefundQueryRequest(appid, mchid, subMchId, key, refundId, outRefundNo, outTradeNo, transactionId);
        }
    }
}
