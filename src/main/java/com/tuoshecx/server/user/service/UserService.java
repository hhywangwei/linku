package com.tuoshecx.server.user.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.common.utils.SecurityUtils;
import com.tuoshecx.server.shop.domain.Shop;
import com.tuoshecx.server.shop.service.ShopService;
import com.tuoshecx.server.user.dao.AccountDao;
import com.tuoshecx.server.user.dao.UserDao;
import com.tuoshecx.server.user.domain.Account;
import com.tuoshecx.server.user.domain.User;
import org.apache.commons.lang3.StringUtils;
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
 * 用户业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserDao dao;
    private final AccountDao accountDao;
    private final ShopService shopService;

    @Autowired
    public UserService(UserDao dao, AccountDao accountDao, ShopService shopService){
        this.dao = dao;
        this.accountDao = accountDao;
        this.shopService = shopService;
    }

    public User get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("用户不存在");
        }
    }

    public User getValidate(String username, String password){
        try{
            User u = dao.findOneByUsername(username);
            if(!StringUtils.equals(u.getPassword(), encodePassword(password))){
                throw new BaseException("用户名或密码错误");
            }
            return u;
        }catch (DataAccessException e){
            throw new BaseException("用户名或密码错误");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User saveWx(User t){

        Optional<User> optional = getByOpenid(t.getOpenid());
        if(optional.isPresent()){
            User o = optional.get();

            o.setNickname(t.getNickname());
            o.setHeadImg(t.getHeadImg());
            o.setSex(t.getSex());
            o.setProvince(t.getProvince());
            o.setCity(t.getCity());
            o.setCountry(t.getCountry());

            return update(o);
        }

        logger.debug("Appid is {}", t.getAppid());
        Shop shop = shopService.getByAppid(t.getAppid());
        if(shop.getState() == Shop.State.CLOSE){
            throw new BaseException("店铺已经关闭");
        }

        t.setId(IdGenerators.uuid());
        t.setUsername(SecurityUtils.randomStr(16));
        t.setPassword(SecurityUtils.randomStr(8));
        t.setShopId(shop.getId());
        dao.insert(t);
        saveAccount(t.getId());

        return get(t.getId());
    }

    private void saveAccount(String userId){
        Account t = new Account();
        t.setId(IdGenerators.uuid());
        t.setUserId(userId);
        t.setPayCode(SecurityUtils.randomStr(16));

        accountDao.insert(t);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User saveUsername(String shopId, String username, String password){

        if(dao.hasUsername(username)){
            throw new BaseException("用户名已经存在");
        }

        User t = new User();
        t.setId(IdGenerators.uuid());
        t.setShopId(shopId);
        t.setUsername(username);
        t.setPassword(password);
        t.setOpenid(t.getId());
        t.setAppid(t.getId());
        dao.insert(t);

        saveAccount(t.getId());

        return get(t.getId());
    }

    public boolean lock(String id){
        try{
            return accountDao.lock(id);
        }catch (DataAccessException e){
            throw new BaseException("用户不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User update(User t){
        dao.update(t);
        return get(t.getId());
    }

    public Optional<User> getByOpenid(String openid){
        try{
            return Optional.of(dao.findOneByOpenid(openid));
        }catch (DataAccessException e){
            return Optional.empty();
        }
    }

    public User getByUsername(String username){
        return dao.findOneByUsername(username);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updatePassword(String id, String password, String newPassword){
        User t = get(id);
        if(!StringUtils.equals(t.getPassword(), encodePassword(password))){
            throw new BaseException("密码错误");
        }
        return dao.updatePassword(id, newPassword);
    }

    private String encodePassword(String password){
        return password;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean resetPassword(String id, String newPassword){
        return dao.updatePassword(id, newPassword);
    }

    public Account getAccount(String userId){
        try{
            return accountDao.findOneByUserId(userId);
        }catch (BaseException e){
            throw new BaseException("用户账户不存在");
        }
    }

    public long count(String shopId, String name, String nickname, String phone){
        return dao.count(shopId, name, nickname, phone);
    }

    public List<User> query(String shopId, String name, String nickname, String phone, int offset, int limit){
        return dao.find(shopId, name, nickname, phone, offset, limit);
    }
}
