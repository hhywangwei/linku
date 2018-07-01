package com.tuoshecx.server.goods.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.goods.dao.CatalogDao;
import com.tuoshecx.server.goods.domain.Catalog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品分类业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class CatalogService {
    private final CatalogDao dao;

    @Autowired
    public CatalogService(CatalogDao dao){
        this.dao = dao;
    }

    public Catalog get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("商品分类不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Catalog save(Catalog t){
        t.setId(IdGenerators.uuid());
        dao.insert(t);
        return get(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Catalog update(Catalog t){
        Catalog o = get(t.getId());
        if(!StringUtils.equals(o.getShopId(), t.getShopId())){
            throw new BaseException("无权限操作");
        }
        dao.update(t);
        return get(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id, String shopId){
        Catalog o = get(id);
        if(!StringUtils.equals(o.getShopId(), shopId)){
            throw new BaseException("无权限操作");
        }

        return dao.delete(id);
    }

    public long count(String shopId, String name){
        return dao.count(shopId, name);
    }

    public List<Catalog> query(String shopId, String name, int offset, int limit){
        return dao.find(shopId, name, offset, limit);
    }
}
