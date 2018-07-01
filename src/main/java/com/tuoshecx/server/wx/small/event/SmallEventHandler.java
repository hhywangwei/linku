package com.tuoshecx.server.wx.small.event;

import com.tuoshecx.server.common.utils.SecurityUtils;
import com.tuoshecx.server.wx.component.encrypt.WxEncrypt;
import com.tuoshecx.server.wx.component.encrypt.WxEncryptException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

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
    Optional<ResponseEntity<String>> handler(String appid, Map<String, String> data);

    /**
     * 回复消息加密
     *
     * @param encrypt 加密对象
     * @param message 消息
     * @return
     */
    default String encodeMessage(WxEncrypt encrypt, String message){
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonce = SecurityUtils.randomStr(16);
        try{
            String e = encrypt.encryptMsg(message, timeStamp, nonce);
            LOGGER.debug("Encrypt content is {}", e);
            return e;
        }catch(WxEncryptException e){
            LOGGER.debug("Subscribe message encrypt fail, error is {}", e.getMessage());
        }
        return "";
    }
}
