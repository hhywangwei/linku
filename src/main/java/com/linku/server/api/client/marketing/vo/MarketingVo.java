package com.linku.server.api.client.marketing.vo;

import com.linku.server.marketing.domain.Marketing;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 营销活动输出对象
 *
 * @param <T> 活动类型
 */
public class MarketingVo<T extends Marketing> {
    @ApiModelProperty(value = "营销活动")
    private final T marketing;
    @ApiModelProperty(value = "营销活动类型")
    private final String type;

    public MarketingVo(T marketing, String type) {
        this.marketing = marketing;
        this.type = type;
    }

    public T getMarketing() {
        return marketing;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MarketingVo)) return false;
        MarketingVo<?> that = (MarketingVo<?>) o;
        return Objects.equals(marketing, that.marketing) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(marketing, type);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("marketing", marketing)
                .append("type", type)
                .toString();
    }
}
