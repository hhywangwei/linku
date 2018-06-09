package com.linku.server.wx.pay.client.parse;

/**
 * 微信返回信息解析异常
 *
 * @author WangWei
 */
public class ResponseParseException extends Exception {

    public ResponseParseException(Throwable e){
        super(e);
    }
}
