package com.linku.server.wx.pay.client.response;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;

/**
 * 退款输出对象
 *
 * @author WangWei
 */
public class RefundResponse extends WxPayResponse {
    private final String transactionId;
    private final String outTradeNo;
    private final String outRefundNo;
    private final String refundId;
    private final Integer refundFee;
    private final Integer settlementRefundFee;
    private final Integer totalFee;
    private final Integer settlementTotalFee;
    private final String feeType;
    private final Integer cashFee;
    private final String cashFeeType;
    private final Integer cashRefundFee;

    public RefundResponse(Map<String, String> data) {
        super(data);
        transactionId = data.get("transaction_id");
        outTradeNo = data.get("out_trade_no");
        outRefundNo = data.get("out_refund_no");
        refundId = data.get("refund_id");
        refundFee = Integer.valueOf(data.get("refund_fee"));
        settlementRefundFee = Integer.valueOf(data.getOrDefault("settlement_refund_fee", "0"));
        totalFee = Integer.valueOf(data.get("total_fee"));
        settlementTotalFee = Integer.valueOf(data.getOrDefault("settlement_total_fee", "0"));
        feeType = data.getOrDefault("fee_type", "CNY");
        cashFee = Integer.valueOf(data.get("cash_fee"));
        cashFeeType = data.getOrDefault("cash_fee_type", "CNY");
        cashRefundFee = Integer.valueOf(data.getOrDefault("cash_refund_fee", "0"));
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public String getRefundId() {
        return refundId;
    }

    public Integer getRefundFee() {
        return refundFee;
    }

    public Integer getSettlementRefundFee() {
        return settlementRefundFee;
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

    public Integer getCashRefundFee() {
        return cashRefundFee;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("transactionId", transactionId)
                .append("outTradeNo", outTradeNo)
                .append("outRefundNo", outRefundNo)
                .append("refundId", refundId)
                .append("refundFee", refundFee)
                .append("settlementRefundFee", settlementRefundFee)
                .append("totalFee", totalFee)
                .append("settlementTotalFee", settlementTotalFee)
                .append("feeType", feeType)
                .append("cashFee", cashFee)
                .append("cashFeeType", cashFeeType)
                .append("cashRefundFee", cashRefundFee)
                .toString();
    }
}
