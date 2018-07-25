package com.tuoshecx.server.marketing.schedule;

import com.tuoshecx.server.marketing.service.GroupRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 营销活动定时处理
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Component
public class MarketingSchedule {
    private static final Logger LOGGER = LoggerFactory.getLogger(MarketingSchedule.class);
    private final GroupRecordService groupRecordService;

    @Autowired
    public MarketingSchedule(GroupRecordService groupRecordService) {
        this.groupRecordService = groupRecordService;
    }

    @Scheduled(fixedDelay = 5 * 60 * 1000L)
    public void closeGroupExpired(){
        try{
            groupRecordService.expire(20);
        }catch (Exception e){
            LOGGER.debug("Close group expired fail, error is {}", e.getMessage());
        }
    }
}
