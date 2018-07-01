package com.tuoshecx.server.wx.component.event;

import com.tuoshecx.server.wx.component.token.ComponentVerifyTicketService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;

import java.util.Map;

class VerifyTicketEventHandler extends BaseEventHandler {
    private final ComponentVerifyTicketService service;

    VerifyTicketEventHandler(ComponentVerifyTicketService service) {
        this.service = service;
    }

    @Override
    protected boolean isHandler(String infoType) {
        return StringUtils.equals(infoType, "component_verify_ticket");
    }

    @Override
    protected ResponseEntity<String> doHandler(Map<String, String> data) {
        String ticket = data.get("ComponentVerifyTicket");
        String appid = data.get("AppId");
        service.save(appid, ticket);
        return ResponseEntity.ok("success");
    }
}
