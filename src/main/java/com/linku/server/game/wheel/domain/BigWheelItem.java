package com.linku.server.game.wheel.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

/**
 * 大转盘奖项
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("大转盘奖项")
public class BigWheelItem {
    @ApiModelProperty(value = "编号", required = true)
    private String id;
    @ApiModelProperty(value = "所属大转盘", required = true)
    private String bigWheelId;
    @ApiModelProperty(value = "顺时针序号", required = true)
    private Integer index;
    @ApiModelProperty(value = "开始角度", required = true)
    private Integer fromCursor;
    @ApiModelProperty(value = "结束角度")
    private Integer toCursor;
    @ApiModelProperty(value = "奖项显示标题")
    private String title;
    @ApiModelProperty(value = "中奖金额，0代表未中奖")
    private Integer money;
    @ApiModelProperty(value = "中奖率")
    private Integer ratio;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBigWheelId() {
        return bigWheelId;
    }

    public void setBigWheelId(String bigWheelId) {
        this.bigWheelId = bigWheelId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getFromCursor() {
        return fromCursor;
    }

    public void setFromCursor(Integer fromCursor) {
        this.fromCursor = fromCursor;
    }

    public Integer getToCursor() {
        return toCursor;
    }

    public void setToCursor(Integer toCursor) {
        this.toCursor = toCursor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BigWheelItem)) return false;
        BigWheelItem prizeItem = (BigWheelItem) o;
        return Objects.equals(id, prizeItem.id) &&
                Objects.equals(bigWheelId, prizeItem.bigWheelId) &&
                Objects.equals(index, prizeItem.index) &&
                Objects.equals(fromCursor, prizeItem.fromCursor) &&
                Objects.equals(toCursor, prizeItem.toCursor) &&
                Objects.equals(title, prizeItem.title) &&
                Objects.equals(money, prizeItem.money) &&
                Objects.equals(ratio, prizeItem.ratio);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, bigWheelId, index, fromCursor, toCursor, title, money, ratio);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("bigWheelId", bigWheelId)
                .append("index", index)
                .append("fromCursor", fromCursor)
                .append("toCursor", toCursor)
                .append("title", title)
                .append("money", money)
                .append("ratio", ratio)
                .toString();
    }
}
