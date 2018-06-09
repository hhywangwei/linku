package com.linku.server.wx.pay.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Map;

/**
 * 微信退款通知
 *
 * @author WangWei
 */
public class WxRefundNotify {
    @ApiModelProperty(value = "编号")
    private String id;
    @ApiModelProperty(value = "微信交易编号")
    private String transactionNo;
    @ApiModelProperty(value = "业务订单编号")
    private String outTradeNo;
    @ApiModelProperty(value = "微信退款编号")
    private String refundId;
    @ApiModelProperty(value = "业务退款编号")
    private String outRefundNo;
    @ApiModelProperty(value = "支付金额")
    private Integer totalFee;
    @ApiModelProperty(value = "当该订单有使用非充值券时，返回此字段。")
    private Integer settlementTotalFee;
    @ApiModelProperty(value = "退款金额")
    private Integer refundFee;
    @ApiModelProperty(value = "退款金额=申请退款金额-非充值代金券退款金额，退款金额")
    private Integer settlementRefundFee;
    @ApiModelProperty(value = "订单状态", notes = "SUCCESS-退款成功;CHANGE-退款异常;REFUNDCLOSE—退款关闭")
    private String state;
    @ApiModelProperty(value = "成功时间")
    private String successTime;
    @ApiModelProperty(value = "退款入账账户")
    private String refundRecvAccout;
    @ApiModelProperty(value = "退款资金来源")
    private String refundAccount;
    @ApiModelProperty(value = "退款发起来源")
    private String refundRequestSource;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public WxRefundNotify(){
        //none instance
    }

    public WxRefundNotify(Map<String, String> data){
        this.transactionNo = data.get("transaction_id");
        this.outTradeNo = data.get("out_trade_no");
        this.refundId = data.get("refund_id");
        this.outRefundNo = data.get("out_refund_no");
        this.totalFee = Integer.valueOf(data.get("total_fee"));
        this.settlementTotalFee = Integer.valueOf(data.getOrDefault("settlement_total_fee", "-1"));
        this.refundFee = Integer.valueOf(data.get("refund_fee"));
        this.settlementRefundFee = Integer.valueOf(data.get("settlement_refund_fee"));
        this.state = data.get("refund_status");
        this.successTime = data.getOrDefault("success_time", "");
        this.refundRecvAccout = data.get("refund_recv_accout");
        this.refundAccount = data.get("refund_account");
        this.refundRequestSource = data.get("refund_request_source");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
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

    public Integer getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Integer refundFee) {
        this.refundFee = refundFee;
    }

    public Integer getSettlementRefundFee() {
        return settlementRefundFee;
    }

    public void setSettlementRefundFee(Integer settlementRefundFee) {
        this.settlementRefundFee = settlementRefundFee;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(String successTime) {
        this.successTime = successTime;
    }

    public String getRefundRecvAccout() {
        return refundRecvAccout;
    }

    public void setRefundRecvAccout(String refundRecvAccout) {
        this.refundRecvAccout = refundRecvAccout;
    }

    public String getRefundAccount() {
        return refundAccount;
    }

    public void setRefundAccount(String refundAccount) {
        this.refundAccount = refundAccount;
    }

    public String getRefundRequestSource() {
        return refundRequestSource;
    }

    public void setRefundRequestSource(String refundRequestSource) {
        this.refundRequestSource = refundRequestSource;
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

        WxRefundNotify that = (WxRefundNotify) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("transactionNo", transactionNo)
                .append("outTradeNo", outTradeNo)
                .append("refundId", refundId)
                .append("outRefundNo", outRefundNo)
                .append("totalFee", totalFee)
                .append("settlementTotalFee", settlementTotalFee)
                .append("refundFee", refundFee)
                .append("settlementRefundFee", settlementRefundFee)
                .append("state", state)
                .append("successTime", successTime)
                .append("refundRecvAccout", refundRecvAccout)
                .append("refundAccount", refundAccount)
                .append("refundRequestSource", refundRequestSource)
                .append("createTime", createTime)
                .toString();
    }
}
