package com.tuoshecx.server.shop.schedule;

import com.tuoshecx.server.shop.domain.ShopWxToken;
import com.tuoshecx.server.shop.service.ShopService;
import com.tuoshecx.server.shop.service.ShopWxService;
import com.tuoshecx.server.wx.component.client.ComponentClientService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 店铺定时任务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Component
public class ShopSchedule {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopSchedule.class);
    private static final int LIMIT = 50;
    private final ShopService service;
    private final ShopWxService wxService;
    private final ComponentClientService clientService;

    @Autowired
    public ShopSchedule(ShopService service, ShopWxService wxService, ComponentClientService clientService) {
        this.service = service;
        this.wxService = wxService;
        this.clientService = clientService;
    }

    @Scheduled(cron = "25 * * * * *")
    public void closeTryTimeout(){
        Date now = new Date();
        int row;
        do{
            row = service.closeTryExpired(now, LIMIT);
        }while (row == LIMIT);
    }

    @Scheduled(fixedDelay = 5 * 60 * 1000L, initialDelay = 30 * 1000L)
    public void refreshToken(){
        int row;
        do{
            List<ShopWxToken> list = wxService.queryExpires(LIMIT);
            list.forEach(this::refreshToken);
            row = list.size();
        }while (row == LIMIT);
    }

    private void refreshToken(ShopWxToken t){
        try{
            clientService.obtainAuthorizerToken(t.getAppid(), t.getRefreshToken()).subscribe(e -> {
                if(e.getCode() == 0){
                    ShopWxToken o = new ShopWxToken();
                    o.setAppid(t.getAppid());
                    o.setAccessToken(e.getAuthorizerAccessToken());
                    o.setRefreshToken(e.getAuthorizerRefreshToken());
                    Date now = new Date();
                    o.setExpiresTime(DateUtils.addSeconds(now, (e.getExpiresIn() - 6 * 60)));
                    o.setUpdateTime(now);

                    wxService.saveToken(o);
                }else{
                    LOGGER.error("Refresh {} token fail, code {} message {}", t.getAppid(), e.getCode(), e.getMessage());
                }
            });
        }catch (Exception e){
            LOGGER.error("Refresh token fail, appid {} error {}", t.getAppid(), e.getMessage());
        }
    }
}
