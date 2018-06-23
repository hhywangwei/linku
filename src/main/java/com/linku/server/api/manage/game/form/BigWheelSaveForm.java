package com.linku.server.api.manage.game.form;

import com.linku.server.game.wheel.domain.BigWheel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 创建大转盘游戏提交数据
 *
 * @author WangWei
 */
public class BigWheelSaveForm {
    @NotNull
    @Size(min = 1)
    @ApiModelProperty(value = "游戏名称", required = true)
    private String name;
    @ApiModelProperty(value = "帮助")
    private String help;
    @NotNull
    @ApiModelProperty(value = "游戏开始时间", required = true)
    private Date fromDate;
    @NotNull
    @ApiModelProperty(value = "游戏结束时间", required = true)
    private Date toDate;
    @NotNull
    @ApiModelProperty(value = "每天限玩次数,防止恶意抽奖", required = true)
    private Integer limit = 1;
    @NotNull
    @ApiModelProperty(value = "奖券有效天数", required = true)
    private Integer pticketDays = 7;
    @NotEmpty
    @ApiModelProperty(value = "中奖项目")
    private List<BigWheelSaveItemForm> items;

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

    public List<BigWheelSaveItemForm> getItems() {
        return items;
    }

    public void setItems(List<BigWheelSaveItemForm> items) {
        this.items = items;
    }

    public BigWheel toDomain(){
        BigWheel t = new BigWheel();

        t.setName(name);
        t.setHelp(help);
        t.setLimit(limit);
        t.setPticketDays(pticketDays);
        t.setFromDate(fromDate);
        t.setToDate(toDate);

        return t;
    }

    @Validated
    public static class BigWheelSaveItemForm{
        @NotNull
        @Size(min =  1)
        @ApiModelProperty(value = "奖项显示标题")
        private String title;
        @NotNull
        @Min(0)
        @ApiModelProperty(value = "中奖金额，0代表未中奖")
        private Integer money;
        @Min(1)
        @ApiModelProperty(value = "中奖率")
        private Integer ratio;

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
    }
}
