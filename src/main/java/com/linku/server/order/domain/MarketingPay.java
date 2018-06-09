package com.linku.server.order.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 营销活动订单支付处理日志
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class MarketingPay {
    private String id;
    private String marketingId;
    private String marketingType;
    private State state;
    private String message;
    private Date updateTime;
    private Date createTime;

    public enum State {
        WAIT, SUCCESS, FAIL
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarketingId() {
        return marketingId;
    }

    public void setMarketingId(String marketingId) {
        this.marketingId = marketingId;
    }

    public String getMarketingType() {
        return marketingType;
    }

    public void setMarketingType(String marketingType) {
        this.marketingType = marketingType;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        if (!(o instanceof MarketingPay)) return false;
        MarketingPay that = (MarketingPay) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(marketingId, that.marketingId) &&
                Objects.equals(marketingType, that.marketingType) &&
                state == that.state &&
                Objects.equals(message, that.message) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, marketingId, marketingType, state, message, updateTime, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("marketingId", marketingId)
                .append("marketingType", marketingType)
                .append("state", state)
                .append("message", message)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
