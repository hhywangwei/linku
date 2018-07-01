package com.tuoshecx.server.security.token.simple.repository;

import com.tuoshecx.server.security.Credential;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 常量Token保存处理
 *
 * @author WangWei
 */
public class RedisTokenRepository implements TokenRepository {
    private final StringRedisTemplate redisTemplate;
    private final int expiresInMinutes;

    public RedisTokenRepository(StringRedisTemplate redisTemplate, int expiresInMinutes){
        this.redisTemplate = redisTemplate;
        this.expiresInMinutes = expiresInMinutes;
    }

    @Override
    public boolean save(String token, Credential t) {
        String key = buildKey(token);
        redisTemplate.opsForHash().putAll(key, toMap(t));
        setExpire(key);
        return true;
    }

    private void setExpire(String key){
        redisTemplate.expire(key, expiresInMinutes, TimeUnit.MINUTES);
    }

    private String buildKey(String token){
        return "appeme:cloud:sec:token:" + token;
    }

    private Map<String, String> toMap(Credential t){
        Map<String, String> map = new HashMap<>(5);
        map.put("id", t.getId());
        map.put("enter", t.getEnter());
        map.put("type", t.getType());

        if(notEmpty(t.getRoles())){
            map.put("roles", StringUtils.join(t.getRoles(), ","));
        }

        if(notEmpty(t.getAttaches())){
            int size = t.getAttaches().size();
            map.put("attSize", String.valueOf(size));
            for(int i = 0; i < size; i++){
                Credential.Attach attach = t.getAttaches().get(i);
                map.put(attachKey(i), attach.getKey());
                map.put(attachValue(i), attach.getValue());
            }
        }

        return map;
    }

    private String attachKey(int index){
        return String.format("attaches[%d].key", index);
    }

    private String attachValue(int index){
        return String.format("attaches[%d].value", index);
    }

    private boolean notEmpty(List<?> list){
        return list != null && !list.isEmpty();
    }

    @Override
    public Optional<Credential> get(String token) {
        final String key = buildKey(token);
        return Optional.of(redisTemplate.boundHashOps(key).entries())
                .map(e -> {
                    setExpire(key);
                    return toCredential(e);
                });
    }

    private Credential toCredential(Map<Object, Object> map){
        return new Credential((String)map.get("id"), (String)map.get("enter"),
                (String)map.get("type"), toRoles(map), toAttaches(map));
    }

    private List<String> toRoles(Map<Object, Object> map){
        String r = (String)map.getOrDefault("roles", "");
        return Arrays.asList(StringUtils.split(r, ","));
    }

    private List<Credential.Attach> toAttaches(Map<Object, Object> map){

        int size = Integer.valueOf((String)map.getOrDefault("attSize", "0"));
        if(size == 0){
            return Collections.emptyList();
        }

        List<Credential.Attach> attaches = new ArrayList<>(size);
        for(int i = 0; i < size; i++ ){
            String k = (String)map.getOrDefault(attachKey(i), "");
            String v = (String)map.getOrDefault(attachValue(i), "");
            attaches.add(new Credential.Attach(k, v));
        }
        return attaches;
    }

    @Override
    public boolean remove(String token) {
        redisTemplate.delete(buildKey(token));
        return true;
    }
}
