package com.tuoshecx.server.wx.small.event;

import com.tuoshecx.server.wx.component.client.ComponentClientService;
import com.tuoshecx.server.wx.component.encrypt.WxEncrypt;
import com.tuoshecx.server.wx.small.client.impl.WxSmallClients;
import com.tuoshecx.server.wx.small.client.request.SendCustomMsgRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

class WxMessageTestHandler extends SmallBaseEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(WxMessageTestHandler.class);
    private final ComponentClientService componentClientService;
    private final WxSmallClients clients;

    WxMessageTestHandler( ComponentClientService clientService) {
        this.componentClientService = clientService;
        this.clients = new WxSmallClients();
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

        LOGGER.debug("WeiXin send message test, auth code is {}", authCode);
        componentClientService.obtainQueryAuth(authCode).subscribe(e -> {
            if(e.getCode() != 0){
                LOGGER.error("Query auth fail, error code {} message", e.getCode(), e.getMessage());
                return ;
            }

            SendCustomMsgRequest request =  SendCustomMsgRequest.buildText(
                    e.getAuthorizerAccessToken(), toUser, buildContent(authCode));

            clients.sendCustomMsgClient().request(request).subscribe(o -> {
                if(o.getCode() == 0){
                    LOGGER.info("Send custom test message success");
                }else{
                    LOGGER.error("Send custom test message fail, code {} message {}", o.getCode(), o.getMessage());
                }
            });
        });

        return "success";
    }

    private String buildContent(String code){
        return String.format("%s_from_api", code);
    }
}
