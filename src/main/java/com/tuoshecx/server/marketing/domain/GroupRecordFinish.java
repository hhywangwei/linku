package com.tuoshecx.server.marketing.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 组团类营销活动结束记录
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class GroupRecordFinish {
    private String id;
    private String recordId;
    private String itemId;
    private String action;
    private State state;
    private String message;
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

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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
        GroupRecordFinish that = (GroupRecordFinish) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(recordId, that.recordId) &&
                Objects.equals(itemId, that.itemId) &&
                Objects.equals(action, that.action) &&
                state == that.state &&
                Objects.equals(message, that.message) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, recordId, itemId, action, state, message, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("recordId", recordId)
                .append("itemId", itemId)
                .append("action", action)
                .append("state", state)
                .append("message", message)
                .append("createTime", createTime)
                .toString();
    }
}
