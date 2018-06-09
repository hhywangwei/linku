package com.linku.server.order.event;

/**
 * 营销支付通知
 *
 * @author WangWei
 */
public class MarketingPayEvent {
    private final String id;
    private final String marketingId;
    private final String marketingType;

    public MarketingPayEvent(String id, String marketingId, String marketingType) {
        this.id = id;
        this.marketingId = marketingId;
        this.marketingType = marketingType;
    }

    public String getId() {
        return id;
    }

    public String getMarketingId() {
        return marketingId;
    }

    public String getMarketingType() {
        return marketingType;
    }
}
