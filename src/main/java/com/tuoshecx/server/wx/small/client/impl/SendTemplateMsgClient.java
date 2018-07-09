package com.tuoshecx.server.wx.small.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.small.client.request.SendTemplateMsgRequest;
import com.tuoshecx.server.wx.small.client.response.WxSmallResponse;
import org.apache.commons.lang3.StringUtils;

import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 发送微信小程序模板消息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class SendTemplateMsgClient extends WxSmallClient<SendTemplateMsgRequest, WxSmallResponse> {

    SendTemplateMsgClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper, "sendTemplateMessage");
    }

    @Override
    protected String buildUri(SendTemplateMsgRequest request) {
        return "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token={token}";
    }

    @Override
    protected Object[] uriParams(SendTemplateMsgRequest request) {
        return new Object[]{request.getToken()};
    }

    @Override
    protected WxSmallResponse buildResponse(Map<String, Object> data) {
        return new WxSmallResponse(data);
    }

    @Override
    protected String buildBody(SendTemplateMsgRequest request){
        StringBuilder builder = new StringBuilder(200);

        builder.append("{\"touser\":\"").append(request.getOpenid()).append("\",");
        builder.append("\"template_id\":\"").append(request.getTemplateId()).append("\",");
        builder.append("\"form_id\":\"").append(request.getFormId()).append("\",");
        if(StringUtils.isNotBlank(request.getColor())){
            builder.append("\"color\":\"").append(request.getColor()).append("\",");
        }
        if(StringUtils.isNotBlank(request.getEmphasisKeyword())){
            builder.append("\"emphasis_keyword\":\"").append(request.getEmphasisKeyword()).append("\",");
        }
        if(StringUtils.isNotBlank(request.getPage())){
            builder.append("\"page\":\"").append(request.getPage()).append("\",");
        }
        builder.append("\"data\":").append(request.getData()).append("}");

        return builder.toString();
    }
}
