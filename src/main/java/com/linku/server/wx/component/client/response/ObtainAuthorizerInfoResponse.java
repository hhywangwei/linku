package com.linku.server.wx.component.client.response;

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
        Map<String, Object> info = (Map<String, Object>)data.get("authorizer_info");
        this.nickname = (String)info.get("nick_name");
        this.headImg = (String)info.get("head_img");
        this.serviceTypeInfo = (Integer)((Map<String, Object>)info.get("service_type_info")).get("id");
        this.verifyTypeInfo = (Integer)((Map<String, Object>)info.get("verify_type_info")).get("id");
        this.username = (String)info.get("user_name");
        this.signature = (String)info.get("signature");
        this.principalName = (String)info.get("principal_name");
        this.qrcodeUrl = (String)info.get("qrcode_url");
        this.businessInfo =(Map<String, Object>)info.get("business_info");
        this.miniProgramInfo = (Map<String, Object>)info.get("MiniProgramInfo");
        this.authorizationInfo = (Map<String, Object>)info.get("authorization_info");
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
