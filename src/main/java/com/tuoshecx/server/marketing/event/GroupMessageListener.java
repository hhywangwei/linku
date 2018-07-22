package com.tuoshecx.server.marketing.event;

import com.tuoshecx.server.marketing.domain.GroupRecord;
import com.tuoshecx.server.marketing.domain.GroupRecordItem;
import com.tuoshecx.server.marketing.service.GroupRecordService;
import com.tuoshecx.server.wx.pay.domain.WxUnifiedOrder;
import com.tuoshecx.server.wx.pay.service.WxUnifiedOrderService;
import com.tuoshecx.server.wx.small.message.sender.WxTemplateMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupMessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupMessageListener.class);

    private final WxTemplateMessageSender sender;
    private final GroupRecordService recordService;
    private final WxUnifiedOrderService orderService;

    @Autowired
    public GroupMessageListener(WxTemplateMessageSender sender, GroupRecordService recordService,
                                WxUnifiedOrderService orderService) {
        this.sender = sender;
        this.recordService = recordService;
        this.orderService = orderService;
    }

    @EventListener
    public void listener(GroupMessageEvent event){
        GroupRecord t = recordService.get(event.getId());

        if(isClose(t.getState())){
            sendSuccess(t);
        }
        if(isActive(t.getState())){
            sendFail(t);
        }
    }

    private boolean isClose(GroupRecord.State state){
        return state == GroupRecord.State.CLOSE;
    }

    private boolean isActive(GroupRecord.State state){
        return state == GroupRecord.State.ACTIVATE;
    }

    private void sendSuccess(GroupRecord t){
        List<GroupRecordItem> items = recordService.getItems(t.getId());
        items.forEach(e -> {
            WxUnifiedOrder o = orderService.getOutTradeNo(e.getOrderId());
            sender.sendGroupSuccess(o.getOpenid(), o.getAppid(), o.getPrepay(), t.getName(), t.getCreateTime());
        });
    }

    private void sendFail(GroupRecord t){
        List<GroupRecordItem> items = recordService.getItems(t.getId());
        items.forEach(e -> {
            WxUnifiedOrder o = orderService.getOutTradeNo(e.getOrderId());
            sender.sendGroupFail(o.getOpenid(), o.getAppid(), o.getPrepay(), t.getName(), t.getJoinPerson());
        });
    }

}
