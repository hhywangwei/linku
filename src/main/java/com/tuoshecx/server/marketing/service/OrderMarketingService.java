package com.tuoshecx.server.marketing.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.marketing.domain.GroupRecord;
import com.tuoshecx.server.marketing.domain.SecondKillRecord;
import com.tuoshecx.server.order.domain.Order;
import com.tuoshecx.server.order.service.OrderService;
import com.tuoshecx.server.user.domain.User;
import com.tuoshecx.server.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

/**
 * 营销活动购买服务
 *
 * @author WangWei
 */
@Service
//@Transactional(readOnly = true)
public class OrderMarketingService {
    private static final Logger logger = LoggerFactory.getLogger(OrderMarketingService.class);

    private final GroupRecordService groupRecordService;
    private final SecondKillRecordService secondKillRecordService;
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public OrderMarketingService(GroupRecordService groupRecordService,
                                 SecondKillRecordService secondKillRecordService,
                                 UserService userService, OrderService orderService) {

        this.groupRecordService = groupRecordService;
        this.secondKillRecordService = secondKillRecordService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Order saveGroupBuy(String userId, String marketingId){
        User u = userService.get(userId);
        GroupRecord r = getGroup(marketingId).orElse(
                groupRecordService.saveGroup(userId, marketingId));

        if(notShop(u, r.getShopId())){
            throw new BaseException("活动不存在");
        }

        return saveOrder(userId, r.getGoodsId(), r.getPrice(), r.getId(), "GROUP_BUY");
    }

    private Optional<GroupRecord> getGroup(String marketingId){
        return groupRecordService.getOption(marketingId);
    }

    private Order saveOrder(String userId, String goodsId, int payTotal, String marketingId, String marketingType){

        return orderService.save(userId, Collections.singletonList(goodsId),
                Collections.singletonList(1), payTotal, marketingId, marketingType);
    }

    private boolean notShop(User u, String shopId){
        return !StringUtils.equals(u.getShopId(), shopId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Order savePresent(String userId, String marketingId){
        User u = userService.get(userId);
        GroupRecord r = getGroup(marketingId).orElse(
                groupRecordService.savePresenter(userId, marketingId));

        if(notShop(u, r.getShopId())){
            throw new BaseException("活动不存在");
        }

        return saveOrder(userId, r.getGoodsId(), r.getPrice(), r.getId(), "SHARE_PRESENT");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Order saveSecondKill(String userId, String marketingId){
        SecondKillRecord m = secondKillRecordService.save(userId, marketingId);
        Order order = saveOrder(userId, m.getGoodsId(), m.getPrice(), marketingId, "SECOND_KILL");
        secondKillRecordService.updateOrderId(m.getId(), order.getId());
        return order;
    }
}
