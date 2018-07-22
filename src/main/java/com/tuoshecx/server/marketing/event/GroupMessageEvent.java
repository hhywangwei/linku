package com.tuoshecx.server.marketing.event;

import com.tuoshecx.server.marketing.domain.GroupRecord;

public class GroupMessageEvent {
    private final String id;
    private final GroupRecord.State state;

    public GroupMessageEvent(String id, GroupRecord.State state) {
        this.id = id;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public GroupRecord.State getState() {
        return state;
    }
}
