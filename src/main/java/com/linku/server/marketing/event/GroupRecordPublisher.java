package com.linku.server.marketing.event;

import com.linku.server.marketing.domain.GroupRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class GroupRecordPublisher {
    private final ApplicationContext context;

    @Autowired
    public GroupRecordPublisher(ApplicationContext context) {
        this.context = context;
    }

    public void publishEvent(String id, GroupRecord.State state){
        if(state == GroupRecord.State.ACTIVATE){
            //TODO 发布激活活动事件
        }
        if(state == GroupRecord.State.CLOSE){
            //TODO 发布关闭活动事件
        }
    }
}
