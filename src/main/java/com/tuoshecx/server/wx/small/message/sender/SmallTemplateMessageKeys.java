package com.tuoshecx.server.wx.small.message.sender;

public enum  SmallTemplateMessageKeys {

    ORDER_PAY_SUCCESS("order_pay_success");

    private final String key;

    private SmallTemplateMessageKeys(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
