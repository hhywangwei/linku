package com.tuoshecx.server.order.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.goods.domain.Goods;
import com.tuoshecx.server.goods.service.GoodsService;
import com.tuoshecx.server.order.dao.OrderDao;
import com.tuoshecx.server.order.dao.OrderItemDao;
import com.tuoshecx.server.order.domain.Order;
import com.tuoshecx.server.order.domain.OrderItem;
import com.tuoshecx.server.order.event.PaySuccessEvent;
import com.tuoshecx.server.order.event.PaySuccessPublisher;
import com.tuoshecx.server.shop.domain.Shop;
import com.tuoshecx.server.shop.service.ShopService;
import com.tuoshecx.server.user.domain.User;
import com.tuoshecx.server.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private static final int MAX_TRY = 5;

    private final OrderDao dao;
    private final OrderItemDao itemDao;
    private final UserService userService;
    private final ShopService shopService;
    private final GoodsService goodsService;
    private final PaySuccessPublisher publisher;

    @Autowired
    public OrderService(OrderDao dao, OrderItemDao itemDao,
                        UserService userService, ShopService shopService,
                        GoodsService goodsService, PaySuccessPublisher publisher){

        this.dao = dao;
        this.itemDao = itemDao;
        this.userService = userService;
        this.shopService = shopService;
        this.goodsService = goodsService;
        this.publisher = publisher;
    }

    public Order get(String id){
        try{
            return  dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("订单不存在");
        }
    }

    public List<OrderItem> getItems(String id){
        return itemDao.find(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Order save(String userId, List<String> goodsIds, List<Integer> counts,
                      int payTotal, String marketingId, String marketingType){

        User u = userService.get(userId);
        Shop shop = getValidateShop(u.getShopId());

        List<Goods> goodsLst = goodsIds.stream()
                .map(e -> getValidateGoods(e, u.getShopId()))
                .collect(Collectors.toList());

        return saveOrder(u, goodsLst, counts, payTotal, marketingId, marketingType);
    }

    private Shop getValidateShop(String shopId){
        Shop shop = shopService.get(shopId);
        if(shop.getState() != Shop.State.OPEN){
            throw new BaseException("店铺可能已经停用，无法生成订单");
        }
        return shop;
    }

    private Goods getValidateGoods(String goodsId, String shopId){
        Goods o = goodsService.get(goodsId);
        if(!StringUtils.equals(o.getShopId(), shopId)){
            throw new BaseException("商品不存在");
        }
        return o;
    }

    private Order saveOrder(User user, List<Goods> goodsLst, List<Integer> counts,
                            int payTotal, String marketingId, String marketingType){

        Order t = new Order();

        t.setId(IdGenerators.uuid());
        t.setShopId(user.getShopId());
        t.setUserId(user.getId());
        t.setName(user.getName());
        t.setPhone(user.getPhone());
        t.setDetail(buildDetail(goodsLst));
        int[] totals = totals(goodsLst, counts);
        t.setTotal(totals[0]);
        t.setPayTotal(payTotal == 0? totals[1]: payTotal);
        t.setPayTotal(payTotal);
        t.setCount(counts.stream().mapToInt(e -> e).sum());
        t.setMarketingId(marketingId);
        t.setMarketingType(marketingType);
        t.setState(Order.State.WAIT);

        dao.insert(t);
        saveItems(t.getId(), goodsLst, counts);

        return get(t.getId());
    }

    private int[] totals(List<Goods> goodsLst, List<Integer> counts){
        int total = 0;
        int payTotal = 0;
        for(int i = 0; i < goodsLst.size(); i++){
            Goods g = goodsLst.get(i);
            int c = counts.get(i);
            total = total + g.getPrice() * c;
            payTotal = payTotal + g.getRealPrice() * c;
        }
        return new int[]{total, payTotal};
    }

    private String buildDetail(List<Goods> goodsLst){
        return "[" + goodsLst.stream().map(e ->
                String.format("{\"name\":\"%s\", \"icon\":\"%s\"}", e.getName(), e.getIcon()))
                .collect(Collectors.joining(",")) + "]";
    }

    private void saveItems(String orderId, List<Goods> goodsLst, List<Integer> counts){

        for(int i = 0; i < goodsLst.size(); i++){
            Goods o = goodsLst.get(i);
            Integer count = counts.get(i);
            OrderItem t = new OrderItem();
            t.setId(IdGenerators.uuid());
            t.setOrderId(orderId);
            t.setGoodsId(o.getId());
            t.setName(o.getName());
            t.setIcon(o.getIcon());
            t.setCount(count);
            t.setPrice(o.getRealPrice());
            t.setTotal(o.getRealPrice() * count);
            itemDao.insert(t);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void pay(String id){

        for(int i = 0; i < MAX_TRY; i++ ){
            Order t = get(id);

            if(isPay(t.getState())){
                logger.debug("Order already pay, order id is {} state {}", t.getId(), t.getState().name());
                return ;
            }

            if(!isWait(t.getState())){
                logger.debug("Order already handler, order id is {} state {}", t.getId(), t.getState().name());
                return ;
            }

            if(dao.pay(t.getId(), t.getVersion())){
                if(StringUtils.isNotBlank(t.getMarketingId())){
                    publisher.publishEvent(new PaySuccessEvent(t.getId(), t.getMarketingId(), t.getMarketingType()));
                }else{
                    publisher.publishEvent(new PaySuccessEvent(t.getId(), t.getId(), "GOODS"));
                }
            }
        }

        logger.error("Order pay over max try, id is {}", id);
        throw new BaseException("订单支付失败");
    }

    private boolean isPay(Order.State state){
        return state == Order.State.PAY;
    }

    private boolean isWait(Order.State state){
        return state == Order.State.WAIT;
    }

    private boolean isMarketing(Order t){
        return StringUtils.isNotBlank(t.getMarketingId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void roll(String id){

        for(int i = 0; i < MAX_TRY; i++){
            Order t = get(id);

            if(!isPay(t.getState())){
                throw new BaseException(30001, "订单未支付不能退款");
            }

            if(dao.roll(t.getId(), t.getVersion())){
                return ;
            }
        }

        logger.error("Order roll over max try, id is {}", id);
        throw new BaseException("订单退款失败");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void cancelWaitExpired(int limit){
        for(List<Order> orders = dao.findWaitExpired(limit);; orders = dao.findWaitExpired(limit)){
            orders.forEach(this::cancelWait);
            if(orders.size() != limit){
                break;
            }
        }
    }

    private void cancelWait(Order o){
        dao.cancel(o.getId(), o.getVersion());
    }

    public long count(String shopId, String userId, Order.State state, Date fromTime, Date toTime){
        return dao.count(shopId, userId, state, fromTime, toTime);
    }

    public List<Order> query(String shopId, String userId, Order.State state, Date fromTime, Date toTime, int offset, int limit){
        return dao.find(shopId, userId, state, fromTime, toTime, offset, limit);
    }
}
