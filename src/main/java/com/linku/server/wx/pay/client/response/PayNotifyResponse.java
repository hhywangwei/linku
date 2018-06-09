package com.linku.server.wx.pay.client.response;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 支付通知微信推送信息
 *
 * TODO 该版本不支持代金券处理
 *
 * @author WangWei
 */
public class PayNotifyResponse extends WxPayResponse {
    private final String sign;
    private final String signType;
    private final String openid;
    private final Boolean isSubscribe;
    private final String tradeType;
    private final String bankType;
    private final Integer totalFee;
    private final Integer settlementTotalFee;
    private final String feeType;
    private final Integer cashFee;
    private final String cashFeeType;
    private final String transactionId;
    private final String outTradeNo;
    private final String attach;
    private final String timeEnd;

    public PayNotifyResponse(Map<String, String> data) {
        super(data);
        this.sign = data.getOrDefault("sign", "");
        this.signType = data.getOrDefault("sign_type", "MD5");
        this.openid = data.getOrDefault("openid", "");
        this.isSubscribe = StringUtils.equals(data.getOrDefault("is_subscribe", ""), "Y");
        this.tradeType = data.getOrDefault("trade_type", "");
        this.bankType = data.getOrDefault("bank_type", "");
        this.totalFee = Integer.valueOf(data.getOrDefault("total_fee", "0"));
        this.settlementTotalFee = Integer.valueOf(data.getOrDefault("settlement_total_fee", "0"));
        this.feeType = data.getOrDefault("fee_type", "");
        this.cashFee = Integer.valueOf(data.getOrDefault("cash_fee", "0"));
        this.cashFeeType = data.getOrDefault("cash_fee_type", "");
        this.transactionId = data.getOrDefault("transaction_id", "");
        this.outTradeNo = data.getOrDefault("out_trade_no", "");
        this.attach = data.getOrDefault("attach", "");
        this.timeEnd = data.getOrDefault("time_end", "");
    }

    public String getSign() {
        return sign;
    }

    public String getSignType() {
        return signType;
    }

    public String getOpenid() {
        return openid;
    }

    public Boolean getSubscribe() {
        return isSubscribe;
    }

    public String getTradeType() {
        return tradeType;
    }

    public String getBankType() {
        return bankType;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public Integer getSettlementTotalFee() {
        return settlementTotalFee;
    }

    public String getFeeType() {
        return feeType;
    }

    public Integer getCashFee() {
        return cashFee;
    }

    public String getCashFeeType() {
        return cashFeeType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public String getAttach() {
        return attach;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

}
