package com.tuoshecx.server.wx.small.message.service;

import com.tuoshecx.server.wx.configure.properties.WxMessageTemplateProperties;
import com.tuoshecx.server.wx.small.client.WxSmallClientService;
import com.tuoshecx.server.wx.small.client.response.MessageTemplateQueryResponse;
import com.tuoshecx.server.wx.small.message.domain.SmallTemplate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 微信消息模板业务管理
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
public class WxSmallTemplateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WxSmallTemplateService.class);

    private final WxMessageTemplateProperties properties;
    private final WxSmallClientService clientService;
    private final SmallTemplateService service;
    private final ApplicationContext applicationContext;

    @Autowired
    public WxSmallTemplateService(WxMessageTemplateProperties properties, WxSmallClientService clientService,
                                  SmallTemplateService service, ApplicationContext applicationContext) {

        this.properties = properties;
        this.clientService = clientService;
        this.service = service;
        this.applicationContext = applicationContext;
    }

    public void refresh(String appid)throws InterruptedException{
        for(WxMessageTemplateProperties.MessageTemplate template: properties.getMsgTemplates()){
            LOGGER.debug("Refresh wx message template appid {} callKey is {}", appid, template.getCallKey());
            deleteTemplateSync(appid, template.getCallKey());
            saveTemplateSync(appid, template);
        }
        applicationContext.publishEvent(new UpdateSmallTemplateEvent(this, appid));
    }

    private void saveTemplateSync(String appid, WxMessageTemplateProperties.MessageTemplate template)throws InterruptedException{
        CountDownLatch latch = new CountDownLatch(1);
        clientService.addMessageTemplate(appid, template.getId(), template.getKeywordIds()).doOnError(e -> {
            LOGGER.error("Add wx message template fail, exception is {}", e.getMessage());
            latch.countDown();
        }).subscribe(e -> {
            if(e.getCode() != 0){
                LOGGER.error("Add wx message template fail, code {} message {}", e.getCode(), e.getMessage());
                latch.countDown();
                return;
            }

            SmallTemplate t = new SmallTemplate();
            t.setGlobalId(template.getId());
            t.setCallKey(template.getCallKey());
            t.setAppid(appid);
            t.setTemplateId(e.getTemplateId());
            t.setRemark(template.getRemark());

            LOGGER.debug("Save wx template, appid {} callKey {}", t.getAppid(), t.getCallKey());
            service.save(t);
            latch.countDown();
        });
        latch.await(8, TimeUnit.SECONDS);
    }

    private void deleteTemplateSync(String appid, String callKey)throws InterruptedException{
        Optional<SmallTemplate> optional = service.get(appid, callKey);
        if(!optional.isPresent()){
            return ;
        }

        SmallTemplate t = optional.get();
        service.delete(t.getId());

        CountDownLatch latch = new CountDownLatch(1);
        String templateId = t.getTemplateId();
        clientService.delMessageTemplate(appid, templateId).doOnError(e -> {
            LOGGER.error("Delete  appid {} templateId {} fail, error is {}" , appid, templateId, e.getMessage());
            latch.countDown();
        }).subscribe(e ->{
            LOGGER.debug("Delete appid{} templateId {} response {}-{}", appid, templateId, e.getCode(), e.getMessage());
            latch.countDown();
        });
        latch.await(8, TimeUnit.SECONDS);
    }

    public void updateTemplateInfoSync(String appid, int offset)throws InterruptedException{
        int count = 20;
        List<SmallTemplate> templates = service.queryAll(appid).stream()
                .filter(e -> StringUtils.isBlank(e.getTitle())).collect(Collectors.toList());

        LOGGER.debug("Update info {} template size is {}", appid, templates.size());

        if(templates.isEmpty()){
            return ;
        }

        CountDownLatch latch = new CountDownLatch(1);
        clientService.queryMessageTemplate(appid, offset, count).doOnError(e -> {
            LOGGER.error("Update {} message template fail, error is {}", appid, e.getMessage());
            latch.countDown();
        }).subscribe(e -> {
            if(e.getTemplates().isEmpty()){
                latch.countDown();
                return;
            }

            for(SmallTemplate t : templates){
                getWxTemplate(e.getTemplates(), t.getTemplateId())
                        .ifPresent(x -> service.updateInfo(t.getId(), x.getTitle(), x.getContent(), x.getExample()));
            }
            latch.countDown();
        });

        latch.await(8, TimeUnit.SECONDS);
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
