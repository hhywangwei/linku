package com.tuoshecx.server.wx.small.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.wx.small.client.request.SetWebViewDomainRequest;
import com.tuoshecx.server.wx.small.client.response.WxSmallResponse;

import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 设置小程序业务域名（仅供第三方代小程序调用）
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class SetWebViewDomainClient extends WxSmallClient<SetWebViewDomainRequest, WxSmallResponse> {

    SetWebViewDomainClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper,"setWebViewDomain");
    }

    @Override
    protected String buildUri(SetWebViewDomainRequest request) {
        return "https://api.weixin.qq.com/wxa/setwebviewdomain?access_token={token}";
    }

    @Override
    protected Object[] uriParams(SetWebViewDomainRequest request) {
        return new Object[]{request.getToken()};
    }

    @Override
    protected String buildBody(SetWebViewDomainRequest request){
        StringBuilder builder = new StringBuilder(50);

        builder.append("{\"action\":\"").append(request.getAction()).append("\",");
        builder.append("\"webviewdomain\":").append(buildArray(request.getWebViewDomain())).append("}");

        return builder.toString();
    }

    private String buildArray(String[] array){
        return "[" + Arrays.stream(array).map(e -> "\"" + e + "\"").collect(Collectors.joining(",")) + "]";
    }

    @Override
    protected WxSmallResponse buildResponse(Map<String, Object> data) {
        return new WxSmallResponse(data);
    }
}
