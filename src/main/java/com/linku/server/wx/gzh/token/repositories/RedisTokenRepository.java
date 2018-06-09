package com.linku.server.wx.gzh.token.repositories;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Redis存储Token操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class RedisTokenRepository implements WxTokenRepository {
    private static final String TOKEN_VALUE_KEY = "wx.token.value";
    private static final String TOKEN_TIME_KEY="wx.token.time";

    private final StringRedisTemplate redisTemplate;

    public RedisTokenRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Optional<String> get(String appid) {
        Double time = redisTemplate.boundZSetOps(TOKEN_TIME_KEY).score(appid);

        if( time == null || time.longValue() < System.currentTimeMillis()){
            return Optional.empty();
        }

        String t = (String)redisTemplate.boundHashOps(TOKEN_VALUE_KEY).get(appid);
        return Optional.ofNullable(t);
    }

    @Override
    public boolean refresh(String appid, String token, int expireSecond) {
        redisTemplate.boundHashOps(TOKEN_VALUE_KEY).put(appid, token);
        long expireMill = System.currentTimeMillis() + (expireSecond - 5 * 60) * 1000L;
        return redisTemplate
                .boundZSetOps(TOKEN_TIME_KEY)
                .add(appid, expireMill);
    }

    @Override
    public Set<String> expired() {
        return redisTemplate
                .boundZSetOps(TOKEN_TIME_KEY)
                .rangeByScore(0, System.currentTimeMillis());
    }
}
