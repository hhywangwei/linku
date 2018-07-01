package com.tuoshecx.server.wx.component.event;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

/**
 * 微信推送消息处理
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public interface ComponentEventHandler {

    /**
     * 微信推送消息处理接口
     *
     * @param  date 推送消息
     * @return 返回客户端消息
     */
    Optional<ResponseEntity<String>> handler(Map<String, String> data);

    /**
     * 得到微信推送消息类型
     *
     * @param data 推送消息
     * @return
     */
    default String getInfoType(Map<String, String> data){
        String infoType = data.get("InfoType");
        return StringUtils.isBlank(infoType) ? "none" : infoType;
    }
}
