package com.tuoshecx.server.wx.small;

import com.tuoshecx.server.wx.small.client.request.SendTmpMsgRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 构建发送模板消息
 *
 * @author <a href="mailto:hhywangwei@mgail.com">WangWei</a>
 */
public class SendTmpMsgRequestBuilder {
    private final String openid;
    private final String templateId;
    private final String formId;
    private final List<SendTmpMsgRequest.Item> items;
    private String page;
    private String color;
    private String emphasisKeyword;

    public SendTmpMsgRequestBuilder(String openid, String templateId, String formId){
        this.openid = openid;
        this.templateId = templateId;
        this.formId = formId;
        this.items = new ArrayList<>();
    }

    public SendTmpMsgRequestBuilder setPage(String page){
        this.page = page;
        return this;
    }

    public SendTmpMsgRequestBuilder setColor(String color){
        this.color = color;
        return this;
    }

    public SendTmpMsgRequestBuilder setEmphasisKeyword(String emphasisKeyword){
        this.emphasisKeyword = emphasisKeyword;
        return this;
    }

    public SendTmpMsgRequestBuilder appendItem(String keyword, String value){
        items.add(new SendTmpMsgRequest.Item(keyword, value));
        return this;
    }

    public SendTmpMsgRequestBuilder appendItem(String keyword, String value, String color){
        items.add(new SendTmpMsgRequest.Item(keyword, value, color));
        return this;
    }

    public SendTmpMsgRequest build(String token){
        SendTmpMsgRequest request = new SendTmpMsgRequest(token, openid, templateId, items, formId);
        request.setColor(color);
        request.setEmphasisKeyword(emphasisKeyword);
        request.setPage(page);

        return request;
    }
}
