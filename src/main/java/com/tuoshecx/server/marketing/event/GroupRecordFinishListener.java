package com.tuoshecx.server.marketing.event;

import com.tuoshecx.server.marketing.domain.GroupRecord;
import com.tuoshecx.server.marketing.domain.GroupRecordItem;
import com.tuoshecx.server.marketing.service.GroupRecordFinishService;
import com.tuoshecx.server.marketing.service.GroupRecordService;
import com.tuoshecx.server.order.domain.OrderItem;
import com.tuoshecx.server.order.service.OrderService;
import com.tuoshecx.server.ticket.service.EticketService;
import com.tuoshecx.server.wx.pay.service.WxPayService;
import com.tuoshecx.server.wx.small.message.sender.WxTemplateMessageSender;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 组团营销结束处理。组团成功发通知信息并生成电子券;不成功发送消息并退款;
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Component
public class GroupRecordFinishListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupRecordFinishListener.class);

    private final WxTemplateMessageSender sender;
    private final GroupRecordService recordService;
    private final EticketService eticketService;
    private final GroupRecordFinishService recordFinishService;
    private final OrderService orderService;
    private final WxPayService wxPayService;

    @Autowired
    public GroupRecordFinishListener(WxTemplateMessageSender sender, GroupRecordService recordService,
                                     EticketService eticketService, GroupRecordFinishService recordFinishService,
                                     OrderService orderService, WxPayService wxPayService) {
        this.sender = sender;
        this.recordService = recordService;
        this.eticketService = eticketService;
        this.recordFinishService = recordFinishService;
        this.orderService = orderService;
        this.wxPayService = wxPayService;
    }

    @EventListener
    public void listen(GroupRecordFinishEvent event){

        LOGGER.debug("Group record finish listen id {} state {}", event.getId(), event.getState());

        GroupRecord t = recordService.get(event.getId());

        try{
            if(isClose(t.getState())){
                sendSuccess(t, event.getItemId());
            }
            if(isActive(t.getState())){
                sendFail(t);
            }
            recordFinishService.success(t.getId(), event.getItemId());
        }catch (Exception e){
            LOGGER.error("Group record finish id {} handle fail, error is {}", event.getId(), e.getMessage());
            recordFinishService.fail(t.getId(), event.getItemId(), StringUtils.left(e.getMessage(), 200));
        }
    }

    private boolean isClose(GroupRecord.State state){
        return state == GroupRecord.State.CLOSE;
    }

    private boolean isActive(GroupRecord.State state){
        return state == GroupRecord.State.ACTIVATE;
    }

    private void sendSuccess(GroupRecord t, String itemId){
        boolean all = StringUtils.equals(itemId, "*");
        List<GroupRecordItem> items = all?
                recordService.getItems(t.getId()): Collections.singletonList(recordService.getItem(itemId));

        items.forEach(e -> {
            genEticket(e.getOrderId(), e.getUserId());
            sender.sendGroupSuccess(e.getOrderId(), t.getName(), t.getCreateTime());
        });
    }

    private void genEticket(String orderId, String userId){
        List<OrderItem> items = orderService.getItems(orderId);
        items.forEach(e -> eticketService.save(userId, orderId, e.getGoodsId()));
    }

    private void sendFail(GroupRecord t){
        List<GroupRecordItem> items = recordService.getItems(t.getId());
        items.forEach(e -> {
            sender.sendGroupFail(e.getOrderId(), t.getName(), t.getJoinPerson());
            wxPayService.refund(e.getOrderId(), "未达开团人数");
        });
    }
}
