package com.tuoshecx.server.wx.small.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.small.client.request.SendCustomMsgRequest;
import com.tuoshecx.server.wx.small.client.response.WxSmallResponse;

import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * 发生微信客服消息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class SendCustomMsgClient extends WxSmallClient<SendCustomMsgRequest, WxSmallResponse> {

    SendCustomMsgClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper, "sendCustomMsg");
    }

    @Override
    protected String buildUri(SendCustomMsgRequest request) {
        return "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token={token}";
    }

    @Override
    protected Object[] uriParams(SendCustomMsgRequest request) {
        return new Object[]{request.getToken()};
    }

    @Override
    protected WxSmallResponse buildResponse(Map<String, Object> data) {
        return new WxSmallResponse(data);
    }

    @Override
    protected String buildBody(SendCustomMsgRequest request){
        StringBuilder builder = new StringBuilder(200);

        builder.append("{\"touser\":\"").append(request.getOpenid()).append("\",");
        builder.append("\"msgtype\":\"").append(request.getMsgType()).append("\",");
        builder.append("\"").append(request.getMsgType()).append("\":");
        builder.append("{");
        builder.append(buildContent(request.getContent()));
        builder.append("}}");
        return builder.toString();
    }

    private String buildContent(Map<String, String> content){
        return content.entrySet().stream()
                .map(k -> ("\"" + k.getKey() + "\":" + "\"" + k.getValue() + "\""))
                .collect(Collectors.joining(","));
    }
}
