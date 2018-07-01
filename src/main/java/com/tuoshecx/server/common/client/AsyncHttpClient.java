package com.tuoshecx.server.common.client;

import reactor.core.publisher.Mono;

/**
 * 异步Http客户端
 *
 * @author WangWei
 */
public interface AsyncHttpClient<T, R> {

    /**
     * 微信API请求
     *
     * @param t 输入信息
     * @return 输出对象
     */
    Mono<R> request(T t);
}
