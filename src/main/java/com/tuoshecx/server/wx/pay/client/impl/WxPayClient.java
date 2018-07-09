package com.tuoshecx.server.wx.pay.client.impl;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.common.client.HttpClient;
import com.tuoshecx.server.wx.pay.client.parse.ResponseParse;
import com.tuoshecx.server.wx.pay.client.parse.ResponseParseException;
import com.tuoshecx.server.wx.pay.client.parse.ResponseParseFactory;
import com.tuoshecx.server.wx.pay.client.request.WxPayRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现微信支付请求
 *
 * @param <T> 请求参数类型
 * @param <R> 输出参数类型
 */
abstract class WxPayClient<T extends WxPayRequest, R> implements HttpClient<T, R> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WxPayClient.class);
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    private final RestTemplate restTemplate;
    private final String clientName;

    WxPayClient(RestTemplate restTemplate, String clientName){
        this.restTemplate = restTemplate;
        this.clientName = clientName;
    }

    @Override
    public R request(T i) {

        try{
            HttpEntity<byte[]> requestEntity = buildRequestEntity(i);
            return parseResponse(restTemplate.postForEntity(buildUri(),requestEntity, byte[].class));
        }catch (ResponseParseException e){
            LOGGER.error("Wx pay {} parse fail, error is {}", clientName, e.getMessage());
            throw new BaseException("解析微信返回信息错误");
        }
    }

    /**
     * 构建请求对象
     *
     * @param i 请求参数对象
     * @return {@link HttpEntity}
     */
    private HttpEntity<byte[]> buildRequestEntity(T i){
        String body = i.body();
        LOGGER.debug("Wx pay client name {} request body {}", clientName, body);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new HttpEntity<>(body.getBytes(UTF_8_CHARSET), headers);
    }

    /**
     * 构建请求URI
     *
     * @return 访问URI
     */
    protected abstract String buildUri();

    /**
     * 解析返回信息
     *
     * @param response {@link ResponseEntity}
     * @return
     */
    private R parseResponse(ResponseEntity<byte[]> response)throws ResponseParseException{
        if(!response.getStatusCode().is2xxSuccessful()){
            LOGGER.error("Wx small {} http request fail, status code {}", clientName, response.getStatusCodeValue());
            Map<String, String> map = new HashMap<>();
            map.put("err_code", "-2000");
            map.put("err_code_des", "Http state code is " + response.getStatusCodeValue());
            return buildResponse(map);
        }

        String data = new String(response.getBody(), UTF_8_CHARSET);
        LOGGER.debug("Wx pay {} response {}", clientName, data);
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
     * @param  e 异常
     * @return 错误
     */
    private String parseErrorMessage(Exception e){
        return "解析微信返回信息错误";
    }
}
