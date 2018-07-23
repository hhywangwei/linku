package com.tuoshecx.server.marketing.event;

import com.tuoshecx.server.marketing.domain.GroupRecord;

public class GroupRecordFinishEvent {
    private final String id;
    private final String itemId;
    private final GroupRecord.State state;

    public GroupRecordFinishEvent(String id, String itemId, GroupRecord.State state){
        this.id = id;
        this.itemId = itemId;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getItemId() {
        return itemId;
    }

    public GroupRecord.State getState() {
        return state;
    }
}
