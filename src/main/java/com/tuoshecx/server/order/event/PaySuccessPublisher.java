package com.tuoshecx.server.order.event;

import com.tuoshecx.server.order.service.PaySuccessService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 订单支付发起后续处理
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Component
public class PaySuccessPublisher {
    private final ApplicationContext context;
    private final PaySuccessService service;

    @Autowired
    public PaySuccessPublisher(ApplicationContext context, PaySuccessService service) {
        this.context = context;
        this.service = service;
    }

    public void publishEvent(PaySuccessEvent event){
        service.save(event.getId(), event.getMarketingId(), event.getMarketingType());
        context.publishEvent(event);
    }
}
