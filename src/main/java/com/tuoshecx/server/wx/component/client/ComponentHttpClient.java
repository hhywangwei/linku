package com.tuoshecx.server.wx.component.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeBase;
import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.common.client.AsyncHttpClient;
import com.tuoshecx.server.wx.component.client.request.ComponentRequest;
import com.tuoshecx.server.wx.component.client.response.ComponentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.function.Function;

/**
 * 实现微信API调用
 *
 * @param <I> 请求类型
 * @param <O> 请求输出类型
 */
public abstract class ComponentHttpClient<I extends ComponentRequest, O extends ComponentResponse> implements AsyncHttpClient<I, O> {
    private static final Logger logger = LoggerFactory.getLogger(ComponentHttpClient.class);

    protected final Charset charsetUTF8 = Charset.forName("UTF-8");
    private static final ObjectMapper objectMapper = initObjectMapper();
    private String apiName;

    /**
     * 构造{@link ComponentHttpClient}
     *
     * @param apiName API名称
     */
	public ComponentHttpClient(String apiName){
	    this.apiName = apiName;
    }

    @Override
    public Mono<O> request(I i) {
        WebClient client = WebClient.builder()
                .defaultHeaders(e -> e.setContentType(MediaType.APPLICATION_JSON_UTF8))
                .build();
        return doRequest(client, i).map(this::responseLogger);
    }

    /**
     * 生成请求
     *
     * @param client {@link WebClient}
     * @param i 请求参数
     * @return
     */
    protected abstract Mono<byte[]> doRequest(WebClient client, I i);

    /**
     * API接口输出信息日志
     *
     * @param data 输出数据
     * @return 输出对象
     */
    private O responseLogger(byte[] data){
        String content = new String(data, Charset.forName("UTF-8"));
        logger.debug("Response is {}", content);

        try{
            TypeBase mapType = objectMapper.getTypeFactory().
                    constructMapType(Map.class, String.class, Object.class);
            Map<String, Object> map = objectMapper.readValue(content, mapType);

            O o = buildResponse(map);

            if(!isOk(o)){
                logger.error("Request api is {}, code is {}, message is {}",
                        apiName, o.getCode(), o.getMessage());
            }

            return o;
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
    protected abstract O buildResponse(Map<String, Object> data);

    /**
     * 访问微信API是否成功
     *
     * @param response 微信输出
     * @return true:成功
     */
	private boolean isOk(ComponentResponse response){
	    return response.getCode() == 0;
    }

    /**
     * 构建URI方法
     *
     * @param func 构建URI方法
     * @return
     */
	protected Function<UriBuilder, URI> buildUri(Function<UriBuilder, URI> func){
	    return e -> {
	        e.scheme("https").host("api.weixin.qq.com");
	        URI uri = func.apply(e);
	        logger.debug("Request uri is {}", uri.toString());
	        return uri;
        };
    }

	private static ObjectMapper initObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        return mapper;
    }
}