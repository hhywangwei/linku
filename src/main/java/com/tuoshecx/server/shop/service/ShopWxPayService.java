package com.tuoshecx.server.shop.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.shop.dao.ShopWxPayDao;
import com.tuoshecx.server.shop.domain.ShopWxPay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  店铺微信支付配置服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class ShopWxPayService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopWxService.class);

    private final ShopWxPayDao dao;

    @Autowired
    public ShopWxPayService(ShopWxPayDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ShopWxPay save(ShopWxPay t){
        t.setId(IdGenerators.uuid());
        dao.insert(t);

        return dao.findOne(t.getId());
    }

    public ShopWxPay get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            LOGGER.error("Get shop wx pay fail, error is {}", e.getMessage());
            throw new BaseException("得到微信支付配置不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ShopWxPay update(ShopWxPay t){
        dao.update(t);
        return get(t.getId());
    }

    public ShopWxPay getShopId(String shopId){
        try{
            return dao.findOneByShopId(shopId);
        }catch (DataAccessException e){
            LOGGER.error("Get shop wx pay fail, error is {}", e.getMessage());
            throw new BaseException("得到微信支付配置不存在");
        }
    }

    public ShopWxPay getAppid(String appid){
        try{
            return dao.findOneByAppid(appid);
        }catch (DataAccessException e){
            LOGGER.error("Get shop wx pay fail, error is {}", e.getMessage());
            throw new BaseException("得到微信支付配置不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        return dao.delete(id);
    }

    public Long count(String shopId, String appid){
        return dao.count(shopId, appid);
    }

    public List<ShopWxPay> query(String shopId, String appid, int offset, int limit){
        return dao.find(shopId, appid, offset, limit);
    }
}
