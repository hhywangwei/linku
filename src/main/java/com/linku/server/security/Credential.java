package com.linku.server.security;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * 用户认证凭证
 *
 * @author WangWei
 */
public class Credential {
    @ApiModelProperty("用户编号")
    private final String id;
    @ApiModelProperty("登陆入口")
    private final String enter;
    @ApiModelProperty(value = "用户类型", allowableValues = "sys, user, manager")
    private final String type;
    @ApiModelProperty("用户角色集合")
    private final List<String> roles;
    @ApiModelProperty("附加信息")
    private final List<Attach> attaches;

    public Credential(String id, String enter, String type, List<String> roles, List<Attach> attaches) {

        this.id = id;
        this.enter = enter;
        this.type = type;
        this.roles = roles;
        this.attaches = attaches;
    }

    public String getId() {
        return id;
    }

    public String getEnter() {
        return enter;
    }

    public String getType() {
        return type;
    }

    public List<String> getRoles() {
        return roles;
    }

    public List<Attach> getAttaches() {
        return attaches;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Credential that = (Credential) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return enter != null ? enter.equals(that.enter) : that.enter == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (enter != null ? enter.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("enter", enter)
                .append("type", type)
                .append("roles", roles)
                .append("attaches", attaches)
                .toString();
    }

    /**
     * 附加信息对象
     *
     * @author WangWei
     */
    public static class Attach {
        private final String key;
        private final String value;

        public Attach(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("key", key)
                    .append("value", value)
                    .toString();
        }
    }
}
