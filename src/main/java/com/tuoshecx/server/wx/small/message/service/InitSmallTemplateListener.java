package com.tuoshecx.server.wx.small.message.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 侦听微信小模板初始事件
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Component
public class InitSmallTemplateListener implements ApplicationListener<InitSmallTemplateEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitSmallTemplateListener.class);

    private final WxSmallTemplateService service;

    @Autowired
    public InitSmallTemplateListener(WxSmallTemplateService service){
        this.service = service;
    }

    @Async
    @Override
    public void onApplicationEvent(InitSmallTemplateEvent event) {
        LOGGER.info("Init {} message template...", event.getAppid());
        service.refresh(event.getAppid());
    }
}
