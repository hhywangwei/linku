package com.tuoshecx.server.wx.small.devops.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.wx.small.devops.dao.SmallAuditConfigureDao;
import com.tuoshecx.server.wx.small.devops.domain.SmallAuditConfigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 微信小程序审核配置业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class SmallAuditConfigureService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmallAuditConfigureService.class);

    private SmallAuditConfigureDao dao;

    @Autowired
    public SmallAuditConfigureService(SmallAuditConfigureDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public SmallAuditConfigure save(SmallAuditConfigure t){
        t.setId(IdGenerators.uuid());
        dao.insert(t);

        return dao.findOne(t.getId());
    }

    @Transactional(propagation =  Propagation.REQUIRED)
    public SmallAuditConfigure update(SmallAuditConfigure t){
        dao.update(t);
        return get(t.getId());
    }

    private SmallAuditConfigure get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            LOGGER.error("Get small audit configure fail, error is {}", e.getMessage());
            throw new BaseException("发布审核信息不存在");
        }
    }

    public boolean delete(String id){
        return dao.delete(id);
    }

    public List<SmallAuditConfigure> queryAppid(String appid){
        String[] appids = new String[]{appid, "*"};
        return dao.findByAppids(appids);
    }

    public long count(String shopId, String appid, String title, String tag){
        return dao.count(shopId, appid, title, tag);
    }

    public List<SmallAuditConfigure> query(String shopId, String appid, String title, String tag, int offset, int limit){
        return dao.find(shopId, appid, title, tag, offset, limit);
    }
}
