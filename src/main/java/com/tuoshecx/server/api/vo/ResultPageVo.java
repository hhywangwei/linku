package com.tuoshecx.server.api.vo;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * 输出分页记录
 *
 * @author WangWei
 */
public class ResultPageVo<T> extends ResultVo<Collection<T>> {
    @ApiModelProperty(value = "查询当前页数")
    private final int page;
    @ApiModelProperty(value = "每页记录数")
    private final int limit;
    @ApiModelProperty(value = "总记录数，不查询记录时返回-1L")
    private final long count;

    public static class Builder<T> {
        private final int page;
        private final int limit;
        private final Collection<T> rs;
        private Supplier<Long> count = () -> -1L;

        public Builder(int page, int limit, Collection<T> rs){
            this.page = page;
            this.limit = limit;
            this.rs = rs;
        }

        public Builder<T> count(boolean isCount, Supplier<Long> count){
            if(isCount){
                this.count = count;
            }
            return this;
        }

        public ResultPageVo<T> build(){
            return new ResultPageVo<>(page, limit, count.get(), rs);
        }
    }

    private ResultPageVo(int page, int limit, long count, Collection<T> rs){
        super(0, new String[0], rs);
        this.page = page;
        this.limit = limit;
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public int getLimit() {
        return limit;
    }

    public long getCount() {
        return count;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("page", page)
                .append("limit", limit)
                .append("count", count)
                .toString();
    }
}
