package com.tuoshecx.server.order.event;

/**
 * 营销支付通知
 *
 * @author WangWei
 */
public class PaySuccessEvent {
    private final String id;

    public PaySuccessEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
