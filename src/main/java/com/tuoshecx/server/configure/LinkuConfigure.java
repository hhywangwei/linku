package com.tuoshecx.server.configure;

import com.tuoshecx.server.configure.properties.TokenProperties;
import com.tuoshecx.server.configure.properties.UploadProperties;
import com.tuoshecx.server.wx.configure.properties.WxPayProperties;
import com.tuoshecx.server.security.token.TokenService;
import com.tuoshecx.server.security.token.simple.SimpleTokenService;
import com.tuoshecx.server.security.token.simple.repository.RedisTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 微信支付配置
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@Configuration
@EnableConfigurationProperties({WxPayProperties.class, TokenProperties.class, UploadProperties.class})
public class LinkuConfigure {
    private static final Logger LOGGER = LoggerFactory.getLogger(LinkuConfigure.class);

    @Autowired
    @Bean
    public TokenService tokenService(StringRedisTemplate redisTemplate){
        return new SimpleTokenService(new RedisTokenRepository(redisTemplate, 60));
    }

    @Bean
    public RestTemplate restTemplate(){
        ClientHttpRequestFactory clientHttpRequestFactory = new OkHttp3ClientHttpRequestFactory();
        return new RestTemplate(clientHttpRequestFactory);
    }
}