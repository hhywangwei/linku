package com.tuoshecx.server.order.event;

import com.tuoshecx.server.marketing.service.GroupRecordService;
import com.tuoshecx.server.marketing.service.SecondKillRecordService;
import com.tuoshecx.server.order.domain.Order;
import com.tuoshecx.server.order.domain.PaySuccess;
import com.tuoshecx.server.order.service.OrderSender;
import com.tuoshecx.server.order.service.OrderService;
import com.tuoshecx.server.order.service.PaySuccessService;
import com.tuoshecx.server.ticket.service.EticketService;
import com.tuoshecx.server.wx.small.message.sender.WxTemplateMessageSender;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PaySuccessListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaySuccessListener.class);

    private final EticketService eticketService;
    private final OrderService orderService;
    private final PaySuccessService paySuccessService;
    private final GroupRecordService groupRecordService;
    private final SecondKillRecordService secondKillRecordService;
    private final OrderSender sender;

    @Autowired
    public PaySuccessListener(EticketService eticketService, OrderService orderService,
                              PaySuccessService paySuccessService, GroupRecordService groupRecordService,
                              SecondKillRecordService secondKillRecordService, OrderSender sender) {

        this.eticketService = eticketService;
        this.orderService = orderService;
        this.paySuccessService = paySuccessService;
        this.groupRecordService = groupRecordService;
        this.secondKillRecordService = secondKillRecordService;
        this.sender = sender;
    }

    @EventListener
    public void listener(PaySuccessEvent event){

        LOGGER.debug("Pay success event order id {}", event.getId());

        Optional<PaySuccess> optional = paySuccessService.getOptional(event.getId());
        if(!optional.isPresent() || optional.get().getState() == PaySuccess.State.SUCCESS){
            return ;
        }

        Order order = orderService.get(event.getId());
        sender.send(order);

        if(isGoods(order)){
            orderService.getItems(order.getId()).forEach(
                    e -> eticketService.save(order.getUserId(), order.getId(), e.getGoodsId()));
            paySuccessService.success(event.getId());
        }

        if(isGroupBuy(order) || isSharePresenter(order)){
            groupRecordService.saveItem(order.getMarketingId(), order.getUserId(), order.getId(), true);
        }

        if (isSecondKill(order)) {
            secondKillRecordService.pay(order.getMarketingId());
        }
        paySuccessService.success(event.getId());
    }

    private boolean isGoods(Order o){
        return StringUtils.equals(o.getMarketingType(), "GOODS");
    }

    private boolean isGroupBuy(Order o){
        return StringUtils.equals(o.getMarketingType(), "GROUP_BUY");
    }

    private boolean isSharePresenter(Order o){
        return StringUtils.equals(o.getMarketingType(), "SHARE_PRESENTER");
    }

    private boolean isSecondKill(Order o){
        return StringUtils.equals(o.getMarketingType(), "SECOND_KILL");
    }

}
