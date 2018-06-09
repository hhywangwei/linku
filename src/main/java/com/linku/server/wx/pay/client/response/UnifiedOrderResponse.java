package com.linku.server.wx.pay.client.response;

import com.linku.server.wx.pay.client.TradeType;

import java.util.Map;

/**
 * 微信统一下单输出对象
 *
 * @author WangWei
 */
public class UnifiedOrderResponse extends WxPayResponse {
    private final TradeType tradeType;
    private final String prepayId;
    private final String codeUrl;

    public UnifiedOrderResponse(Map<String, String> data) {
        super(data);
        this.tradeType = isSuccess() ? TradeType.valueOf(data.get("trade_type")) : TradeType.JSAPI;
        this.prepayId = data.getOrDefault("prepay_id", "");
        this.codeUrl = data.getOrDefault("code_url", "");
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public String getCodeUrl() {
        return codeUrl;
    }


}
