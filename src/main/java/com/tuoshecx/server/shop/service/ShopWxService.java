package com.tuoshecx.server.shop.service;

import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.shop.dao.ShopWxConfigureDao;
import com.tuoshecx.server.shop.dao.ShopWxTokenDao;
import com.tuoshecx.server.shop.dao.ShopWxUnauthorizedDao;
import com.tuoshecx.server.shop.domain.ShopWxConfigure;
import com.tuoshecx.server.shop.domain.ShopWxToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 店铺微信业务服务
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class ShopWxService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopWxService.class);

    private final ShopWxConfigureDao configureDao;
    private final ShopWxTokenDao tokenDao;
    private final ShopWxUnauthorizedDao unauthorizedDao;

    @Autowired
    public ShopWxService(ShopWxConfigureDao configureDao, ShopWxTokenDao tokenDao, ShopWxUnauthorizedDao unauthorizedDao) {
        this.configureDao = configureDao;
        this.tokenDao = tokenDao;
        this.unauthorizedDao = unauthorizedDao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveToken(ShopWxToken t){
        if(tokenDao.hasAppid(t.getAppid())){
            tokenDao.updateToken(t.getAppid(), t.getAccessToken(),
                    t.getRefreshToken(), t.getExpiresTime(), t.getUpdateTime());
        }else{
            t.setId(IdGenerators.uuid());
            tokenDao.insert(t);
        }
    }

    public Optional<ShopWxToken> getToken(String appid){
        try{
            return Optional.of(tokenDao.findOneByAppid(appid));
        }catch (DataAccessException e){
            LOGGER.error("Get wx token {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveConfigure(ShopWxConfigure t){
        if(configureDao.hasAppid(t.getAppid())){
            configureDao.update(t);
        }else{
            t.setId(IdGenerators.uuid());
            configureDao.insert(t);
        }
    }

    public Optional<ShopWxConfigure> getConfigure(String appid){
        try{
            return Optional.of(configureDao.findOneByAppid(appid));
        }catch (DataAccessException e){
            LOGGER.error("Get wx configure {}", e.getMessage());
            return Optional.empty();
        }
    }

    public List<ShopWxConfigure> queryByShopId(String shopId){
        return configureDao.findByShopId(shopId);
    }

    public List<ShopWxToken> queryExpires(int limit){
       return tokenDao.findExpires(new Date(), limit);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void unauthorized(String appid){
        if(!configureDao.hasAppid(appid)){
            LOGGER.debug("unauthorized appid {} not exist", appid);
            return ;
        }

        ShopWxConfigure configure = configureDao.findOneByAppid(appid);
        unauthorizedDao.insert(configure);
        LOGGER.debug("Save {} unauthorized success", appid);
        configureDao.delete(appid);
        tokenDao.delete(appid);
    }
}
