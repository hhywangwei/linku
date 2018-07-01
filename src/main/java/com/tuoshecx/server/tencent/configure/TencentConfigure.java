package com.tuoshecx.server.tencent.configure;

import com.tuoshecx.server.tencent.configure.properties.TencentProperties;
import com.tuoshecx.server.tencent.map.client.cache.DistrictCache;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 腾讯地理位置配置
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@Configuration
@EnableConfigurationProperties({TencentProperties.class})
public class TencentConfigure {

    @Bean
    public DistrictCache districtCache(RedisTemplate<String, String> redisTemplate){
        return new DistrictCache(redisTemplate);
    }
}
