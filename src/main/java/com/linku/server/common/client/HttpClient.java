package com.linku.server.common.client;

/**
 * Http客户端
 *
 * @author WangWei
 */
public interface HttpClient<T, R> {

    /**
     * 请求微信支付API
     *
     * @param t 请求参数
     * @return 输出对象
     */
    R request(T t);
}
