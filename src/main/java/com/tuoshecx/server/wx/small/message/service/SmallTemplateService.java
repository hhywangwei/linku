package com.tuoshecx.server.wx.small.message.service;

import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.wx.small.message.dao.SmallTemplateDao;
import com.tuoshecx.server.wx.small.message.domain.SmallTemplate;
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
 * 实现微信消息模板业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class SmallTemplateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmallTemplateService.class);

    private final SmallTemplateDao dao;

    @Autowired
    public SmallTemplateService(SmallTemplateDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(SmallTemplate t){
        t.setId(IdGenerators.uuid());
        dao.insert(t);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updateInfo(String id, String title, String content, String example){
        return dao.updateInfo(id, title, content, example);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        return dao.delete(id);
    }

    public Optional<SmallTemplate> get(String appid, String callKey){
        try{
            return Optional.of(dao.findOneByCallKey(appid, callKey));
        }catch (DataAccessException e){
            LOGGER.error("Get wx small template fail, error is {]", e.getMessage());
            return Optional.empty();
        }
    }

    public List<SmallTemplate> queryAll(String appid){
        return dao.findAll(appid);
    }

    public long count(String appid, String callKey, String title, String remark){
        return dao.count(appid, callKey, title, remark);
    }

    public List<SmallTemplate>  query(String appid, String callKey, String title, String remark, int offset, int limit){
        return dao.find(appid, callKey, remark, title, offset, limit);
    }
}
