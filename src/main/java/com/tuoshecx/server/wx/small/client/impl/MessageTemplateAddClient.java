package com.tuoshecx.server.wx.small.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.small.client.request.MessageTemplateAddRequest;
import com.tuoshecx.server.wx.small.client.response.MessageTemplateAddResponse;

import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * 添加微信消息模板
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class MessageTemplateAddClient extends WxSmallClient<MessageTemplateAddRequest, MessageTemplateAddResponse> {
    MessageTemplateAddClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper, "messageTemplateAdd");
    }

    @Override
    protected String buildUri(MessageTemplateAddRequest request) {
        return "https://api.weixin.qq.com/cgi-bin/wxopen/template/add?access_token={token}";
    }

    @Override
    protected Object[] uriParams(MessageTemplateAddRequest request) {
        return new Object[]{request.getToken()};
    }

    @Override
    protected String buildBody(MessageTemplateAddRequest request) {
        StringBuilder builder = new StringBuilder(200);

        builder.append("{\"id\":\"").append(request.getId()).append("\",");
        builder.append("\"keyword_id_list\":[");
        builder.append(request.getKeywordIds().stream().map(String::valueOf).collect(Collectors.joining(",")));
        builder.append("]}");
        return builder.toString();
    }

    @Override
    protected MessageTemplateAddResponse buildResponse(Map<String, Object> data) {
        return new MessageTemplateAddResponse(data);
    }
}
