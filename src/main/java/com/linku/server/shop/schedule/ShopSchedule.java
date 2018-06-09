package com.linku.server.shop.schedule;

import com.linku.server.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 店铺定时任务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Component
public class ShopSchedule {
    private static final int LIMIT = 50;
    private final ShopService service;

    @Autowired
    public ShopSchedule(ShopService service) {
        this.service = service;
    }

    @Scheduled(cron = "25 * * * * *")
    public void closeTryTimeout(){
        Date now = new Date();
        int row;
        do{
            row = service.closeTryExpired(now, LIMIT);
        }while (row == LIMIT);
    }
}
