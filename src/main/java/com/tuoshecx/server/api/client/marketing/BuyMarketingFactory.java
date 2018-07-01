package com.tuoshecx.server.api.client.marketing;

import com.tuoshecx.server.marketing.service.OrderMarketingService;
import com.tuoshecx.server.order.domain.Order;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * 购买营销活动Factory
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class BuyMarketingFactory {
    private final Map<String, Function<BuyMarketing, Order>> handlers = new HashMap<>(3, 1);

    BuyMarketingFactory(OrderMarketingService service){
        handlers.put("GROUP_BUY", e -> service.saveGroupBuy(e.getUserId(), e.getId()));
        handlers.put("SHARE_PRESENTER", e -> service.savePresent(e.getUserId(), e.getId()));
        handlers.put("SECOND_KILL", e -> service.saveSecondKill(e.getUserId(), e.getId()));
    }

    Optional<Function<BuyMarketing, Order>> buyHandler(String type){
        return Optional.ofNullable(handlers.get(type));
    }

    static class BuyMarketing {
        private final String userId;
        private final String id;

        BuyMarketing(String userId, String id) {
            this.userId = userId;
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public String getId() {
            return id;
        }
    }
}
