package com.tuoshecx.server.wx.pay.client.response;

import java.util.Map;

/**
 * 微信企业支付输出对象
 *
 * @author WangWei
 */
public class TransferResponse extends WxPayResponse {

    private final String partnerTradeNo;
    private final String paymentNo;
    private final String paymentTime;

    public TransferResponse(Map<String, String> data) {
        super(data);

        this.partnerTradeNo = data.getOrDefault("partner_trade_no", "");
        this.paymentNo = data.getOrDefault("payment_no", "");
        this.paymentTime = data.getOrDefault("payment_time", "");
    }

    public String getPartnerTradeNo() {
        return partnerTradeNo;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public String getPaymentTime() {
        return paymentTime;
    }
}
