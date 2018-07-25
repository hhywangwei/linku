package com.tuoshecx.server.wx.small.message.sender;

public enum  SmallTemplateMessageKeys {

    ORDER_PAY_SUCCESS("order_pay_success"),
    GROUP_SUCCESS("group_success"),
    GROUP_FAIL("group_fail"),
    SECOND_KILL_SUCCESS("second_kill_success");

    private final String key;

    private SmallTemplateMessageKeys(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
