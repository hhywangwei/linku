package com.linku.server.api.client.game.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 大转盘抽奖提交数据
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("大转盘抽奖数据")
public class PlayForm {

    @NotNull
    @Size(min = 1)
    @ApiModelProperty(value = "游戏编号", required = true)
    private String gameId;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
