package com.tuoshecx.server.order.event;

import com.tuoshecx.server.marketing.service.GroupRecordService;
import com.tuoshecx.server.order.domain.Order;
import com.tuoshecx.server.order.service.OrderService;
import com.tuoshecx.server.order.service.PaySuccessService;
import com.tuoshecx.server.ticket.service.EticketService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PaySuccessListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaySuccessListener.class);

    private final EticketService eticketService;
    private final OrderService orderService;
    private final PaySuccessService paySuccessService;
    private final GroupRecordService groupRecordService;

    @Autowired
    public PaySuccessListener(EticketService eticketService, OrderService orderService,
                              PaySuccessService paySuccessService, GroupRecordService groupRecordService) {

        this.eticketService = eticketService;
        this.orderService = orderService;
        this.paySuccessService = paySuccessService;
        this.groupRecordService = groupRecordService;
    }

    @EventListener
    public void listener(PaySuccessEvent event){
        LOGGER.debug("Pay success event order id {}", event.getId());

        if(isGoods(event)){
            Order order = orderService.get(event.getId());
            orderService.getItems(order.getId()).forEach(
                    e -> eticketService.save(order.getUserId(), order.getId(), e.getGoodsId()));
            paySuccessService.success(event.getId());
        }

        if(isGroupBuy(event) || isSharePresenter(event)){
            Order order = orderService.get(event.getId());
            groupRecordService.saveItem(event.getMarketingId(), order.getUserId(), order.getId(), true);
        }
    }

    private boolean isGoods(PaySuccessEvent event){
        return StringUtils.equals(event.getMarketingType(), "GOODS");
    }

    private boolean isGroupBuy(PaySuccessEvent event){
        return StringUtils.equals(event.getMarketingType(), "GROUP_BUY");
    }

    private boolean isSharePresenter(PaySuccessEvent event){
        return StringUtils.equals(event.getMarketingType(), "SHARE_PRESENTER");
    }

}
