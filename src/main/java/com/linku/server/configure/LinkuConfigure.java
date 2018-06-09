package com.linku.server.configure;

import com.linku.server.configure.properties.TokenProperties;
import com.linku.server.configure.properties.UploadProperties;
import com.linku.server.wx.configure.properties.WxPayProperties;
import com.linku.server.security.token.TokenService;
import com.linku.server.security.token.simple.SimpleTokenService;
import com.linku.server.security.token.simple.repository.RedisTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 微信支付配置
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@Configuration
@EnableConfigurationProperties({WxPayProperties.class, TokenProperties.class, UploadProperties.class})
public class LinkuConfigure {
    private static final Logger logger = LoggerFactory.getLogger(LinkuConfigure.class);

    @Autowired
    @Bean
    public TokenService tokenService(StringRedisTemplate redisTemplate){
        return new SimpleTokenService(new RedisTokenRepository(redisTemplate, 60));
    }

}
