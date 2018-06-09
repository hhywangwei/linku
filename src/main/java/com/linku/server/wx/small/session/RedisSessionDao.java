package com.linku.server.wx.small.session;

import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Optional;

/**
 * 通过Redis实现用户session_key数据存储操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class RedisSessionDao implements SessionDao {
    private static final String KEY = "wx.small.session_key";

    private final BoundHashOperations<String, Object, Object> operations;

    public RedisSessionDao(StringRedisTemplate redisTemplate) {
        this.operations = redisTemplate.boundHashOps(KEY);
    }

    @Override
    public boolean saveKey(String openid, String sessionKey) {
        operations.put(openid, sessionKey);
        return true;
    }

    @Override
    public Optional<String> getKey(String openid) {
        return Optional.ofNullable((String)operations.get(openid));
    }

    @Override
    public void remove(String openid) {
        operations.delete(openid);
    }
}
