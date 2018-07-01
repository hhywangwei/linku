package com.tuoshecx.server.wx.component.client.response;

import java.util.Collections;
import java.util.Map;

/**
 * 获取授权方的帐号基本信息输出
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class ObtainAuthorizerInfoResponse extends ComponentResponse{
    private final String nickname;
    private final String headImg;
    private final Integer serviceTypeInfo;
    private final Integer verifyTypeInfo;
    private final String username;
    private final String signature;
    private final String principalName;
    private final String qrcodeUrl;
    private final Map<String, Object> businessInfo;
    private final Map<String, Object> miniProgramInfo;
    private final Map<String, Object> authorizationInfo;

    @SuppressWarnings("unchecked")
    public ObtainAuthorizerInfoResponse(Map<String, Object> data) {
        super(data);
        Map<String, Object> info = (Map<String, Object>)data.getOrDefault("authorizer_info", Collections.emptyMap());
        this.nickname = (String)info.getOrDefault("nick_name", "");
        this.headImg = (String)info.getOrDefault("head_img", "");
        this.serviceTypeInfo = (Integer)((Map<String, Object>)info.getOrDefault("service_type_info", Collections.emptyMap())).getOrDefault("id", -1);
        this.verifyTypeInfo = (Integer)((Map<String, Object>)info.getOrDefault("verify_type_info", Collections.emptyMap())).getOrDefault("id", -1);
        this.username = (String)info.getOrDefault("user_name", "");
        this.signature = (String)info.getOrDefault("signature", "");
        this.principalName = (String)info.getOrDefault("principal_name", "");
        this.qrcodeUrl = (String)info.getOrDefault("qrcode_url", "");
        this.businessInfo =(Map<String, Object>)info.getOrDefault("business_info", Collections.emptyMap());
        this.miniProgramInfo = (Map<String, Object>)info.getOrDefault("MiniProgramInfo", Collections.emptyMap());
        this.authorizationInfo = (Map<String, Object>)data.getOrDefault("authorization_info", Collections.emptyMap());
    }

    public String getNickname() {
        return nickname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public Integer getServiceTypeInfo() {
        return serviceTypeInfo;
    }

    public Integer getVerifyTypeInfo() {
        return verifyTypeInfo;
    }

    public String getUsername() {
        return username;
    }

    public String getSignature() {
        return signature;
    }

    public Map<String, Object> getMiniProgramInfo() {
        return miniProgramInfo;
    }

    public Map<String, Object> getAuthorizationInfo() {
        return authorizationInfo;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public Map<String, Object> getBusinessInfo() {
        return businessInfo;
    }
}
