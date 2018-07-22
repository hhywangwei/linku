package com.tuoshecx.server.wx.pay.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 微信统一下单实体对象
 *
 * @author WangWei
 */
@ApiModel(value = "微信统一订单")
public class WxUnifiedOrder {
    @ApiModelProperty(value = "编号")
    private String id;
    @ApiModelProperty(value = "所属店铺编号")
    private String shopId;
    @ApiModelProperty(value = "微信appid")
    private String appid;
    @ApiModelProperty(value = "支付用户编号")
    private String userId;
    @ApiModelProperty(value = "支付用户openid")
    private String openid;
    @ApiModelProperty(value = "订单编号")
    private String outTradeNo;
    @ApiModelProperty(value = "订单描述")
    private String body;
    @ApiModelProperty(value = "附加数据，支付通知原样返回")
    private String attach;
    @ApiModelProperty(value = "货币类型")
    private String feeType;
    @ApiModelProperty(value = "支付金额")
    private Integer totalFee;
    @ApiModelProperty(value = "实际支付，微信通知中金额")
    private Integer realTotalFee;
    @ApiModelProperty(value = "微信交易类型")
    private String tradeType;
    @ApiModelProperty(value = "微信交易编号")
    private String transactionNo;
    @ApiModelProperty(hidden = true, notes = "微信生成的预支付码")
    private String prepay;
    @ApiModelProperty(value = "订单状态", allowableValues = "wait, pay, refund", notes = "wait:等待支付, pay:支付成功, refund:退款")
    private String state = "wait";
    @ApiModelProperty(value = "退款金额")
    private Integer refundFee;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "支付时间")
    private Date payTime;
    @ApiModelProperty(value = "退款时间")
    private Date refundTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getRealTotalFee() {
        return realTotalFee;
    }

    public void setRealTotalFee(Integer realTotalFee) {
        this.realTotalFee = realTotalFee;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPrepay() {
        return prepay;
    }

    public void setPrepay(String prepay) {
        this.prepay = prepay;
    }

    public Integer getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Integer refundFee) {
        this.refundFee = refundFee;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WxUnifiedOrder order = (WxUnifiedOrder) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(shopId, order.shopId) &&
                Objects.equals(appid, order.appid) &&
                Objects.equals(userId, order.userId) &&
                Objects.equals(openid, order.openid) &&
                Objects.equals(outTradeNo, order.outTradeNo) &&
                Objects.equals(body, order.body) &&
                Objects.equals(attach, order.attach) &&
                Objects.equals(feeType, order.feeType) &&
                Objects.equals(totalFee, order.totalFee) &&
                Objects.equals(realTotalFee, order.realTotalFee) &&
                Objects.equals(tradeType, order.tradeType) &&
                Objects.equals(transactionNo, order.transactionNo) &&
                Objects.equals(prepay, order.prepay) &&
                Objects.equals(state, order.state) &&
                Objects.equals(refundFee, order.refundFee) &&
                Objects.equals(createTime, order.createTime) &&
                Objects.equals(payTime, order.payTime) &&
                Objects.equals(refundTime, order.refundTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, shopId, appid, userId, openid, outTradeNo, body, attach, feeType, totalFee, realTotalFee, tradeType, transactionNo, prepay, state, refundFee, createTime, payTime, refundTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("shopId", shopId)
                .append("appid", appid)
                .append("userId", userId)
                .append("openid", openid)
                .append("outTradeNo", outTradeNo)
                .append("body", body)
                .append("attach", attach)
                .append("feeType", feeType)
                .append("totalFee", totalFee)
                .append("realTotalFee", realTotalFee)
                .append("tradeType", tradeType)
                .append("transactionNo", transactionNo)
                .append("prepay", prepay)
                .append("state", state)
                .append("refundFee", refundFee)
                .append("createTime", createTime)
                .append("payTime", payTime)
                .append("refundTime", refundTime)
                .toString();
    }
}
