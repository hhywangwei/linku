package com.tuoshecx.server.order.event;

import com.tuoshecx.server.order.service.MarketingPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 订单支付发起后续处理
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Component
public class MarketingPayPublisher {
    private final ApplicationContext context;
    private final MarketingPayService service;

    @Autowired
    public MarketingPayPublisher(ApplicationContext context, MarketingPayService service) {
        this.context = context;
        this.service = service;
    }

    public void publishEvent(MarketingPayEvent event){
        service.save(event.getId(), event.getMarketingId(), event.getMarketingType());
        context.publishEvent(event);
    }
}
