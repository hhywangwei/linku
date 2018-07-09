package com.tuoshecx.server.wx.small.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.small.client.request.MessageTemplateDelRequest;
import com.tuoshecx.server.wx.small.client.response.WxSmallResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 *  删除微信消息模板
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class MessageTemplateDelClient extends WxSmallClient<MessageTemplateDelRequest, WxSmallResponse>{

    MessageTemplateDelClient(RestTemplate restTemplate, ObjectMapper objectMapper){
        super(restTemplate, objectMapper, "messageTemplateDel");
    }

    @Override
    protected String buildUri(MessageTemplateDelRequest request) {
        return "https://api.weixin.qq.com/cgi-bin/wxopen/template/del?access_token={token}";
    }

    @Override
    protected Object[] uriParams(MessageTemplateDelRequest request) {
        return new Object[]{request.getToken()};
    }

    @Override
    protected String buildBody(MessageTemplateDelRequest request){
        return String.format("{\"template_id\": \"%s\"}", request.getTemplateId());
    }

    @Override
    protected WxSmallResponse buildResponse(Map<String, Object> data) {
        return new WxSmallResponse(data);
    }
}
