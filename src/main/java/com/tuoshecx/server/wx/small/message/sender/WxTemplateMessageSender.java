package com.tuoshecx.server.wx.small.message.sender;

import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.wx.small.client.WxSmallClientService;
import com.tuoshecx.server.wx.small.client.request.SendTemplateMsgRequest;
import com.tuoshecx.server.wx.small.message.domain.SmallTemplate;
import com.tuoshecx.server.wx.small.message.domain.SendMessage;
import com.tuoshecx.server.wx.small.message.service.SendMessageService;
import com.tuoshecx.server.wx.small.message.service.WxSmallTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    private final WxSmallClientService clientService;

    @Autowired
    public WxTemplateMessageSender(SendMessageService service, WxSmallTemplateService templateService, WxSmallClientService clientService) {
        this.service = service;
        this.templateService = templateService;
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

        SmallTemplate template = optional.get();
        clientService.sendTmpMsg(message.getAppid(), e -> buildRequest(e, template.getTemplateId(),t))
                .subscribe(e -> {
                    if(e.getCode() == 0){
                        service.success(t.getId());
                    }else{
                        service.fail(t.getId(), e.getMessage());
                    }
                });
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
                map(e -> String.format("\"%s\": {\"value\":\"%s\", \"color\":\"%s\"}", e.getKeyword(), e.getValue(), e.getColor())).
                collect(Collectors.joining(","))
                + "}";
    }
}
