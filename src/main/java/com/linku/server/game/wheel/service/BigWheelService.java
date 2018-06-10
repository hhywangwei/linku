package com.linku.server.game.wheel.service;

import com.linku.server.BaseException;
import com.linku.server.common.id.IdGenerators;
import com.linku.server.game.wheel.dao.BigWheelDao;
import com.linku.server.game.wheel.dao.BigWheelItemDao;
import com.linku.server.game.wheel.domain.BigWheel;
import com.linku.server.game.wheel.domain.BigWheelItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 大转轮游戏服务
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class BigWheelService {
    private final BigWheelDao dao;
    private final BigWheelItemDao itemDao;

    @Autowired
    public BigWheelService(BigWheelDao dao, BigWheelItemDao itemDao) {
        this.dao = dao;
        this.itemDao = itemDao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public BigWheel save(BigWheel t, List<BigWheelItem> items){
        t.setId(IdGenerators.uuid());
        dao.insert(t);

        for(int i = 0; i < items.size(); i++){
            BigWheelItem item = items.get(i);
            item.setId(IdGenerators.uuid());
            item.setBigWheelId(t.getId());
            item.setIndex(i);
            itemDao.insert(item);
        }

        return dao.findOne(t.getId());
    }

    public BigWheel get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("大转盘游戏不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public BigWheel update(BigWheel t, List<BigWheelItem> items){
        BigWheel o = get(t.getId());

        if(!StringUtils.equals(t.getShopId(), o.getShopId())){
            throw new BaseException("大转盘游戏不存在");
        }

        if(t.getState() == BigWheel.State.OPEN){
            throw new BaseException("大转盘游戏已经开始不能修改");
        }

        dao.update(t);
        itemDao.deleteOfBigWheel(t.getId());
        for(int i = 0; i < items.size(); i++){
            BigWheelItem item = items.get(i);
            item.setId(IdGenerators.uuid());
            item.setIndex(i);
            item.setBigWheelId(t.getId());
            itemDao.insert(item);
        }

        return get(t.getId());
    }

    public List<BigWheelItem> getItems(String id){
        return itemDao.find(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean delete(String id, String shopId){
        BigWheel t = get(id);

        if(!StringUtils.equals(t.getShopId(), shopId)){
            throw new BaseException("大转盘游戏不存在");
        }

        if(t.getState() == BigWheel.State.OPEN){
            throw new BaseException("大转盘游戏已经开始不能删除");
        }

        if(!StringUtils.equals(t.getShopId(), shopId)){
            throw new BaseException("大转盘游戏不存在");
        }

        return dao.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public BigWheel open(String id, String shopId){
        BigWheel t = get(id);

        if(!StringUtils.equals(t.getShopId(), shopId)){
            throw new BaseException("大转盘游戏不存在");
        }

        if(t.getState() == BigWheel.State.OPEN){
            throw new BaseException("大转盘游戏已经开始");
        }

        if(!dao.updateState(id, BigWheel.State.OPEN)){
            throw new BaseException("大转盘开始失败");
        }

        return get(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public BigWheel close(String id, String shopId){
        BigWheel t = get(id);

        if(!StringUtils.equals(t.getShopId(), shopId)){
            throw new BaseException("大转盘游戏不存在");
        }

        if(t.getState() == BigWheel.State.CLOSE){
            throw new BaseException("大转盘游戏已经关闭");
        }

        if(!dao.updateState(id, BigWheel.State.CLOSE)){
            throw new BaseException("大转盘开始失败");
        }

        return get(id);
    }

    public long count(String shopId, String name, BigWheel.State state){
        return dao.count(shopId, name, state);
    }

    public List<BigWheel> query(String shopId, String name, BigWheel.State state, int offset, int limit){
        return dao.find(shopId, name, state, offset, limit);
    }

}
