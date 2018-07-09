package com.tuoshecx.server.wx.component.token;

import com.tuoshecx.server.wx.component.client.ComponentClientService;
import com.tuoshecx.server.wx.component.client.response.ObtainAccessTokenResponse;
import com.tuoshecx.server.wx.configure.properties.WxComponentProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ComponentTokenSchedule {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentTokenSchedule.class);

    private final WxComponentProperties properties;
    private final ComponentTokenService tokenService;
    private final ComponentClientService clientService;

    @Autowired
    public ComponentTokenSchedule(WxComponentProperties properties,
                                  ComponentTokenService tokenService,
                                  ComponentClientService clientService) {

        this.properties = properties;
        this.tokenService = tokenService;
        this.clientService = clientService;
    }

    @Scheduled(fixedDelay = 3 * 60 * 1000L, initialDelay = 5 * 1000L)
    public void refreshComponentToken(){
        if(tokenService.isExpired(properties.getAppid())){
            try{
                ObtainAccessTokenResponse response = clientService.obtainAccessToken();
                if(response.getCode() == 0) {
                    LOGGER.debug("Refresh component token success ...");
                    tokenService.save(properties.getAppid(), response.getComponentAccessToken(), response.getExpiresIn());
                }else{
                    LOGGER.error("Refresh component token fail, code is {}", response.getCode());
                }
            }catch (Exception e){
                LOGGER.error("refresh component token fail, error is {}", e.getMessage());
            }
        }
    }
}
