package com.linku.server.tencent.map.client.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linku.server.tencent.map.client.response.DistrictResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 地理位置缓存
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class DistrictCache {
    private static final Logger logger = LoggerFactory.getLogger(DistrictCache.class);

    private static final Long DEFAULT_EXPIRE_TIME = 7 * 24 *3600 * 1000L;
    private static final String DISTRICT_KEY_PREFIX = "wx_district_";

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public DistrictCache(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public void put(String id, DistrictResponse response){
        String key = buildKey(id);
        toJson(response).ifPresent(e -> {
            redisTemplate.boundValueOps(key).setIfAbsent(e);
            redisTemplate.expire(key, DEFAULT_EXPIRE_TIME, TimeUnit.MILLISECONDS);
        });
    }

    private Optional<String> toJson(DistrictResponse response){
        try{
            return Optional.of(objectMapper.writeValueAsString(response));
        }catch (IOException e){
            logger.error("To json district fail, error is {}", e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<DistrictResponse> get(String id){
        String key = buildKey(id);
        String v = redisTemplate.boundValueOps(key).get();
        return toResponse(v);
    }

    private Optional<DistrictResponse> toResponse(String v){
        if(StringUtils.isBlank(v)){
            return Optional.empty();
        }
        try{
            return Optional.of(objectMapper.readValue(v, DistrictResponse.class));
        }catch (IOException e){
            logger.error("To json district fail, error is {}", e.getMessage());
            return Optional.empty();
        }
    }

    private String buildKey(String id){
        return DISTRICT_KEY_PREFIX + id;
    }

}
