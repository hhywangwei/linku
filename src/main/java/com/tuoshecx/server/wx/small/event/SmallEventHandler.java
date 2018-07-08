package com.tuoshecx.server.wx.small.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

/**
 * 接收托管公众号微信处理
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public interface SmallEventHandler {
    Logger LOGGER = LoggerFactory.getLogger(SmallBaseEventHandler.class);

    /**
     * 微信推送消息处理接口
     *
     * @param  appid 推送消息所属公众号appid
     * @param  data 推送消息
     * @return 返回客户端消息
     */
    Optional<String> handler(String appid, Map<String, String> data);
}
