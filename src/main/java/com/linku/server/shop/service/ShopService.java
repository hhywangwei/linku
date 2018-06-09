package com.linku.server.shop.service;

import com.linku.server.BaseException;
import com.linku.server.common.id.IdGenerators;
import com.linku.server.common.utils.HtmlUtils;
import com.linku.server.shop.dao.ShopDao;
import com.linku.server.shop.dao.WxConfigureDao;
import com.linku.server.shop.domain.Manager;
import com.linku.server.shop.domain.Shop;
import com.linku.server.shop.domain.WxConfigure;
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

/**
 *  店铺业务服务
 *
 *  @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class ShopService {
    private static final Logger logger = LoggerFactory.getLogger(ShopService.class);

    private final ShopDao dao;
    private final WxConfigureDao wxConfigureDao;
    private final ManagerService managerService;

    @Autowired
    public ShopService(ShopDao dao, WxConfigureDao wxConfigureDao, ManagerService managerService){
        this.dao = dao;
        this.wxConfigureDao = wxConfigureDao;
        this.managerService = managerService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Shop save(Shop t, String manager, String password){
        t.setId(IdGenerators.uuid());
        t.setState(Shop.State.WAIT);
        if(t.getTryUse() && t.getTryFromTime() == null){
            t.setTryFromTime(new Date());
        }
        if(StringUtils.isBlank(t.getSummary())){
            t.setSummary(summary(t.getDetail()));
        }
        dao.insert(t);

        saveManager(t.getId(), manager, password);

        return get(t.getId());
    }

    private String summary(String detail){
        return HtmlUtils.text(detail, 150);
    }

    private void saveManager(String shopId, String manager, String password){
        Manager t = new Manager();

        t.setId(IdGenerators.uuid());
        t.setShopId(shopId);
        t.setUsername(manager);
        t.setPassword(password);
        t.setName("");
        t.setPhone("");
        t.setEnable(true);
        t.setManager(true);
        t.setRoles(new String[]{"ROLE_SHOP_ADMIN"});
        managerService.save(t);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Shop update(Shop t){
        Shop o = get(t.getId());

        if(StringUtils.isBlank(t.getSummary())){
            t.setSummary(summary(t.getDetail()));
        }

        t.setTryUse(o.getTryUse());
        t.setTryFromTime(o.getTryFromTime());
        t.setTryToTime(o.getTryToTime());

        if(!dao.update(t)){
            throw new BaseException("修改店铺失败");
        }
        return get(t.getId());
    }

    public Shop get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            logger.warn("Get shop fail, error is {}", e.getMessage());
            throw new BaseException("店铺不存在");
        }
    }

    public Shop getByAppid(String appid){
        try{
            logger.debug("Appid is {}", appid);
            WxConfigure c = wxConfigureDao.findOneByAppid(appid);
            logger.debug("Shop id is {}", c.getShopId());
            return get(c.getShopId());
        }catch (DataAccessException e){
            throw new BaseException("店铺不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        Shop t = get(id);

        if(t.getState() == Shop.State.OPEN){
            throw new BaseException("发布店铺不能直接删除");
        }

        boolean ok = dao.delete(id);
        if(ok){
            managerService.inactiveShop(id);
        }

        return ok;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Shop open(String id){
        Shop t = get(id);

        boolean ok = dao.updateState(id, Shop.State.OPEN);
        if(ok && t.getState() == Shop.State.CLOSE){
            managerService.activeManager(id);
        }

        return get(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Shop close(String id){
        Shop t = get(id);

        if(t.getState() == Shop.State.CLOSE){
            throw new BaseException("店铺已经关闭");
        }
        if(dao.updateState(id, Shop.State.CLOSE)){
            managerService.inactiveShop(id);
        }

        return get(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Shop incTryUseTime(String id, Date tryToTime){
        Shop o = get(id);

        if(!o.getTryUse()){
            o.setTryUse(true);
            o.setTryFromTime(new Date());
        }
        o.setTryToTime(tryToTime);

        if(!dao.update(o)){
            throw new BaseException("延长试用时间失败");
        }

        return open(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Shop openFormal(String id){
        Shop o = get(id);

        if(o.getTryUse()){
            o.setTryUse(false);
            o.setTryToTime(null);
            o.setTryFromTime(null);
            dao.update(o);
        }

        return open(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int closeTryExpired(Date timeout, int limit){
        List<Shop> data = dao.findTryExpired(timeout, limit);
        for(Shop shop: data){
            if(shop.getState() == Shop.State.OPEN){
                try{
                    close(shop.getId());
                }catch (Exception e){
                    logger.error("Close try expired {} shop fail, error is {}", shop.getId(), e.getMessage());
                }
            }
        }
        return data.size();
    }

    public long count(String name, String phone, String province, String city, String county, String address){
        return dao.count(name, phone, province, city, county, address);
    }

    public List<Shop> query(String name, String phone, String province, String city, String county, String address, int offset, int limit){
        return dao.find(name, phone, province, city, county, address, offset, limit);
    }
}
