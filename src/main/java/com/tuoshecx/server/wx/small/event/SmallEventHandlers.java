package com.tuoshecx.server.wx.small.event;

import com.tuoshecx.server.common.mime.MimeConverters;
import com.tuoshecx.server.wx.component.client.ComponentClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class SmallEventHandlers {
    private final List<SmallEventHandler> handlers  = new ArrayList<>();

    @Autowired
    public SmallEventHandlers(RestTemplate restTemplate, ComponentClientService clientService) {
        handlers.add(new WxMessageTestHandler(restTemplate, clientService));
        handlers.add(new NoneSmallEventHandler());
    }

    public String handler(String appid, String body){
        Map<String, String> data = MimeConverters.simpleXmlToMap(body);
        for(SmallEventHandler handler: handlers){
            Optional<String> optional = handler.handler(appid, data);
            if(optional.isPresent()){
                return optional.get();
            }
        }

        return "success";
    }

}
