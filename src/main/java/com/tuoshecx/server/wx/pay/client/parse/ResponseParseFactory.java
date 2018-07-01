package com.tuoshecx.server.wx.pay.client.parse;

/**
 * 输出解析
 *
 * @author WangWei
 */
public class ResponseParseFactory {
    private static final ResponseParse xmlParse = new XmlResponseParse();

    /**
     * 解析XML处理对象
     *
     * @return 解析XML对象
     */
    public static ResponseParse xmlParse() {
        return xmlParse;
    }
}
