package com.linku.server.tencent.map.client.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeBase;
import com.linku.server.BaseException;
import com.linku.server.common.client.HttpClient;
import com.linku.server.tencent.map.client.request.QqMapRequest;
import com.linku.server.tencent.map.client.response.QqMapResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.Charset;
import java.util.Map;

public abstract class BaseMapClient<I extends QqMapRequest, R extends QqMapResponse> implements HttpClient<I, R> {
    private static final Logger logger = LoggerFactory.getLogger(BaseMapClient.class);
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    private final ObjectMapper objectMapper;
    private final TypeBase mapType;
    private final String apiName;

    public BaseMapClient(String apiName) {
        this.apiName = apiName;
        this.objectMapper = initObjectMapper();
        this.mapType = objectMapper.getTypeFactory().constructMapType(
                Map.class, String.class, Object.class);
    }

    @Override
    public R request(I i) {
        WebClient client = WebClient
                .builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .build();

        String data = new String(doRequest(client, i), UTF_8_CHARSET);
        logger.debug("{} response is {}", apiName, data);
        try{
            R r =  buildResponse(objectMapper.readValue(data, mapType));
            if(r.getStatus() != 0){
                logger.error("Request api is {}, code is {}, message is {}",
                        apiName, r.getStatus(), r.getMessage());
            }

            return r;
        }catch (Exception e){
            throw new BaseException("解析Json错误");
        }
    }

    /**
     * http请求
     *
     * @param client {@link WebClient}
     * @param i 请求参数
     * @return 输出数据
     */
    protected abstract byte[] doRequest(WebClient client, I i);

    /**
     * 构建输出对象
     *
     * @param data 输入数据
     * @return
     */
    protected abstract R buildResponse(Map<String, Object> data);


    private static ObjectMapper initObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        return mapper;
    }
}
