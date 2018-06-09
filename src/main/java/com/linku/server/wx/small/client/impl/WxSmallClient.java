package com.linku.server.wx.small.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeBase;
import com.linku.server.BaseException;
import com.linku.server.common.client.AsyncHttpClient;
import com.linku.server.wx.configure.properties.WxProperties;
import com.linku.server.wx.small.client.response.WxSmallResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * 实现微信小程序请求
 *
 * @param <T> 输出参数类型
 * @param <R> 输出数据类型
 */
abstract class WxSmallClient<T, R extends WxSmallResponse> implements AsyncHttpClient<T, R> {
    private static final Logger logger = LoggerFactory.getLogger(WxSmallClient.class);

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final WxProperties properties;
    private final String apiName;

    /**
     * 构建请求
     *
     * @param properties {@link WxProperties}
     * @param apiName 调用API名
     */
    WxSmallClient(WxProperties properties, String apiName) {
        this.properties = properties;
        this.apiName = apiName;
    }

    public Mono<R> request(T t){
        WebClient client = WebClient.create();
        return doRequest(client, properties, t).map(this::responseLogger);
    }

    /**
     * 生成请求
     *
     * @param client {@link WebClient}
     * @param p 微信小程序配置属性
     * @param t 请求参数
     * @return
     */
    protected abstract Mono<byte[]> doRequest(WebClient client, WxProperties p, T t);

    /**
     * API接口输出信息日志
     *
     * @param data 输出数据
     * @return 输出对象
     */
    private R responseLogger(byte[] data){
        String content = new String(data, Charset.forName("UTF-8"));
        logger.debug("Response is {}", content);

        try{
            TypeBase mapType = objectMapper.getTypeFactory().
                    constructMapType(Map.class, String.class, Object.class);
            Map<String, Object> map = objectMapper.readValue(content, mapType);

            R r = buildResponse(map);

            if(!r.isOk()){
                logger.error("Request api is {}, code is {}, message is {}",
                        apiName, r.getCode(), r.getMessage());
            }

            return r;
        }catch (Exception e){
            throw new BaseException("解析Json错误");
        }
    }

    /**
     * 构造输出对象
     *
     * @param data 输出数据
     * @return 输出对象
     */
    protected abstract R buildResponse(Map<String, Object> data);
}
