package com.tuoshecx.server.order.service;

import com.tuoshecx.server.order.domain.Order;
import com.tuoshecx.server.shop.domain.Shop;
import com.tuoshecx.server.shop.service.ShopService;
import com.tuoshecx.server.wx.small.message.sender.WxTemplateMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderSender {
    private final WxTemplateMessageSender sender;
    private final ShopService shopService;

    @Autowired
    public OrderSender(WxTemplateMessageSender sender, ShopService shopService) {
        this.sender = sender;
        this.shopService = shopService;
    }

    public void send(Order t){
        Shop shop = shopService.get(t.getShopId());
        sender.sendOrderPaySuccess(t.getId(), shop.getName(),  shop.getName() + " 消费");
    }
}
