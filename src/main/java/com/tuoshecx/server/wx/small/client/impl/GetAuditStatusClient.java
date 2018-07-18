package com.tuoshecx.server.wx.small.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.small.client.request.GetAuditStatusRequest;
import com.tuoshecx.server.wx.small.client.response.GetAuditStatusResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 查询某个指定版本的审核状态（仅供第三方代小程序调用）
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class GetAuditStatusClient extends WxSmallClient<GetAuditStatusRequest, GetAuditStatusResponse> {

    GetAuditStatusClient(RestTemplate restTemplate, ObjectMapper objectMapper){
        super(restTemplate, objectMapper, "getAuditStatus");
    }

    @Override
    protected String buildUri(GetAuditStatusRequest request) {
        return "https://api.weixin.qq.com/wxa/get_auditstatus?access_token={token}";
    }

    @Override
    protected Object[] uriParams(GetAuditStatusRequest request) {
        return new Object[]{request.getToken()};
    }

    @Override
    protected String buildBody(GetAuditStatusRequest request){
        return String.format("{\"auditid\":\"%d\"}", request.getAuditId());
    }

    @Override
    protected GetAuditStatusResponse buildResponse(Map<String, Object> data) {
        return new GetAuditStatusResponse(data);
    }
}
