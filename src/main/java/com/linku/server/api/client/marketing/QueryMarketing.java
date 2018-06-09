package com.linku.server.api.client.marketing;

import com.linku.server.api.client.marketing.vo.MarketingVo;
import com.linku.server.api.vo.ResultPageVo;
import com.linku.server.marketing.domain.Marketing;
import com.linku.server.marketing.service.GroupBuyService;
import com.linku.server.marketing.service.SecondKillService;
import com.linku.server.marketing.service.SharePresentService;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 查询营销活动
 *
 * <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class QueryMarketing {
    private static final int OFFSET = 0;
    private static final int MAX = 20;

    private final GroupBuyService groupBuyService;
    private final SharePresentService presentService;
    private final SecondKillService secondKillService;

    QueryMarketing(GroupBuyService groupBuyService, SharePresentService presentService, SecondKillService secondKillService) {
        this.groupBuyService = groupBuyService;
        this.presentService = presentService;
        this.secondKillService = secondKillService;
    }

    ResultPageVo<MarketingVo<Marketing>> query(String shopId, String name, String type, int page, int rows){

        Collection<MarketingVo<Marketing>> data = cache(shopId, name)
                .stream()
                .filter(e -> matchName(e, name) && matchType(e, type))
                .skip(page * rows).limit(rows)
                .collect(Collectors.toList());

        return new ResultPageVo.Builder<>(page, rows, data).build();
    }

    private Collection<MarketingVo<Marketing>> cache(String shopId, String name){
        Set<MarketingVo<Marketing>> all = new TreeSet<>((t1, t2) -> comparator(t1.getMarketing(), t2.getMarketing()));

        int offset = OFFSET;
        int limit = MAX;

        groupBuyService.query(shopId, name, true, offset, limit)
                .forEach(e -> all.add(new MarketingVo<>(e, "GROUP_BUY")));
        presentService.query(shopId, name, true, offset, limit)
                .forEach(e -> all.add(new MarketingVo<>(e, "SHARE_PRESENTER")));
        secondKillService.query(shopId, name, true, offset, limit)
                .forEach(e -> all.add(new MarketingVo<>(e, "SECOND_KILL")));

        return all;
    }

    private int comparator(Marketing t1, Marketing t2){
        int o = t1.getShowOrder() - t2.getShowOrder();
        if(o == 0){
            return (int)(t1.getCreateTime().getTime() - t2.getCreateTime().getTime());
        }
        return o;
    }

    private boolean matchName(MarketingVo<Marketing> vo, String name){
        return StringUtils.isBlank(name) || StringUtils.contains(vo.getMarketing().getName(), name);
    }

    private boolean matchType(MarketingVo<Marketing> vo, String type){
        return StringUtils.isBlank(type) || StringUtils.equals(vo.getType(), type);
    }
}
