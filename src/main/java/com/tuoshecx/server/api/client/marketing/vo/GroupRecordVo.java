package com.tuoshecx.server.api.client.marketing.vo;

import com.tuoshecx.server.marketing.domain.GroupBuy;
import com.tuoshecx.server.marketing.domain.GroupRecord;

public class GroupRecordVo {
    private final GroupBuy info;
    private final GroupRecord record;

    public GroupRecordVo(GroupBuy info, GroupRecord record) {
        this.info = info;
        this.record = record;
    }

    public GroupBuy getInfo() {
        return info;
    }

    public GroupRecord getRecord() {
        return record;
    }
}
