package com.tuoshecx.server.wx.small.event;

import com.tuoshecx.server.wx.component.encrypt.WxEncrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

/**
 * 实现小程序推送信息处理
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
abstract class SmallBaseEventHandler implements SmallEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmallBaseEventHandler.class);

    private final WxEncrypt encrypt;

    SmallBaseEventHandler(WxEncrypt encrypt){
        this.encrypt = encrypt;
    }

    @Override
    public Optional<ResponseEntity<String>> handler(String appid, Map<String, String> data) {
        if(data.isEmpty()){
            LOGGER.warn("WeiXin {} push message is empty", appid);
            return Optional.of(ResponseEntity.ok().body("fail"));
        }

        if(isHandler(data)){
            String m = doHandler(appid , data);
            LOGGER.debug("Small event response is {}", m);
            return Optional.of(ResponseEntity.ok(encodeMessage(encrypt, m)));
        }

        return Optional.empty();
    }

    /**
     * 判断是否处理消息事件
     *
     * @param data 消息对象
     * @return true:处理,false:不处理
     */
    protected abstract boolean isHandler(Map<String, String> data);

    /**
     * 处理消息
     *
     * @param appid 接收消息的公众号appid
     * @param data 消息数据
     * @return 返回处理结果
     */
    protected abstract String doHandler(String appid, Map<String, String> data);
}
