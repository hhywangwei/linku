package com.tuoshecx.server.wx.small.client.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.common.client.HttpClient;
import com.tuoshecx.server.wx.small.client.request.*;
import com.tuoshecx.server.wx.small.client.response.*;
import org.springframework.web.client.RestTemplate;

/**
 * 微信小程序API接口请求处理
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class WxSmallClients {
    private final HttpClient<LoginRequest, LoginResponse> loginClient;
    private final HttpClient<SendTemplateMsgRequest, WxSmallResponse> sendTmpMsgClient;
    private final HttpClient<SendCustomMsgRequest, WxSmallResponse> sendCustomMsgClient;
    private final HttpClient<GetAuditStatusRequest, GetAuditStatusResponse> getAuditStatusClient;
    private final HttpClient<WxSmallRequest, GetCategoryResponse> getCategoryClient;
    private final HttpClient<ProgramCommitRequest, WxSmallResponse> programCommitClient;
    private final HttpClient<WxSmallRequest, WxSmallResponse> programReleaseClient;
    private final HttpClient<SetWebViewDomainRequest, WxSmallResponse> setWebViewDomainClient;
    private final HttpClient<SubmitAuditRequest, SubmitAuditResponse> submitAuditClient;
    private final HttpClient<UpdateDomainRequest, WxSmallResponse> updateDomainClient;
    private final HttpClient<MessageTemplateAddRequest, MessageTemplateAddResponse> messageTemplateAddClient;
    private final HttpClient<MessageTemplateDelRequest, WxSmallResponse> messageTemplateDelClient;
    private final HttpClient<MessageTemplateQueryRequest, MessageTemplateQueryResponse> messageTemplateQueryClient;

    public WxSmallClients(RestTemplate restTemplate) {
        ObjectMapper objectMapper = initObjectMapper();
        this.loginClient = new LoginClient(restTemplate, objectMapper);
        this.sendTmpMsgClient = new SendTemplateMsgClient(restTemplate, objectMapper);
        this.sendCustomMsgClient = new SendCustomMsgClient(restTemplate, objectMapper);
        this.getAuditStatusClient = new GetAuditStatusClient(restTemplate, objectMapper);
        this.getCategoryClient = new GetCategoryClient(restTemplate, objectMapper);
        this.programCommitClient = new ProgramCommitClient(restTemplate, objectMapper);
        this.programReleaseClient = new ProgramReleaseClient(restTemplate, objectMapper);
        this.setWebViewDomainClient = new SetWebViewDomainClient(restTemplate, objectMapper);
        this.submitAuditClient = new SubmitAuditClient(restTemplate, objectMapper);
        this.updateDomainClient = new UpdateDomainClient(restTemplate, objectMapper);
        this.messageTemplateAddClient = new MessageTemplateAddClient(restTemplate, objectMapper);
        this.messageTemplateDelClient = new MessageTemplateDelClient(restTemplate, objectMapper);
        this.messageTemplateQueryClient = new MessageTemplateQueryClient(restTemplate, objectMapper);
    }

    private ObjectMapper initObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        return mapper;
    }

    /**
     * 用户登陆请求客户端
     *
     * @return {@link LoginClient}
     */
    public HttpClient<LoginRequest, LoginResponse> loginClient(){
        return loginClient;
    }

    /**
     * 发送模板消息
     *
     * @return {@link SendTemplateMsgClient}
     */
    public HttpClient<SendTemplateMsgRequest, WxSmallResponse> sendTmpMsgClient(){
        return sendTmpMsgClient;
    }

    /**
     *  发送客户消息
     *
     * @return {@link SendCustomMsgClient}
     */
    public HttpClient<SendCustomMsgRequest, WxSmallResponse> sendCustomMsgClient() {
        return sendCustomMsgClient;
    }

    public HttpClient<GetAuditStatusRequest, GetAuditStatusResponse> getAuditStatusClient() {
        return getAuditStatusClient;
    }

    public HttpClient<WxSmallRequest, GetCategoryResponse> getCategoryClient() {
        return getCategoryClient;
    }

    public HttpClient<ProgramCommitRequest, WxSmallResponse> programCommitClient() {
        return programCommitClient;
    }

    public HttpClient<WxSmallRequest, WxSmallResponse> programReleaseClient() {
        return programReleaseClient;
    }

    public HttpClient<SetWebViewDomainRequest, WxSmallResponse> setWebViewDomainClient() {
        return setWebViewDomainClient;
    }

    public HttpClient<SubmitAuditRequest, SubmitAuditResponse> submitAuditClient() {
        return submitAuditClient;
    }

    public HttpClient<UpdateDomainRequest, WxSmallResponse> updateDomainClient() {
        return updateDomainClient;
    }

    public HttpClient<MessageTemplateAddRequest, MessageTemplateAddResponse> messageTemplateAddClient(){
        return messageTemplateAddClient;
    }

    public HttpClient<MessageTemplateDelRequest, WxSmallResponse> messageTemplateDelClient(){
        return messageTemplateDelClient;
    }

    public HttpClient<MessageTemplateQueryRequest, MessageTemplateQueryResponse> messageTemplateQueryClient(){
        return messageTemplateQueryClient;
    }
}
