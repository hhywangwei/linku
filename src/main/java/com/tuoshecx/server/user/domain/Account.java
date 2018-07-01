package com.tuoshecx.server.user.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 用户账户
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("用户账户")
public class Account {
    @ApiModelProperty(value = "编号", required = true)
    private String id;
    @ApiModelProperty(value = "用户编号", required = true)
    private String userId;
    @ApiModelProperty(value = "支付验证码", required = true)
    private String payCode;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

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

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
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
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(userId, account.userId) &&
                Objects.equals(payCode, account.payCode) &&
                Objects.equals(createTime, account.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, payCode, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("userId", userId)
                .append("payCode", payCode)
                .append("createTime", createTime)
                .toString();
    }
}
