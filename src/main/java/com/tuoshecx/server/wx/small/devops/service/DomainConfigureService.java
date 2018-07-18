package com.tuoshecx.server.wx.small.devops.service;

import com.tuoshecx.server.wx.small.devops.dao.DomainConfigureDao;
import com.tuoshecx.server.wx.small.devops.domain.DomainConfigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 小程序服务域业务处理
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class DomainConfigureService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DomainConfigureService.class);
    private static final String ID = "10000";

    private final DomainConfigureDao dao;

    @Autowired
    public DomainConfigureService(DomainConfigureDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public DomainConfigure update(DomainConfigure t){
        t.setId(ID);
        dao.update(t);
        return dao.findOne(t.getId());
    }

    public Optional<DomainConfigure> getOptional(){
        try{
            return Optional.of(dao.findOne(ID));
        }catch (DataAccessException e){
            LOGGER.error("Get domain configure fail, error is {}", e.getMessage());
            return Optional.empty();
        }
    }
}
