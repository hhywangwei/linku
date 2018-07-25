package com.tuoshecx.server.wx.small.message.sender;

import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.common.utils.DateUtils;
import com.tuoshecx.server.wx.pay.domain.WxUnifiedOrder;
import com.tuoshecx.server.wx.pay.service.WxUnifiedOrderService;
import com.tuoshecx.server.wx.small.client.WxSmallClientService;
import com.tuoshecx.server.wx.small.client.request.SendTemplateMsgRequest;
import com.tuoshecx.server.wx.small.client.response.WxSmallResponse;
import com.tuoshecx.server.wx.small.message.domain.SmallTemplate;
import com.tuoshecx.server.wx.small.message.domain.SendMessage;
import com.tuoshecx.server.wx.small.message.service.SendMessageService;
import com.tuoshecx.server.wx.small.message.service.WxSmallTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 发送微信模板消息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Component
public class WxTemplateMessageSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(WxTemplateMessageSender.class);

    private final SendMessageService service;
    private final WxSmallTemplateService templateService;
    private final WxUnifiedOrderService wxUnifiedOrderService;
    private final WxSmallClientService clientService;

    @Autowired
    public WxTemplateMessageSender(SendMessageService service, WxSmallTemplateService templateService,
                                   WxUnifiedOrderService wxUnifiedOrderService, WxSmallClientService clientService) {
        this.service = service;
        this.templateService = templateService;
        this.wxUnifiedOrderService = wxUnifiedOrderService;
        this.clientService = clientService;
    }

    public void send(SmallTemplateMessage message){
        SendMessage t = saveMessage(message);
        Optional<SmallTemplate> optional = templateService.get(message.getAppid(), message.getCallKey());
        if(!optional.isPresent()){
            service.fail(t.getId(), t.getCallKey() + "模板不存在，可能还未初始化");
            LOGGER.error("Send message call template not exist, appid is {} call key  {}", t.getAppid(), t.getCallKey());
            return;
        }

        try{
            SmallTemplate template = optional.get();
            WxSmallResponse response = clientService.sendTmpMsg(message.getAppid(), e -> buildRequest(e, template.getTemplateId(),t));
            if(response.getCode() == 0){
                service.success(t.getId());
            }else{
                service.fail(t.getId(), response.getMessage());
            }
        }catch (Exception e){
            LOGGER.error("Send mall template message fail, error is {}", e.getMessage());
        }
    }

    private SendMessage saveMessage(SmallTemplateMessage message){
        SendMessage t = new SendMessage();

        t.setId(IdGenerators.uuid());
        t.setAppid(message.getAppid());
        t.setCallKey(message.getCallKey());
        t.setFormId(message.getFormId());
        t.setPage(message.getPage());
        t.setContent(buildData(message.getContentItems()));
        t.setOpenid(message.getOpenid());
        t.setColor(message.getColor());
        t.setEmphasisKeyword(message.getEmphasisKeyword());

        service.save(t);

        return t;
    }

    private SendTemplateMsgRequest buildRequest(String token, String templateId, SendMessage m){
        return new SendTemplateMsgRequest(token, m.getOpenid(), templateId, m.getContent(),
                m.getFormId(), m.getPage(), m.getColor(), m.getEmphasisKeyword());
    }

    private String buildData(List<SmallTemplateMessage.ContentItem> items){
        return "{" +
                items.stream().
                map(e ->StringUtils.isNotBlank(e.getColor())?
                        String.format("\"%s\": {\"value\":\"%s\", \"color\":\"%s\"}", e.getKeyword(), e.getValue(), e.getColor()):
                        String.format("\"%s\": {\"value\":\"%s\"}", e.getKeyword(), e.getValue())).
                collect(Collectors.joining(","))
                + "}";
    }

    public void sendOrderPaySuccess(String orderId, String shopName, String orderContent){

        WxUnifiedOrder o = wxUnifiedOrderService.getOutTradeNo(orderId);
        SmallTemplateMessage message = new SmallTemplateMessage
                .Builder(o.getOpenid(), o.getAppid(), SmallTemplateMessageKeys.ORDER_PAY_SUCCESS.getKey(), o.getPrepay())
                .addContentItem("keyword1", shopName)
                .addContentItem("keyword2", orderContent)
                .addContentItem("keyword3", formatFee(o.getTotalFee()))
                .addContentItem("keyword4", DateUtils.formatTimestamp(o.getPayTime()))
                .setPage("/pages/index/index")
                .build();

        send(message);
    }

    private String formatFee(int totalFee){
        int fen = totalFee % 100;
        int yuan = totalFee / 100;

        return String.format("%d.%02d元", yuan, fen);
    }

    public void sendGroupSuccess(String orderId, String name, Date openTime){
        WxUnifiedOrder o = wxUnifiedOrderService.getOutTradeNo(orderId);
        SmallTemplateMessage message = new SmallTemplateMessage
                .Builder(o.getOpenid(), o.getAppid(), SmallTemplateMessageKeys.GROUP_SUCCESS.getKey(), o.getPrepay())
                .addContentItem("keyword1", name)
                .addContentItem("keyword2", "电子券以生成")
                .addContentItem("keyword3", DateUtils.formatTimestamp(openTime))
                .setPage("/pages/index/index")
                .build();

        send(message);
    }

    public void sendGroupFail(String orderId, String name, int number){
        WxUnifiedOrder o = wxUnifiedOrderService.getOutTradeNo(orderId);
        SmallTemplateMessage message = new SmallTemplateMessage
                .Builder(o.getOpenid(), o.getAppid(), SmallTemplateMessageKeys.GROUP_FAIL.getKey(), o.getPrepay())
                .addContentItem("keyword1", name)
                .addContentItem("keyword2", String.valueOf(number))
                .addContentItem("keyword3", "支付金额将在2工作日内全额退还")
                .setPage("/pages/index/index")
                .build();

        send(message);
    }

    public void sendSecondKillSuccess(String orderId, String name, Date secondKillTime){
        WxUnifiedOrder o = wxUnifiedOrderService.getOutTradeNo(orderId);
        SmallTemplateMessage message = new SmallTemplateMessage
                .Builder(o.getOpenid(), o.getAppid(), SmallTemplateMessageKeys.GROUP_FAIL.getKey(), o.getPrepay())
                .addContentItem("keyword1", name)
                .addContentItem("keyword2", formatFee(o.getTotalFee()))
                .addContentItem("keyword3", DateUtils.formatDate(secondKillTime))
                .setPage("/pages/index/index")
                .build();

        send(message);
    }
}


