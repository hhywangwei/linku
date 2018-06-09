package com.linku.server.marketing.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

/**
 * 团购行销活动
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("团购活动")
public class GroupBuy extends Marketing {
    @ApiModelProperty(value = "组团人数", required = true)
    private Integer person;
    @ApiModelProperty(value = "等待天数", required = true)
    private Integer days;

    public Integer getPerson() {
        return person;
    }

    public void setPerson(Integer person) {
        this.person = person;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupBuy)) return false;
        if (!super.equals(o)) return false;
        GroupBuy that = (GroupBuy) o;
        return Objects.equals(person, that.person) &&
                Objects.equals(days, that.days);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), person, days);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("person", person)
                .append("days", days)
                .toString();
    }
}
