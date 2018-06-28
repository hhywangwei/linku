package com.linku.server.shop.service;

import com.linku.server.common.id.IdGenerators;
import com.linku.server.shop.dao.ShopWxConfigureDao;
import com.linku.server.shop.dao.ShopWxTokenDao;
import com.linku.server.shop.domain.ShopWxConfigure;
import com.linku.server.shop.domain.ShopWxToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    public ShopWxService(ShopWxConfigureDao configureDao, ShopWxTokenDao tokenDao) {
        this.configureDao = configureDao;
        this.tokenDao = tokenDao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveToken(ShopWxToken t){
        if(tokenDao.hasAppid(t.getAppid())){
            tokenDao.updateToken(t.getAppid(), t.getAccessToken(), t.getRefreshToken());
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
}
