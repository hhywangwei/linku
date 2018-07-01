package com.tuoshecx.server.marketing.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

/**
 * 多人行活动
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("多人行活动")
public class SharePresent extends Marketing {
    @ApiModelProperty(value = "需要人数", required = true)
    private Integer person;
    @ApiModelProperty(value = "true:用户首单", required = true)
    private Boolean first;
    @ApiModelProperty(value = "等待天数", required = true)
    private Integer days;

    public Integer getPerson() {
        return person;
    }

    public void setPerson(Integer person) {
        this.person = person;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
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
        if (!(o instanceof SharePresent)) return false;
        if (!super.equals(o)) return false;
        SharePresent that = (SharePresent) o;
        return Objects.equals(person, that.person) &&
                Objects.equals(first, that.first) &&
                Objects.equals(days, that.days);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), person, first, days);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("person", person)
                .append("first", first)
                .append("days", days)
                .toString();
    }
}
