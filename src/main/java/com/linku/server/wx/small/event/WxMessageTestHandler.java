package com.linku.server.wx.small.event;

import com.linku.server.wx.component.client.ComponentClientService;
import com.linku.server.wx.component.encrypt.WxEncrypt;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

class WxMessageTestHandler extends SmallBaseEventHandler {
    private static final Logger logger = LoggerFactory.getLogger(WxMessageTestHandler.class);
    private final ComponentClientService clientService;

    WxMessageTestHandler(WxEncrypt encrypt, ComponentClientService clientService) {
        super(encrypt);
        this.clientService = clientService;
    }

    @Override
    protected boolean isHandler(Map<String, String> data) {
        String msgType = data.get("MsgType");
        String content = data.get("Content");
        return StringUtils.isNotBlank(msgType)
                && StringUtils.equals(msgType, "text")
                && StringUtils.startsWith(content, "QUERY_AUTH_CODE");
    }

    @Override
    protected String doHandler(String appid, Map<String, String> data) {
        String toUser = data.get("FromUserName");
        String content = data.get("Content");
        String authCode = StringUtils.remove(content, "QUERY_AUTH_CODE:");

        logger.debug("WeiXin send message test, auth code is {}", authCode);
//        clientService.obtainAuthorizerToken(authCode, o ->{
//            //clientService.customSend(appid, toUser, "text", buildContent(authCode));
//        }, e -> logger.error("Weixin send message test fail, code is {}", e));

        return "success";
    }

    private String buildContent(String code){
        return String.format("\"text\":{\"content\":\"%s_from_api\"}", code);
    }
}
