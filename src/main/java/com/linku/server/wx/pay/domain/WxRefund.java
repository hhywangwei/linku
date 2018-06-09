package com.linku.server.wx.pay.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Objects;

/**
 * 退款实体对象
 *
 * @author WangWei
 */
@ApiModel(value = "微信退款")
public class WxRefund {
    @ApiModelProperty(value = "编号")
    private String id;
    @ApiModelProperty(value = "用户编号")
    private String userId;
    @ApiModelProperty(value = "用户openid")
    private String openid;
    @ApiModelProperty(value = "店铺编号")
    private String shopId;
    @ApiModelProperty(value = "业务订单编号")
    private String outTradeNo;
    @ApiModelProperty(value = "微信退款编号")
    private String refundId;
    @ApiModelProperty(value = "订单总金额")
    private Integer totalFee;
    @ApiModelProperty(value = "退款金额")
    private Integer refundFee;
    @ApiModelProperty(value = "退款原因")
    private String refundDesc = "";
    @ApiModelProperty(value = "状态", allowableValues = "wait, apply, success, fail")
    private String state = "wait";
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "退款成功时间")
    private Date successTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
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

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Integer refundFee) {
        this.refundFee = refundFee;
    }

    public String getRefundDesc() {
        return refundDesc;
    }

    public void setRefundDesc(String refundDesc) {
        this.refundDesc = refundDesc;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WxRefund wxRefund = (WxRefund) o;
        return Objects.equals(id, wxRefund.id) &&
                Objects.equals(userId, wxRefund.userId) &&
                Objects.equals(openid, wxRefund.openid) &&
                Objects.equals(shopId, wxRefund.shopId) &&
                Objects.equals(outTradeNo, wxRefund.outTradeNo) &&
                Objects.equals(refundId, wxRefund.refundId) &&
                Objects.equals(totalFee, wxRefund.totalFee) &&
                Objects.equals(refundFee, wxRefund.refundFee) &&
                Objects.equals(refundDesc, wxRefund.refundDesc) &&
                Objects.equals(state, wxRefund.state) &&
                Objects.equals(createTime, wxRefund.createTime) &&
                Objects.equals(successTime, wxRefund.successTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, openid, shopId, outTradeNo, refundId, totalFee, refundFee, refundDesc, state, createTime, successTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("userId", userId)
                .append("openid", openid)
                .append("shopId", shopId)
                .append("outTradeNo", outTradeNo)
                .append("refundId", refundId)
                .append("totalFee", totalFee)
                .append("refundFee", refundFee)
                .append("refundDesc", refundDesc)
                .append("state", state)
                .append("createTime", createTime)
                .append("successTime", successTime)
                .toString();
    }
}
