package com.tuoshecx.server.api.manage.game.vo;

import com.tuoshecx.server.game.wheel.domain.BigWheel;
import com.tuoshecx.server.game.wheel.domain.BigWheelItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * 大转盘游戏信息
 *
 * @author <a href="mail:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("大转盘游戏信息")
public class BigWheelVo {
    @ApiModelProperty(value = "编号", required = true)
    private final String id;
    @ApiModelProperty(value = "店铺编号", required = true)
    private final String shopId;
    @ApiModelProperty(value = "游戏名称", required = true)
    private final String name;
    @ApiModelProperty(value = "帮助")
    private final String help;
    @ApiModelProperty(value = "状态", required = true)
    private final BigWheel.State state ;
    @ApiModelProperty(value = "游戏开始时间", required = true)
    private final Date fromDate;
    @ApiModelProperty(value = "游戏结束时间", required = true)
    private final Date toDate;
    @ApiModelProperty(value = "每天限玩次数,防止恶意抽奖", required = true)
    private final Integer limit;
    @ApiModelProperty(value = "奖券有效天数", required = true)
    private final Integer pticketDays;
    @ApiModelProperty(value = "创建时间", required = true)
    private final Date createTime;
    @ApiModelProperty(value = "中奖项目")
    private final List<BigWheelItem> items;

    public BigWheelVo(BigWheel t, List<BigWheelItem>  items){
        this.id = t.getId();
        this.shopId = t.getShopId();
        this.name = t.getName();
        this.help = t.getHelp();
        this.state = t.getState();
        this.fromDate = t.getFromDate();
        this.toDate = t.getToDate();
        this.limit = t.getLimit();
        this.pticketDays = t.getPticketDays();
        this.createTime = t.getCreateTime();
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public String getShopId() {
        return shopId;
    }

    public String getName() {
        return name;
    }

    public String getHelp() {
        return help;
    }

    public BigWheel.State getState() {
        return state;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getPticketDays() {
        return pticketDays;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public List<BigWheelItem> getItems() {
        return items;
    }
}
