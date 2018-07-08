package com.tuoshecx.server.wx.small.client.impl;

import com.tuoshecx.server.common.client.AsyncHttpClient;
import com.tuoshecx.server.wx.small.client.request.*;
import com.tuoshecx.server.wx.small.client.response.*;

/**
 * 微信小程序API接口请求处理
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class WxSmallClients {
    private final AsyncHttpClient<LoginRequest, LoginResponse> loginClient;
    private final AsyncHttpClient<SendTemplateMsgRequest, WxSmallResponse> sendTmpMsgClient;
    private final AsyncHttpClient<SendCustomMsgRequest, WxSmallResponse> sendCustomMsgClient;
    private final AsyncHttpClient<GetAuditStatusRequest, GetAuditStatusResponse> getAuditStatusClient;
    private final AsyncHttpClient<WxSmallRequest, GetCategoryResponse> getCategoryClient;
    private final AsyncHttpClient<ProgramCommitRequest, WxSmallResponse> programCommitClient;
    private final AsyncHttpClient<WxSmallRequest, WxSmallResponse> programReleaseClient;
    private final AsyncHttpClient<SetWebViewDomainRequest, WxSmallResponse> setWebViewDomainClient;
    private final AsyncHttpClient<SubmitAuditRequest, SubmitAuditResponse> submitAuditClient;
    private final AsyncHttpClient<UpdateDomainRequest, WxSmallResponse> updateDomainClient;
    private final AsyncHttpClient<MessageTemplateAddRequest, MessageTemplateAddResponse> messageTemplateAddClient;
    private final AsyncHttpClient<MessageTemplateDelRequest, WxSmallResponse> messageTemplateDelClient;
    private final AsyncHttpClient<MessageTemplateQueryRequest, MessageTemplateQueryResponse> messageTemplateQueryClient;

    public WxSmallClients() {
        this.loginClient = new LoginClient();
        this.sendTmpMsgClient = new SendTemplateMsgClient();
        this.sendCustomMsgClient = new SendCustomMsgClient();
        this.getAuditStatusClient = new GetAuditStatusClient();
        this.getCategoryClient = new GetCategoryClient();
        this.programCommitClient = new ProgramCommitClient();
        this.programReleaseClient = new ProgramReleaseClient();
        this.setWebViewDomainClient = new SetWebViewDomainClinet();
        this.submitAuditClient = new SubmitAuditClient();
        this.updateDomainClient = new UpdateDomainClient();
        this.messageTemplateAddClient = new MessageTemplateAddClient();
        this.messageTemplateDelClient = new MessageTemplateDelClient();
        this.messageTemplateQueryClient = new MessageTemplateQueryClient();
    }

    /**
     * 用户登陆请求客户端
     *
     * @return {@link LoginClient}
     */
    public AsyncHttpClient<LoginRequest, LoginResponse> loginClient(){
        return loginClient;
    }

    /**
     * 发送模板消息
     *
     * @return {@link SendTemplateMsgClient}
     */
    public AsyncHttpClient<SendTemplateMsgRequest, WxSmallResponse> sendTmpMsgClient(){
        return sendTmpMsgClient;
    }

    /**
     *  发送客户消息
     *
     * @return {@link SendCustomMsgClient}
     */
    public AsyncHttpClient<SendCustomMsgRequest, WxSmallResponse> sendCustomMsgClient() {
        return sendCustomMsgClient;
    }

    public AsyncHttpClient<GetAuditStatusRequest, GetAuditStatusResponse> getAuditStatusClient() {
        return getAuditStatusClient;
    }

    public AsyncHttpClient<WxSmallRequest, GetCategoryResponse> getCategoryClient() {
        return getCategoryClient;
    }

    public AsyncHttpClient<ProgramCommitRequest, WxSmallResponse> programCommitClient() {
        return programCommitClient;
    }

    public AsyncHttpClient<WxSmallRequest, WxSmallResponse> programReleaseClient() {
        return programReleaseClient;
    }

    public AsyncHttpClient<SetWebViewDomainRequest, WxSmallResponse> setWebViewDomainClient() {
        return setWebViewDomainClient;
    }

    public AsyncHttpClient<SubmitAuditRequest, SubmitAuditResponse> submitAuditClient() {
        return submitAuditClient;
    }

    public AsyncHttpClient<UpdateDomainRequest, WxSmallResponse> updateDomainClient() {
        return updateDomainClient;
    }

    public AsyncHttpClient<MessageTemplateAddRequest, MessageTemplateAddResponse> messageTemplateAddClient(){
        return messageTemplateAddClient;
    }

    public AsyncHttpClient<MessageTemplateDelRequest, WxSmallResponse> messageTemplateDelClient(){
        return messageTemplateDelClient;
    }

    public AsyncHttpClient<MessageTemplateQueryRequest, MessageTemplateQueryResponse> messageTemplateQueryClient(){
        return messageTemplateQueryClient;
    }
}
