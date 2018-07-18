package com.tuoshecx.server.wx.small.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.small.client.request.ProgramCommitRequest;
import com.tuoshecx.server.wx.small.client.response.WxSmallResponse;

import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 *  为授权的小程序帐号上传小程序代码
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class ProgramCommitClient extends WxSmallClient<ProgramCommitRequest, WxSmallResponse> {

    ProgramCommitClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper, "programCommit");
    }

    @Override
    protected String buildUri(ProgramCommitRequest request) {
        return "https://api.weixin.qq.com/wxa/commit?access_token={token}";
    }

    @Override
    protected Object[] uriParams(ProgramCommitRequest request) {
        return new Object[]{request.getToken()};
    }

    @Override
    protected String buildBody(ProgramCommitRequest request){
        StringBuilder builder = new StringBuilder(200);

        builder.append("{\"template_id\":\"").append(request.getTemplateId()).append("\",");
        builder.append("\"ext_json\":\"").append(request.getExtJson()).append("\",");
        builder.append("\"user_version\":\"").append(request.getUserVersion()).append("\",");
        builder.append("\"user_desc\":\"").append(request.getUserDesc()).append("\"}");
        return builder.toString();
    }

    @Override
    protected WxSmallResponse buildResponse(Map<String, Object> data) {
        return new WxSmallResponse(data);
    }
}
