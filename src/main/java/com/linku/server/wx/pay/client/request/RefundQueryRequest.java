package com.linku.server.wx.pay.client.request;

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

    public RefundQueryRequest(String appid, String mchid, String key, String refundId,
                              String outRefundNo, String outTradeNo, String transactionId) {

        super(appid, mchid, key);
        this.refundId = refundId;
        this.outRefundNo = outRefundNo;
        this.outTradeNo = outTradeNo;
        this.transactionId = transactionId;
    }

    @Override
    protected void buildParameters(Map<String, String> parameters, String appid, String mchid) {
        putNotBlank(parameters, "appid", appid);
        putNotBlank(parameters, "mch_id", mchid);
        putCanBlank(parameters, "refund_id", refundId);
        putCanBlank(parameters, "out_refund_no", outRefundNo);
        putCanBlank(parameters, "out_trade_no", outTradeNo);
        putCanBlank(parameters, "transaction_id", transactionId);
    }

    public static class Builder{
        private final String appid;
        private final String mchid;
        private final String key;
        private String refundId;
        private String outRefundNo;
        private String outTradeNo;
        private String transactionId;

        public Builder(String appid, String mchid, String key){
            this.appid = appid;
            this.mchid = mchid;
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
            return new RefundQueryRequest(appid, mchid, key, refundId, outRefundNo, outTradeNo, transactionId);
        }
    }
}
