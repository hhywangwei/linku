package com.linku.server.game.wheel.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 大转盘
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("大转盘")
public class BigWheel {
    @ApiModelProperty(value = "编号", required = true)
    private String id;
    @ApiModelProperty(value = "店铺编号", required = true)
    private String shopId;
    @ApiModelProperty(value = "游戏名称", required = true)
    private String name;
    @ApiModelProperty(value = "帮助")
    private String help;
    @ApiModelProperty(value = "状态", required = true)
    private State state = State.WAIT;
    @ApiModelProperty(value = "游戏开始时间", required = true)
    private Date fromDate;
    @ApiModelProperty(value = "游戏结束时间", required = true)
    private Date toDate;
    @ApiModelProperty(value = "每天限玩次数,防止恶意抽奖", required = true)
    private Integer limit;
    @ApiModelProperty(value = "奖券有效天数", required = true)
    private Integer pticketDays;
    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

    public enum State {
        WAIT, OPEN, CLOSE
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPticketDays() {
        return pticketDays;
    }

    public void setPticketDays(Integer pticketDays) {
        this.pticketDays = pticketDays;
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
        if (!(o instanceof BigWheel)) return false;
        BigWheel prize = (BigWheel) o;
        return Objects.equals(id, prize.id) &&
                Objects.equals(shopId, prize.shopId) &&
                Objects.equals(name, prize.name) &&
                Objects.equals(help, prize.help) &&
                state == prize.state &&
                Objects.equals(fromDate, prize.fromDate) &&
                Objects.equals(toDate, prize.toDate) &&
                Objects.equals(limit, prize.limit) &&
                Objects.equals(pticketDays, prize.pticketDays) &&
                Objects.equals(createTime, prize.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, shopId, name, help, state, fromDate, toDate, limit, pticketDays, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("shopId", shopId)
                .append("name", name)
                .append("help", help)
                .append("state", state)
                .append("fromDate", fromDate)
                .append("toDate", toDate)
                .append("limit", limit)
                .append("pticketDays", pticketDays)
                .append("createTime", createTime)
                .toString();
    }
}
