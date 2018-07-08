package com.tuoshecx.server.wx.small.client.request;

import java.util.List;

/**
 * 发送模板消息请求
 *
 * @author WangWei
 */
public class SendTemplateMsgRequest extends WxSmallRequest {

    private final String openid;
    private final String templateId;
    private final String  data;
    private final String formId;
    private final String page;
    private final String color;
    private final String emphasisKeyword;

    public SendTemplateMsgRequest(String token, String openid, String templateId, String data, String formId,
                                  String page, String color, String emphasisKeyword) {
        super(token);
        this.openid = openid;
        this.templateId = templateId;
        this.data = data;
        this.formId = formId;
        this.page = page;
        this.color = color;
        this.emphasisKeyword = emphasisKeyword;
    }

    public String getOpenid() {
        return openid;
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getData() {
        return data;
    }

    public String getFormId(){
        return formId;
    }

    public String getPage() {
        return page;
    }

    public String getColor() {
        return color;
    }

    public String getEmphasisKeyword() {
        return emphasisKeyword;
    }

}
