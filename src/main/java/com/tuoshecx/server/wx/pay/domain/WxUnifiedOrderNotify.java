package com.tuoshecx.server.wx.pay.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * 微信支付通知
 *
 * @author WangWei
 */
public class WxUnifiedOrderNotify {
    private String id;
    private String sign;
    private String signType;
    private String openid;
    private Boolean isSubscribe;
    private String tradeType;
    private String bankType;
    private Integer totalFee;
    private Integer settlementTotalFee;
    private String feeType;
    private Integer cashFee;
    private String cashFeeType;
    private String transactionId;
    private String outTradeNo;
    private String attach;
    private String timeEnd;
    private Date createTime;

    public WxUnifiedOrderNotify(){
        //none instance
    }

    public WxUnifiedOrderNotify(Map<String, String> data){
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Boolean getSubscribe() {
        return isSubscribe;
    }

    public void setSubscribe(Boolean subscribe) {
        isSubscribe = subscribe;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getSettlementTotalFee() {
        return settlementTotalFee;
    }

    public void setSettlementTotalFee(Integer settlementTotalFee) {
        this.settlementTotalFee = settlementTotalFee;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public Integer getCashFee() {
        return cashFee;
    }

    public void setCashFee(Integer cashFee) {
        this.cashFee = cashFee;
    }

    public String getCashFeeType() {
        return cashFeeType;
    }

    public void setCashFeeType(String cashFeeType) {
        this.cashFeeType = cashFeeType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WxUnifiedOrderNotify that = (WxUnifiedOrderNotify) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WxPayNotifyLog{");
        sb.append("id='").append(id).append('\'');
        sb.append(", sign='").append(sign).append('\'');
        sb.append(", signType='").append(signType).append('\'');
        sb.append(", openid='").append(openid).append('\'');
        sb.append(", isSubscribe=").append(isSubscribe);
        sb.append(", tradeType='").append(tradeType).append('\'');
        sb.append(", bankType='").append(bankType).append('\'');
        sb.append(", totalFee=").append(totalFee);
        sb.append(", settlementTotalFee=").append(settlementTotalFee);
        sb.append(", feeType='").append(feeType).append('\'');
        sb.append(", cashFee=").append(cashFee);
        sb.append(", cashFeeType='").append(cashFeeType).append('\'');
        sb.append(", transactionId='").append(transactionId).append('\'');
        sb.append(", outTradeNo='").append(outTradeNo).append('\'');
        sb.append(", attach='").append(attach).append('\'');
        sb.append(", timeEnd='").append(timeEnd).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}
