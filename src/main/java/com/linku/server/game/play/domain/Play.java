package com.linku.server.game.play.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 参与游戏用户信息
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class Play {
    private String id;
    private String gameId;
    private String gameName;
    private String userId;
    private String name;
    private String headImg;
    private String prize;
    private Integer money;
    private String pticketId;
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getPticketId() {
        return pticketId;
    }

    public void setPticketId(String pticketId) {
        this.pticketId = pticketId;
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
        if (!(o instanceof Play)) return false;
        Play play = (Play) o;
        return Objects.equals(id, play.id) &&
                Objects.equals(gameId, play.gameId) &&
                Objects.equals(gameName, play.gameName) &&
                Objects.equals(userId, play.userId) &&
                Objects.equals(name, play.name) &&
                Objects.equals(headImg, play.headImg) &&
                Objects.equals(prize, play.prize) &&
                Objects.equals(money, play.money) &&
                Objects.equals(pticketId, play.pticketId) &&
                Objects.equals(createTime, play.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, gameId, gameName, userId, name, headImg, prize, money, pticketId, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("gameId", gameId)
                .append("gameName", gameName)
                .append("userId", userId)
                .append("name", name)
                .append("headImg", headImg)
                .append("prize", prize)
                .append("money", money)
                .append("pticketId", pticketId)
                .append("createTime", createTime)
                .toString();
    }
}
