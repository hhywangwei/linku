package com.linku.server.api.vo;

/**
 * 查询记录存在
 *
 * @author WangWei
 */
public class HasVo {
    private final boolean has;

    public HasVo(boolean has) {
        this.has = has;
    }

    public boolean isHas() {
        return has;
    }
}
