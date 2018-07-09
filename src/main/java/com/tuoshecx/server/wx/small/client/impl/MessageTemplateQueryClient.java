package com.tuoshecx.server.wx.small.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.small.client.request.MessageTemplateQueryRequest;
import com.tuoshecx.server.wx.small.client.response.MessageTemplateQueryResponse;

import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 获取帐号下已存在的模板列表
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
 class MessageTemplateQueryClient extends WxSmallClient<MessageTemplateQueryRequest, MessageTemplateQueryResponse> {

    MessageTemplateQueryClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper, "messageTemplateQuery");
    }

    @Override
    protected String buildUri(MessageTemplateQueryRequest request) {
        return "https://api.weixin.qq.com/cgi-bin/wxopen/template/list?access_token={token}";
    }

    @Override
    protected Object[] uriParams(MessageTemplateQueryRequest request) {
        return new Object[]{request.getToken()};
    }

    @Override
    protected String buildBody(MessageTemplateQueryRequest request){
        StringBuilder builder = new StringBuilder(200);

        builder.append("{\"offset\":").append(request.getOffset()).append(",");
        builder.append("\"count\":").append(request.getCount()).append("}");
        return builder.toString();
    }

    @Override
    protected MessageTemplateQueryResponse buildResponse(Map<String, Object> data) {
        return new MessageTemplateQueryResponse(data);
    }
}
