package com.tuoshecx.server.wx.small.devops.schedule;

import com.tuoshecx.server.wx.small.devops.service.DeployService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SmallSchedule {

    private final DeployService deployService;

    @Autowired
    public SmallSchedule(DeployService deployService) {
        this.deployService = deployService;
    }

    @Scheduled(fixedDelay = 10 * 60 * 1000L, initialDelay = 3 * 60 * 1000L)
    public void getSmallAuditStatus(){
        deployService.getAuditStatus();
    }
}
