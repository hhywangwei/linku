package com.linku.server.wx.component.event;

import com.linku.server.common.mime.MimeConverters;
import com.linku.server.wx.component.token.ComponentVerifyTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ComponentEventHandlers {
    private final List<ComponentEventHandler> handlers;

    @Autowired
    public ComponentEventHandlers(ComponentVerifyTicketService verifyTicketService) {
        this.handlers = new ArrayList<>();
        handlers.add(new VerifyTicketEventHandler(verifyTicketService));
        handlers.add(new NoneEventHandler());
    }

    public ResponseEntity<String> handler(String body){
        Map<String, String> data = MimeConverters.simpleXmlToMap(body);
        for(ComponentEventHandler handler: handlers){
            Optional<ResponseEntity<String>> optional = handler.handler(data);
            if(optional.isPresent()){
                return optional.get();
            }
        }

        return ResponseEntity.ok("success");
    }
}