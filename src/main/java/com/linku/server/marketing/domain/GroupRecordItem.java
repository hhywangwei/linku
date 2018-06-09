package com.linku.server.marketing.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 团购类营销记录明细
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("团购类营销记录明细")
public class GroupRecordItem {
    @ApiModelProperty(value = "编号")
    private String id;
    @ApiModelProperty(value = "活动编号")
    private String recordId;
    @ApiModelProperty(value = "购买用户编号")
    private String userId;
    @ApiModelProperty(value = "购买用户昵称")
    private String nickname;
    @ApiModelProperty(value = "购买用户头像")
    private String headImg;
    @ApiModelProperty(value = "联系电话")
    private String phone;
    @ApiModelProperty(value = "是否发起团购者")
    private Boolean owner;
    @ApiModelProperty(value = "所属订单编号")
    private String orderId;
    @ApiModelProperty(value = "是否是首单")
    private Boolean first;
    @ApiModelProperty(value = "是否取消")
    private Boolean cancel;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "取消时间")
    private Date cancelTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getOwner() {
        return owner;
    }

    public void setOwner(Boolean owner) {
        this.owner = owner;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Boolean getCancel() {
        return cancel;
    }

    public void setCancel(Boolean cancel) {
        this.cancel = cancel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupRecordItem)) return false;
        GroupRecordItem that = (GroupRecordItem) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(recordId, that.recordId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(nickname, that.nickname) &&
                Objects.equals(headImg, that.headImg) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(owner, that.owner) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(first, that.first) &&
                Objects.equals(cancel, that.cancel) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(cancelTime, that.cancelTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, recordId, userId, nickname, headImg, phone, owner, orderId, first, cancel, createTime, cancelTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("recordId", recordId)
                .append("userId", userId)
                .append("nickname", nickname)
                .append("headImg", headImg)
                .append("phone", phone)
                .append("owner", owner)
                .append("orderId", orderId)
                .append("first", first)
                .append("cancel", cancel)
                .append("createTime", createTime)
                .append("cancelTime", cancelTime)
                .toString();
    }
}
