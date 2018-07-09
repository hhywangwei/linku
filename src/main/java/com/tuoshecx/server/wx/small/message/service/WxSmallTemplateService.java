package com.tuoshecx.server.wx.small.message.service;

import com.tuoshecx.server.wx.configure.properties.WxMessageTemplateProperties;
import com.tuoshecx.server.wx.small.client.WxSmallClientService;
import com.tuoshecx.server.wx.small.client.response.MessageTemplateAddResponse;
import com.tuoshecx.server.wx.small.client.response.MessageTemplateQueryResponse;
import com.tuoshecx.server.wx.small.client.response.WxSmallResponse;
import com.tuoshecx.server.wx.small.message.domain.SmallTemplate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 微信消息模板业务管理
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
public class WxSmallTemplateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WxSmallTemplateService.class);
    private static final int MAX_OFFSET = 2;

    private final WxMessageTemplateProperties properties;
    private final WxSmallClientService clientService;
    private final SmallTemplateService service;

    @Autowired
    public WxSmallTemplateService(WxMessageTemplateProperties properties, WxSmallClientService clientService,
                                  SmallTemplateService service) {

        this.properties = properties;
        this.clientService = clientService;
        this.service = service;
    }

    public void refresh(String appid){

        for(WxMessageTemplateProperties.MessageTemplate template: properties.getMsgTemplates()){
            LOGGER.debug("Refresh wx message template appid {} callKey is {}", appid, template.getCallKey());
            deleteTemplateSync(appid, template.getCallKey());
            saveTemplateSync(appid, template);
        }

        for(int i = 0; i < MAX_OFFSET; i++){
            updateTemplateInfoSync(appid, i);
        }
    }

    private void saveTemplateSync(String appid, WxMessageTemplateProperties.MessageTemplate template){

        MessageTemplateAddResponse e = clientService.addMessageTemplate(appid, template.getId(), template.getKeywordIds());

        if(e.getCode() != 0){
            LOGGER.error("Add wx message template fail, code {} message {}", e.getCode(), e.getMessage());
            return;
        }

        SmallTemplate t = new SmallTemplate();
        t.setGlobalId(template.getId());
        t.setCallKey(template.getCallKey());
        t.setAppid(appid);
        t.setTemplateId(e.getTemplateId());
        t.setRemark(template.getRemark());

        service.save(t);
    }

    private void deleteTemplateSync(String appid, String callKey) {
        Optional<SmallTemplate> optional = service.get(appid, callKey);
        if(!optional.isPresent()){
            return ;
        }

        SmallTemplate t = optional.get();
        service.delete(t.getId());

        String templateId = t.getTemplateId();
        WxSmallResponse response = clientService.delMessageTemplate(appid, templateId);
        LOGGER.debug("Delete appid{} templateId {} response {}-{}", appid, templateId, response.getCode(), response.getMessage());
    }

    private void updateTemplateInfoSync(String appid, int offset){
        int count = 20;
        List<SmallTemplate> templates = service.queryAll(appid).stream()
                .filter(e -> StringUtils.isBlank(e.getTitle())).collect(Collectors.toList());

        LOGGER.debug("Update info {} template size is {}", appid, templates.size());

        if(templates.isEmpty()){
            return ;
        }

        MessageTemplateQueryResponse response = clientService.queryMessageTemplate(appid, offset, count);
        if(response.getTemplates().isEmpty()){
            return;
        }

        for(SmallTemplate t : templates){
            getWxTemplate(response.getTemplates(), t.getTemplateId())
                    .ifPresent(x -> service.updateInfo(t.getId(), x.getTitle(), x.getContent(), x.getExample()));
        }
    }

    private Optional<MessageTemplateQueryResponse.Template> getWxTemplate(List<MessageTemplateQueryResponse.Template> wxTemplates, String wxTmpId){
        for(MessageTemplateQueryResponse.Template t : wxTemplates){
            if(StringUtils.equals(t.getId(), wxTmpId)){
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }

    public Optional<SmallTemplate> get(String appid, String callKey){
        return service.get(appid, callKey);
    }

    public long count(String appid, String callKey, String title, String remark){
        return service.count(appid, callKey, title, remark);
    }

    public List<SmallTemplate> query(String appid, String callKey, String title, String remark, int offset, int limit){
        return service.query(appid, callKey, title, remark, offset, limit);
    }
}
