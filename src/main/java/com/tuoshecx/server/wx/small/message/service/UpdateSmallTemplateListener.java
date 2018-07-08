package com.tuoshecx.server.wx.small.message.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UpdateSmallTemplateListener implements ApplicationListener<UpdateSmallTemplateEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateSmallTemplateListener.class);

    private final WxSmallTemplateService service;

    @Autowired
    public UpdateSmallTemplateListener(WxSmallTemplateService service){
        this.service = service;
    }

    @Async
    @Override
    public void onApplicationEvent(UpdateSmallTemplateEvent event) {
        LOGGER.info("Update {} message template info...", event.getAppid());
        try{
            for(int i = 0; i < 2; i++){
                service.updateTemplateInfoSync(event.getAppid(), i);
            }
        }catch (InterruptedException e){
            LOGGER.error("Update {} message template fail, error is {}", event.getAppid(), e.getMessage());
        }
    }
}
