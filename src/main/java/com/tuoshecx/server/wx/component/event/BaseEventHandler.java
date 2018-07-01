package com.tuoshecx.server.wx.component.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

/**
 * 实现微信第三方平台推送消息处理
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
abstract class BaseEventHandler implements ComponentEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseEventHandler.class);

    @Override
    public Optional<ResponseEntity<String>> handler(Map<String, String> data) {
        if(data.isEmpty()){
            LOGGER.warn("WeiXin push message is empty");
            return Optional.of(ResponseEntity.ok().body("fail"));
        }

        String infoType = getInfoType(data);
        LOGGER.debug("WeiXin push message type is {}", infoType);

        return isHandler(infoType)? Optional.of(doHandler(data)): Optional.empty();
    }

    /**
     * 判断是否处理
     *
     * @param infoType 消息类型
     * @return true:处理，false:不处理
     */
    protected abstract boolean isHandler(String infoType);

    /**
     * 处理消息
     *
     * @param data 消息数据
     * @return 返回处理结果
     */
    protected abstract ResponseEntity<String> doHandler(Map<String, String> data);
}
