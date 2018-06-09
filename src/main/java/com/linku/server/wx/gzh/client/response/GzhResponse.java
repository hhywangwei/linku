package com.linku.server.wx.gzh.client.response;


import java.util.Map;

/**
 * 实现微信公众号API返回输出对象
 *
 * @author WangWei
 */
public class GzhResponse {
    private final int code;
    private final String message;

    public GzhResponse(int code, String message){
        this.code = code;
        this.message = message;
    }

    public GzhResponse(Map<String, Object> data){

        this.code = (Integer) data.getOrDefault("errcode", 0);
        this.message = (String) data.getOrDefault("errmsg", "ok");
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
