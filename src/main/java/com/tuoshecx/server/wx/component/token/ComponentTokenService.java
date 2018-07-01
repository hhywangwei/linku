package com.tuoshecx.server.wx.component.token;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ComponentTokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentTokenService.class);
    private static final String WX_COMPONENT_TOKEN_VALUE_KEY = "tuoshecx.wx.component.token.value";
    private static final String WX_COMPONENT_TOKEN_TIME_KEY = "tuoshecx.wx.component.token.time";

    private final BoundHashOperations<String, String, String> valueOperations;
    private final BoundZSetOperations<String, String> timeOperations;

    @Autowired
    public ComponentTokenService(RedisTemplate<String, String> redisTemplate) {
        this.valueOperations = redisTemplate.boundHashOps(WX_COMPONENT_TOKEN_VALUE_KEY);
        this.timeOperations = redisTemplate.boundZSetOps(WX_COMPONENT_TOKEN_TIME_KEY);
    }

    public Optional<String> get(String componentAppid){
        String token = valueOperations.get(componentAppid);
        if(StringUtils.isBlank(token)){
            LOGGER.debug("{} token not exist", componentAppid);
            return Optional.empty();
        }

        Double score = timeOperations.score(componentAppid);
        long expired = score == null? 0 : score.longValue();
        if(expired < System.currentTimeMillis()){
            LOGGER.debug("{} token is expired", componentAppid);
            return Optional.empty();
        }

        return Optional.of(token);
    }

    public void save(String componentAppid, String token, int expireSeconds){
        LOGGER.debug("Save {} token {} expire seconds {}", componentAppid, token, expireSeconds);

        valueOperations.put(componentAppid, token);
        timeOperations.add(componentAppid, System.currentTimeMillis() + (expireSeconds - 300) * 1000L);
    }

    public boolean isExpired(String componentAppid){
        Double score = timeOperations.score(componentAppid);
        if(score == null){
            return true;
        }
        Long now = System.currentTimeMillis();
        return score.longValue() < now;
    }
}
