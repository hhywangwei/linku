package com.linku.server.marketing.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

/**
 * 秒杀促销活动
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("秒杀活动")
public class SecondKill extends Marketing {
    @ApiModelProperty(value = "库存")
    private Integer stock;
    @ApiModelProperty(value = "剩余库存")
    private Integer remain;
    @ApiModelProperty(value = "版本号，乐观锁")
    private Integer version;

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getRemain() {
        return remain;
    }

    public void setRemain(Integer remain) {
        this.remain = remain;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SecondKill)) return false;
        if (!super.equals(o)) return false;
        SecondKill that = (SecondKill) o;
        return Objects.equals(stock, that.stock) &&
                Objects.equals(remain, that.remain) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), stock, remain, version);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("stock", stock)
                .append("remain", remain)
                .append("version", version)
                .toString();
    }
}
