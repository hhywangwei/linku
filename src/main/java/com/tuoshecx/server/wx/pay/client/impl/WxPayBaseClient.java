package com.tuoshecx.server.wx.pay.client.impl;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.common.client.HttpClient;
import com.tuoshecx.server.wx.pay.client.parse.ResponseParse;
import com.tuoshecx.server.wx.pay.client.parse.ResponseParseException;
import com.tuoshecx.server.wx.pay.client.parse.ResponseParseFactory;
import com.tuoshecx.server.wx.pay.client.request.WxPayRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * 实现微信支付请求
 *
 * @param <T> 请求参数类型
 * @param <R> 输出参数类型
 */
abstract class WxPayBaseClient<T extends WxPayRequest, R> implements HttpClient<T, R> {
    private static final Logger logger = LoggerFactory.getLogger(WxPayBaseClient.class);
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    private final ClientHttpConnector connector;
    private final String apiName;

    WxPayBaseClient(ClientHttpConnector connector, String apiName){
        this.connector = connector;
        this.apiName = apiName;
    }

    @Override
    public R request(T t) {

        WebClient client = WebClient
                .builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .clientConnector(connector)
                .build();

        String data = new String(doRequest(client, t), UTF_8_CHARSET);
        logger.debug("{} response is {}", apiName, data);

        try{
            return parseResponse(data);
        }catch (ResponseParseException e){
            logger.error("Wx pay {} parse fail, error is {}", apiName, e.getMessage());
            throw new BaseException(parseErrorMessage(e));
        }
    }

    /**
     * http请求
     *
     * @param client {@link WebClient}
     * @param t 请求参数
     * @return 输出数据
     */
    protected abstract byte[] doRequest(WebClient client, T t);

    /**
     * 解析返回信息
     *
     * @param data 返回数据
     * @return
     */
    R parseResponse(String data)throws ResponseParseException{
        ResponseParse parse = ResponseParseFactory.xmlParse();
        return parse.parse(data, this::buildResponse);
    }

    /**
     * 构建输出对象
     *
     * @param data 输入数据
     * @return
     */
    protected abstract R buildResponse(Map<String, String> data);

    /**
     * 解析微信支付返回信息错误信息
     *
     * @throws e 异常
     * @return 错误
     */
    private String parseErrorMessage(Exception e){
        return "解析微信返回信息错误";
    }
}
