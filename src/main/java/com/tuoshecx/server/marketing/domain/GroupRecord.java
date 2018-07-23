package com.tuoshecx.server.marketing.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 团购类营销记录
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("团购类营销记录")
public class GroupRecord {
    @ApiModelProperty(value = "编号")
    private String id;
    @ApiModelProperty(value = "店铺编号")
    private String shopId;
    @ApiModelProperty(value = "活动编号")
    private String marketingId;
    @ApiModelProperty(value = "活动名称")
    private String name;
    @ApiModelProperty(value = "活动图标")
    private String icon;
    @ApiModelProperty(value = "需要人员")
    private Integer needPerson;
    @ApiModelProperty(value = "参加人员")
    private Integer joinPerson;
    @ApiModelProperty(value = "活动只针新用户")
    private Boolean first;
    @ApiModelProperty(value = "活动类型")
    private String type;
    @ApiModelProperty(value = "参加用户描述，保存参加用户信息,数据格式[{name:xxx, headImg:xxx}]")
    private String joinUserDetail;
    @ApiModelProperty(value = "状态")
    private State state;
    @ApiModelProperty(value = "是否分享")
    private Boolean share;
    @ApiModelProperty(value = "活动价格")
    private Integer price;
    @ApiModelProperty(value = "商品编号")
    private String goodsId;
    @ApiModelProperty(value = "发起用户编号")
    private String userId;
    @ApiModelProperty(value = "乐观锁", hidden = true)
    private Integer version;
    @ApiModelProperty(value = "开始时间")
    private Date startTime;
    @ApiModelProperty(value = "结束时间")
    private Date endTime;
    @ApiModelProperty(value = "关闭时间")
    private Date closeTime;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public enum State {
        WAIT, ACTIVATE, CLOSE, SUCCESS
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

    public String getMarketingId() {
        return marketingId;
    }

    public void setMarketingId(String marketingId) {
        this.marketingId = marketingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getNeedPerson() {
        return needPerson;
    }

    public void setNeedPerson(Integer needPerson) {
        this.needPerson = needPerson;
    }

    public Integer getJoinPerson() {
        return joinPerson;
    }

    public void setJoinPerson(Integer joinPerson) {
        this.joinPerson = joinPerson;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJoinUserDetail() {
        return joinUserDetail;
    }

    public void setJoinUserDetail(String joinUserDetail) {
        this.joinUserDetail = joinUserDetail;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Boolean getShare() {
        return share;
    }

    public void setShare(Boolean share) {
        this.share = share;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
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
        if (!(o instanceof GroupRecord)) return false;
        GroupRecord that = (GroupRecord) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(shopId, that.shopId) &&
                Objects.equals(marketingId, that.marketingId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(icon, that.icon) &&
                Objects.equals(needPerson, that.needPerson) &&
                Objects.equals(joinPerson, that.joinPerson) &&
                Objects.equals(first, that.first) &&
                Objects.equals(type, that.type) &&
                Objects.equals(joinUserDetail, that.joinUserDetail) &&
                state == that.state &&
                Objects.equals(share, that.share) &&
                Objects.equals(price, that.price) &&
                Objects.equals(goodsId, that.goodsId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(version, that.version) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(closeTime, that.closeTime) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, shopId, marketingId, name, icon, needPerson, joinPerson, first, type, joinUserDetail, state, share, price, goodsId, userId, version, startTime, endTime, closeTime, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("shopId", shopId)
                .append("marketingId", marketingId)
                .append("name", name)
                .append("icon", icon)
                .append("needPerson", needPerson)
                .append("joinPerson", joinPerson)
                .append("first", first)
                .append("type", type)
                .append("joinUserDetail", joinUserDetail)
                .append("state", state)
                .append("share", share)
                .append("price", price)
                .append("goodsId", goodsId)
                .append("userId", userId)
                .append("version", version)
                .append("startTime", startTime)
                .append("endTime", endTime)
                .append("closeTime", closeTime)
                .append("createTime", createTime)
                .toString();
    }
}
