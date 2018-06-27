package com.linku.server.wx.component.client.response;

import java.util.Map;

/**
 * 微信第三方平台输出
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class ComponentResponse {
    private final Integer code;
    private final String message;

    public ComponentResponse(Map<String, Object> data){
        this.code = (Integer) data.getOrDefault("errcode", 0);
        this.message = (String) data.getOrDefault("errmsg", "");
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
