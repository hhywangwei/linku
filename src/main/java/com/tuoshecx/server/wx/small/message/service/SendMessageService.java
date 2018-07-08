package com.tuoshecx.server.wx.small.message.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.wx.small.message.dao.SendMessageDao;
import com.tuoshecx.server.wx.small.message.domain.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class SendMessageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendMessageService.class);

    private final SendMessageDao dao;

    @Autowired
    public SendMessageService(SendMessageDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String save(SendMessage t){

        t.setId(IdGenerators.uuid());
        t.setState("WAIT");
        dao.insert(t);

        return t.getId();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean success(String id){
        return dao.updateStatus(id, "SUCCESS", "");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean fail(String id, String error){
        return dao.updateStatus(id, "FAIL", error);
    }

    public SendMessage get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            LOGGER.error("Get template message fail, error is {}", e.getMessage());
            throw new BaseException("微信消息不存在");
        }
    }

    public Long count(String appid, String title, String callKey, String openid, String status, Date fromTime, Date toTime){
        return dao.count(appid, title, callKey, openid, status, fromTime, toTime);
    }

    public List<SendMessage> query(String appid, String title, String callKey, String openid, String status, Date fromTime, Date toTime, int offset, int limit){
        return dao.find(appid, title, callKey, openid, status, fromTime, toTime, offset, limit);
    }
}
