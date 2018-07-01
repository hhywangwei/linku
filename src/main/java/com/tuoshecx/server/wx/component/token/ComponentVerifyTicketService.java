package com.tuoshecx.server.wx.component.token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 微信第三平台Verify Ticket服务
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@Service
public class ComponentVerifyTicketService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentVerifyTicketService.class);
    private static final String WX_COMPONENT_VERIFY_TICKET_KEY = "tuoshecx.wx.component.verify.ticket";

    private final BoundHashOperations<String, String, String> operations;

    @Autowired
    public ComponentVerifyTicketService(RedisTemplate<String, String> redisTemplate){
        operations = redisTemplate.boundHashOps(WX_COMPONENT_VERIFY_TICKET_KEY);
    }

    public void save(String componentAppid, String ticket){
        LOGGER.debug("Save wx component verify ticket appid {} verify ticket {}", componentAppid, ticket);
        operations.put(componentAppid, ticket);
    }

    public String get(String componentAppid){
        LOGGER.debug("Get wx component verify ticket appid {}", componentAppid);
        return operations.get(componentAppid);
    }
}
