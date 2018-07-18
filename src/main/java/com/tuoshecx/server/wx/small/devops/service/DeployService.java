package com.tuoshecx.server.wx.small.devops.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.shop.domain.ShopWxAuthorized;
import com.tuoshecx.server.shop.service.ShopWxService;
import com.tuoshecx.server.wx.small.client.WxSmallClientService;
import com.tuoshecx.server.wx.small.client.request.SubmitAuditRequest;
import com.tuoshecx.server.wx.small.client.response.GetAuditStatusResponse;
import com.tuoshecx.server.wx.small.client.response.SubmitAuditResponse;
import com.tuoshecx.server.wx.small.client.response.WxSmallResponse;
import com.tuoshecx.server.wx.small.devops.domain.DomainConfigure;
import com.tuoshecx.server.wx.small.devops.domain.SmallAuditConfigure;
import com.tuoshecx.server.wx.small.devops.domain.SmallDeploy;
import com.tuoshecx.server.wx.small.devops.domain.SmallExtConfigure;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 发布小程序服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
public class DeployService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeployService.class);

    private final ShopWxService shopWxService;
    private final SmallDeployService smallDeployService;
    private final SmallDeployLogService smallDeployLogService;
    private final DomainConfigureService domainConfigureService;
    private final SmallExtConfigureService smallExtConfigureService;
    private final WxSmallClientService smallClientService;
    private final SmallAuditConfigureService auditConfigureService;

    @Autowired
    public DeployService(ShopWxService shopWxService, SmallDeployService smallDeployService,
                         SmallDeployLogService smallDeployLogService, DomainConfigureService domainConfigureService,
                         SmallExtConfigureService smallExtConfigureService, WxSmallClientService smallClientService,
                         SmallAuditConfigureService auditConfigureService) {

        this.shopWxService = shopWxService;
        this.smallDeployService = smallDeployService;
        this.smallDeployLogService = smallDeployLogService;
        this.domainConfigureService = domainConfigureService;
        this.smallExtConfigureService = smallExtConfigureService;
        this.smallClientService = smallClientService;
        this.auditConfigureService = auditConfigureService;
    }

    public SmallDeploy deploy(String shopId, Integer templateId){
        List<ShopWxAuthorized> authorizeds = shopWxService.queryAuthorized(shopId);
        if(authorizeds.isEmpty()){
            throw new BaseException("店铺还未托管小程序公众号");
        }
        String appid = authorizeds.get(0).getAppid();

        SmallDeploy t = new SmallDeploy();
        t.setShopId(shopId);
        t.setAppid(appid);
        t.setTemplateId(templateId);
        t.setState("WAIT");
        t.setSetDomain(false);

        return smallDeployService.save(t);
    }

    public SmallDeploy setDomain(String id){
        SmallDeploy t = smallDeployService.get(id);
        Optional<DomainConfigure> optional = domainConfigureService.getOptional();
        if(!optional.isPresent()){
            saveDeployLog(id, "SET_DOMAIN", "Domain配置不存在");
            throw new BaseException("Domain配置不存在");
        }
        if(t.getSetDomain()){
            return t;
        }

        DomainConfigure configure = optional.get();
        WxSmallResponse viewResponse = smallClientService.setWebViewDomain(t.getAppid(), configure.getWebViewDomain());
        if(!viewResponse.isOk()){
            LOGGER.error("Set view domain fail, error {}-{}", viewResponse.getCode(), viewResponse.getMessage());
            saveDeployLog(id, "SET_DOMAIN", String.format("%d-%s", viewResponse.getCode(), viewResponse.getMessage()));
            throw new BaseException("设置业务域失败");
        }

        WxSmallResponse domainResponse = smallClientService.updateDomain(t.getAppid(), configure.getRequestDomain(),
                configure.getWsrequestDomain(), configure.getUploadDomain(), configure.getDownloadDomain());
        if(!domainResponse.isOk() && domainResponse.getCode() != 85017){
            saveDeployLog(id, "SET_DOMAIN", String.format("%d-%s", domainResponse.getCode(), domainResponse.getMessage()));
            throw new BaseException("设置服务器域名失败");
        }

        smallDeployService.setDomain(id);
        saveDeployLog(id, "SET_DOMAIN", "成功设置Domain");
        return smallDeployService.get(id);
    }

    private void saveDeployLog(String id, String action, String message){
        smallDeployLogService.save(id, action, message);
    }

    public SmallDeploy programCommit(String id){
        SmallDeploy t = smallDeployService.get(id);
        WxSmallResponse  response =smallClientService.programCommit(t.getAppid(), t.getTemplateId(),
                "V1.0", "tuoshecx.com", buildExtJson(t.getAppid(), t.getTemplateId()));

        if(response.isOk()){
            saveDeployLog(id, "COMMIT", "提交小程序成功");
            smallDeployService.commit(id);
        }else{
            saveDeployLog(id, "COMMIT", String.format("%d-%s", response.getCode(), response.getMessage()));
        }

        return smallDeployService.get(id);
    }

    private String buildExtJson(String appid, Integer templateId){
        Optional<SmallExtConfigure> optional = smallExtConfigureService.getTemplateId(templateId);
        String m = optional.map(configure -> {
            StringBuilder builder = new StringBuilder(200);
            builder.append("{extAppid:\"").append(appid).append("\"");
            appendNotBlank(builder, "ext", configure.getExt());
            appendNotBlank(builder, "extPages", configure.getExtPages());
            appendNotBlank(builder, "pages", configure.getPages());
            appendNotBlank(builder, "window", configure.getWindow());
            appendNotBlank(builder, "tabBar", configure.getTabBar());
            builder.append("}");
            return builder.toString();
        }).orElseGet(() ->  String.format("{\"extAppid\":\"%s\"}",  appid));
        return StringUtils.replace(m, "\"", "\\\"");
    }

    private void appendNotBlank(StringBuilder builder, String key, String value){
        if(StringUtils.isNotBlank(value)){
            builder.append(String.format(",%s:%s", key, value));
        }
    }

    public SmallDeploy submitAudit(String id){
        SmallDeploy t = smallDeployService.get(id);
        List<SmallAuditConfigure> configures = auditConfigureService.queryAppid(t.getAppid());
        if(configures.isEmpty()){
            throw new BaseException("小程序审核信息不存在");
        }

        SubmitAuditResponse response = smallClientService.submitAudit(t.getAppid(), e -> {
            for(SmallAuditConfigure c: configures){
                e.addItem(new SubmitAuditRequest.SubmitAuditItem(c.getTitle(), c.getAddress(), c.getTag(),
                        c.getFirstClass(), c.getSecondClass(), c.getThirdClass(), c.getFirstId(), c.getSecondId(),
                        c.getThirdId()));
            }
        });

        if(response.isOk()){
            smallDeployService.apply(id, response.getAuditId());
            saveDeployLog(id, "AUDIT", "提交小程序审核成功");
        }else{
            saveDeployLog(id, "AUDIT", String.format("%d-%s", response.getCode(), response.getMessage()));
        }
        return smallDeployService.get(id);
    }

    public SmallDeploy getAuditStatus(String id){
        SmallDeploy t = smallDeployService.get(id);

        if(!isAudit(t.getState())){
            LOGGER.error("shop {} appid {} state {}", t.getShopId(), t.getAppid(), t.getState());
            throw new BaseException("不是提交审核状态");
        }

        GetAuditStatusResponse response = smallClientService.getAuditStatus(t.getAppid(), t.getAuditId());
        if(response.isOk()){
            if(response.getStatus() == 0){
                smallDeployService.auditPass(id);
                saveDeployLog(id, "AUDIT-PASS", "小程序审核通过");
            }else{
                smallDeployService.auditRefuse(id, response.getReason());
                saveDeployLog(id, "AUDIT-REFUSE", response.getReason());
            }
        }else{
            saveDeployLog(id, "GET-AUDIT", String.format("%d-%s", response.getCode(), response.getMessage()));
        }

        return smallDeployService.get(id);
    }

    private boolean isAudit(String state){
        return StringUtils.equals(state, "AUDIT");
    }

    public SmallDeploy release(String id){
        SmallDeploy t = smallDeployService.get(id);

        if(!isAuditPass(t.getState())){
            LOGGER.error("Shop {} appid {} state {}", t.getShopId(), t.getAppid(), t.getState());
            throw new BaseException("小程序未审核通过");
        }

        WxSmallResponse response = smallClientService.promgramRelease(t.getAppid());
        if(response.isOk()){
            smallDeployService.release(id);
            saveDeployLog(id, "RELEASE", "发布成功");
        }else{
            saveDeployLog(id, "RELEASE", String.format("%d-%s", response.getCode(), response.getMessage()));
        }

        return smallDeployService.get(id);
    }

    private boolean isAuditPass(String state){
        return StringUtils.equals(state, "PASS");
    }

}
