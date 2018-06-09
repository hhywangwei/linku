package com.linku.server.marketing.service;

import com.linku.server.BaseException;
import com.linku.server.common.utils.HtmlUtils;
import com.linku.server.marketing.dao.SecondKillDao;
import com.linku.server.marketing.domain.SecondKill;
import com.linku.server.common.id.IdGenerators;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SecondKillService {
    private final SecondKillDao dao;

    @Autowired
    public SecondKillService(SecondKillDao dao) {
        this.dao = dao;
    }

    public SecondKill get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("秒杀活动不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public SecondKill save(SecondKill t){
        t.setId(IdGenerators.uuid());
        dao.insert(t);
        setSummary(t);
        return get(t.getId());
    }

    private void setSummary(SecondKill t){
        if(StringUtils.isBlank(t.getSummary())){
            t.setSummary(HtmlUtils.text(t.getDetail(), 150));
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public SecondKill update(SecondKill t){
        SecondKill o = get(t.getId());

        if(!StringUtils.equals(t.getShopId(), o.getShopId())){
            throw new BaseException("秒杀活动不存在");
        }

        setSummary(t);
        if(!dao.update(t)){
            throw new BaseException("修改秒杀活动失败");
        }
        return get(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public SecondKill incStock(String id, String shopId, int number){
        for(int i = 0; i < 5; i++){
            SecondKill t = get(id);

            if(!StringUtils.equals(t.getShopId(), shopId)){
                throw new BaseException("秒杀活动不存在");
            }

            if(dao.incStock(t.getId(), number, t.getVersion())){
                return get(id);
            }
        }

        throw new BaseException("增加库存失败");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean incRemain(String id, int number, int version){
        return dao.incRemain(id, number, version);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public SecondKill open(String id, String shopId){
        SecondKill o = get(id);

        if(!StringUtils.equals(o.getShopId(), shopId)){
            throw new BaseException("团购活动不存在");
        }

        if(o.getOpen()){
            throw new BaseException("活动已经上架");
        }
        if(!dao.updateOpen(id, true)){
            throw new BaseException("活动上架失败");
        }
        return get(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public SecondKill close(String id, String shopId){
        SecondKill o = get(id);

        if(!StringUtils.equals(o.getShopId(), shopId)){
            throw new BaseException("团购活动不存在");
        }

        if(!o.getOpen()){
            throw new BaseException("活动已经下架");
        }
        if(!dao.updateOpen(id, false)){
            throw new BaseException("活动下架失败");
        }
        return get(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id, String shopId){
        SecondKill o = get(id);

        if(!StringUtils.equals(o.getShopId(), shopId)){
            throw new BaseException("团购活动不存在");
        }

        if(o.getOpen()){
            throw new BaseException("上架活动不能删除");
        }
        return dao.delete(id);
    }

    public long count(String shopId, String name, Boolean isOpen){
        return dao.count(shopId, name, isOpen);
    }

    public List<SecondKill> query(String shopId, String name, Boolean isOpen, int offset, int limit){
        return dao.find(shopId, name, isOpen, offset, limit);
    }
}
