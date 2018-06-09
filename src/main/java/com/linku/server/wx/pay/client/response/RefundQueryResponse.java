package com.linku.server.wx.pay.client.response;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查询退款输出对象
 *
 * @author WangWei
 */
public class RefundQueryResponse extends WxPayResponse {
    private final String transactionId;
    private final String outTradeNo;
    private final Integer totalFee;
    private final Integer settlementTotalFee;
    private final String feeType;
    private final Integer cashFee;
    private final List<RefundItem> items;

    public RefundQueryResponse(Map<String, String> data) {
        super(data);

        this.transactionId = data.get("transaction_id");
        this.outTradeNo = data.get("out_trade_no");
        this.totalFee = Integer.valueOf(data.get("total_fee"));
        this.settlementTotalFee = Integer.valueOf(data.getOrDefault("settlement_total_fee", "0"));
        this.feeType = data.getOrDefault("fee_type", "CNY");
        this.cashFee = Integer.valueOf(data.get("cash_fee"));
        int count = Integer.valueOf(data.get("refund_count"));
        items = new ArrayList<>(count);
        for(int i = 0; i < count; i++){
            items.add(new RefundItem(data, i));
        }
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
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

    public List<RefundItem> getItems() {
        return items;
    }

    public static class RefundItem {
        private final String outRefundNo;
        private final String refundId;
        private final String refundChannel;
        private final Integer refundFee;
        private final Integer settlementRefundFee;
        private final String couponType;
        private final Integer couponRefundFee;
        private final String refundStatus;
        private final String refundAccount;
        private final String refundRecvAccout;
        private final String refundSuccessTime;

        RefundItem(Map<String, String> data, int index){
            outRefundNo = data.get("out_refund_no_" + index);
            refundId = data.get("refund_id_" + index);
            refundChannel = data.getOrDefault("refund_channel_" + index, "ORIGINAL");
            refundFee = Integer.valueOf(data.get("refund_fee_" + index));
            settlementRefundFee = Integer.valueOf(data.getOrDefault("settlement_refund_fee_" + index, "0"));
            couponType = data.get("coupon_type_" + index);
            couponRefundFee = Integer.valueOf(data.getOrDefault("coupon_refund_fee_" + index, "0"));
            refundStatus = data.get("refund_status_" + index);
            refundAccount  = data.getOrDefault("refund_account_" + index, "");
            refundRecvAccout = data.get("refund_recv_accout_" + index);
            refundSuccessTime = data.getOrDefault("refund_success_time_" + index, "");
        }

        public String getOutRefundNo() {
            return outRefundNo;
        }

        public String getRefundId() {
            return refundId;
        }

        public String getRefundChannel() {
            return refundChannel;
        }

        public Integer getRefundFee() {
            return refundFee;
        }

        public Integer getSettlementRefundFee() {
            return settlementRefundFee;
        }

        public String getCouponType() {
            return couponType;
        }

        public Integer getCouponRefundFee() {
            return couponRefundFee;
        }

        public String getRefundStatus() {
            return refundStatus;
        }

        public String getRefundAccount() {
            return refundAccount;
        }

        public String getRefundRecvAccout() {
            return refundRecvAccout;
        }

        public String getRefundSuccessTime() {
            return refundSuccessTime;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("outRefundNo", outRefundNo)
                    .append("refundId", refundId)
                    .append("refundChannel", refundChannel)
                    .append("refundFee", refundFee)
                    .append("couponType", couponType)
                    .append("couponRefundFee", couponRefundFee)
                    .append("refundStatus", refundStatus)
                    .append("refundAccount", refundAccount)
                    .append("refundRecvAccout", refundRecvAccout)
                    .append("refundSuccessTime", refundSuccessTime)
                    .toString();
        }
    }
}
