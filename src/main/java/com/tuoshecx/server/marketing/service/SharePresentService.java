package com.tuoshecx.server.marketing.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.common.utils.HtmlUtils;
import com.tuoshecx.server.marketing.dao.SharePresentDao;
import com.tuoshecx.server.marketing.domain.SharePresent;
import com.tuoshecx.server.common.id.IdGenerators;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 多人行业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class SharePresentService {
    private final SharePresentDao dao;

    @Autowired
    public SharePresentService(SharePresentDao dao) {
        this.dao = dao;
    }

    public SharePresent get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("多人行活动不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public SharePresent save(SharePresent t){
        t.setId(IdGenerators.uuid());
        dao.insert(t);
        setSummary(t);
        return get(t.getId());
    }

    private void setSummary(SharePresent t){
        if(StringUtils.isBlank(t.getSummary())){
            t.setSummary(HtmlUtils.text(t.getDetail(), 150));
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public SharePresent update(SharePresent t){
        SharePresent o = get(t.getId());
        if(!StringUtils.equals(t.getShopId(), o.getShopId())){
            throw new BaseException("多人行活动不存在");
        }
        setSummary(t);
        dao.update(t);

        return get(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public SharePresent open(String id, String shopId){
        SharePresent o = get(id);

        if(!StringUtils.equals(o.getShopId(), shopId)){
            throw new BaseException("多人行活动不存在");
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
    public SharePresent close(String id, String shopId){
        SharePresent o = get(id);

        if(!StringUtils.equals(o.getShopId(), shopId)){
            throw new BaseException("多人行活动不存在");
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
        SharePresent o = get(id);

        if(!StringUtils.equals(o.getShopId(), shopId)){
            throw new BaseException("多人行活动不存在");
        }

        if(o.getOpen()){
            throw new BaseException("上架活动不能删除");
        }
        return dao.delete(id);
    }

    public long count(String shopId, String name, Boolean isOpen){
        return dao.count(shopId, name, isOpen);
    }

    public List<SharePresent> query(String shopId, String name, Boolean isOpen, int offset, int limit){
        return dao.find(shopId, name, isOpen, offset, limit);
    }
}
