package com.linku.server.order.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MarketingPayListener {
    private static final Logger logger = LoggerFactory.getLogger(MarketingPayListener.class);

    @EventListener
    public void listener(MarketingPayEvent event){
        logger.debug("Order id is {}", event.getId());
    }
}
