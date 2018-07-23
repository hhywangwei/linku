package com.tuoshecx.server.marketing.event;

import com.tuoshecx.server.marketing.domain.GroupRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class GroupRecordFinishPublisher {
    private final ApplicationContext context;

    @Autowired
    public GroupRecordFinishPublisher(ApplicationContext context) {
        this.context = context;
    }

    public void publishEvent(String id, String itemId, GroupRecord.State state){
        context.publishEvent(new GroupRecordFinishEvent(id, itemId, state));
    }
}
