package com.tuoshecx.server.wx.small.devops.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.wx.small.devops.dao.SmallExtConfigureDao;
import com.tuoshecx.server.wx.small.devops.domain.SmallExtConfigure;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 小程序配置业务服务
 *
 * @author WangWei
 */
@Service
@Transactional(readOnly = true)
public class SmallExtConfigureService {
    private final SmallExtConfigureDao dao;

    @Autowired
    public SmallExtConfigureService(SmallExtConfigureDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public SmallExtConfigure save(SmallExtConfigure t){
        t.setId(IdGenerators.uuid());
        if(dao.hasTemplateId(t.getTemplateId())){
            throw new BaseException("版本已经存在");
        }
        dao.insert(t);

        return dao.findOne(t.getId());
    }

    public SmallExtConfigure get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("配置不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public SmallExtConfigure update(SmallExtConfigure t){
        SmallExtConfigure o = get(t.getId());
        if(o.getTemplateId().intValue() != t.getTemplateId().intValue()
                && dao.hasTemplateId(t.getTemplateId())){
            throw new BaseException("版本号已经存在");
        }
        dao.update(t);

        return get(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        return dao.delete(id);
    }

    public Optional<SmallExtConfigure> getTemplateId(Integer templateId){
        List<SmallExtConfigure> list =  dao.findByTemplateId(templateId);
        return list.isEmpty()? Optional.empty() : Optional.of(list.get(0));
    }

    public long count(Integer templateId){
        return dao.count(templateId);
    }

    public List<SmallExtConfigure> query(Integer templateId, int offset, int limit){
        return dao.find(templateId, offset, limit);
    }
}
