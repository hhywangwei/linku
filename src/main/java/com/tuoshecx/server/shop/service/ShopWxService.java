package com.tuoshecx.server.shop.service;

import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.shop.dao.ShopWxAuthorizedDao;
import com.tuoshecx.server.shop.dao.ShopWxTokenDao;
import com.tuoshecx.server.shop.dao.ShopWxUnauthorizedDao;
import com.tuoshecx.server.shop.domain.ShopWxAuthorized;
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

    private final ShopWxAuthorizedDao authorizedDao;
    private final ShopWxTokenDao tokenDao;
    private final ShopWxUnauthorizedDao unauthorizedDao;

    @Autowired
    public ShopWxService(ShopWxAuthorizedDao authorizedDao, ShopWxTokenDao tokenDao, ShopWxUnauthorizedDao unauthorizedDao) {
        this.authorizedDao = authorizedDao;
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
    public void saveAuthorized(ShopWxAuthorized t){
        if(authorizedDao.hasAppid(t.getAppid())){
            authorizedDao.update(t);
        }else{
            t.setId(IdGenerators.uuid());
            authorizedDao.insert(t);
        }
    }

    public Optional<ShopWxAuthorized> getAuthorized(String appid){
        try{
            return Optional.of(authorizedDao.findOneByAppid(appid));
        }catch (DataAccessException e){
            LOGGER.error("Get wx configure {}", e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<String> getAppid(String shopId){
        return queryAuthorized(shopId).stream().map(ShopWxAuthorized::getAppid).findFirst();
    }

    public List<ShopWxAuthorized> queryAuthorized(String shopId){
        return authorizedDao.findByShopId(shopId);
    }

    public List<ShopWxToken> queryExpiresToken(int limit){
       return tokenDao.findExpires(new Date(), limit);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void unauthorized(String appid){
        if(!authorizedDao.hasAppid(appid)){
            LOGGER.debug("unauthorized appid {} not exist", appid);
            return ;
        }

        ShopWxAuthorized configure = authorizedDao.findOneByAppid(appid);
        unauthorizedDao.insert(configure);
        LOGGER.debug("Save {} unauthorized success", appid);
        authorizedDao.delete(appid);
        tokenDao.delete(appid);
    }
}
