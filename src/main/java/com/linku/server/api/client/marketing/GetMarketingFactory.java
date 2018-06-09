package com.linku.server.api.client.marketing;

import com.linku.server.marketing.domain.Marketing;
import com.linku.server.marketing.service.GroupBuyService;
import com.linku.server.marketing.service.SecondKillService;
import com.linku.server.marketing.service.SharePresentService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * 得到营销活动Factory
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class GetMarketingFactory {
    private final Map<String, Function<String, Marketing>> handlers = new HashMap<>(3, 1);

    GetMarketingFactory(GroupBuyService groupBuyService, SharePresentService presentService, SecondKillService secondKillService){
        handlers.put("GROUP_BUY", groupBuyService::get);
        handlers.put("SHARE_PRESENTER", presentService::get);
        handlers.put("SECOND_KILL", secondKillService::get);
    }

    Optional<Function<String, Marketing>> getHandler(String type){
        return Optional.ofNullable(handlers.get(type));
    }
}
