package com.tuoshecx.server.marketing.event;

import com.tuoshecx.server.marketing.domain.GroupRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class GroupMessagePublisher {
    private final ApplicationContext context;

    @Autowired
    public GroupMessagePublisher(ApplicationContext context) {
        this.context = context;
    }

    public void publishEvent(String id, GroupRecord.State state){
        context.publishEvent(new GroupMessageEvent(id, state));
    }
}
