package com.tuoshecx.server.wx.small.event;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class ProgramAuditEventHandler extends SmallBaseEventHandler {

    @Override
    protected boolean isHandler(Map<String, String> data) {
        String msgType = data.get("MsgType");
        String event = data.get("Event");
        return StringUtils.equals(msgType, "event") && StringUtils.startsWith(event, "weapp_audit");
    }

    @Override
    protected String doHandler(String appid, Map<String, String> data) {
        boolean success = isSuccess(data);
        return "success";
    }

    private boolean isSuccess(Map<String, String> data){
        String event = data.get("Event");
        return StringUtils.equals(event, "weapp_audit_success");
    }
}
