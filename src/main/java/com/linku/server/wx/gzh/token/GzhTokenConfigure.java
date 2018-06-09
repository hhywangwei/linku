package com.linku.server.wx.gzh.token;

import com.linku.server.wx.gzh.token.repositories.RedisTokenRepository;
import com.linku.server.wx.gzh.token.repositories.WxTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * {@link GzhTokenService} factory
 *
 * @author WangWei
 */
@Configuration
public class GzhTokenConfigure {
    private static final Logger logger = LoggerFactory.getLogger(GzhTokenConfigure.class);

    @Bean
    @Autowired
    public WxTokenRepository redisTokenRepository(StringRedisTemplate redisTemplate){
        return new RedisTokenRepository(redisTemplate);
    }

    @Bean
    @Autowired
    public static GzhTokenService gzhTokenService(WxTokenRepository tokenRepository){
        return new GzhTokenService(tokenRepository);
    }


}
