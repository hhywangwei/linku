package com.tuoshecx.server.wx.pay.client.parse;

import java.util.Map;
import java.util.function.Function;

/**
 *  微信输出解析
 *
 *  @author WangWei
 */
@FunctionalInterface
public interface ResponseParse {

    /**
     * 解析输出数据到Map中
     *
     * @param body 输出body内容
     * @param map  映射对象处理
     * @return  解析对象
     * @throws ResponseParseException 解析异常
     */
    <T> T parse(String body, Function<Map<String, String>, T> map)throws ResponseParseException;
}
