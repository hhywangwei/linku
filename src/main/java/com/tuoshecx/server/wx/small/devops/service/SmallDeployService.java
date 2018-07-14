package com.tuoshecx.server.wx.small.devops.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.wx.small.devops.dao.SmallDeployDao;
import com.tuoshecx.server.wx.small.devops.domain.SmallDeploy;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 小程序发布业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class SmallDeployService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmallDeployService.class);

    private final SmallDeployDao dao;

    @Autowired
    public SmallDeployService(SmallDeployDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public SmallDeploy save(SmallDeploy t){

        if(dao.hasTemplateId(t.getAppid(), t.getTemplateId())){
            throw new BaseException("发布版本已经存在");
        }

        t.setId(IdGenerators.uuid());
        dao.insert(t);
        return get(t.getId());
    }

    public SmallDeploy get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            LOGGER.error("Get small deploy fail, error is {}", e.getMessage());
            throw new BaseException("小程序发布信息不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean setDomain(String id){
        return dao.setDomain(id, true);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean commit(String id){
        SmallDeploy t = get(id);
        if(!isWait(t.getState())){
            LOGGER.warn("Deploy {} already commit, state {}", id, t.getState());
            return false;
        }
        return dao.updateState(id, "COMMIT", "");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean apply(String id, String auditId){
        SmallDeploy t = get(id);
        if(!isWait(t.getState())){
            LOGGER.warn("Deploy {} already audit, state {}", id, t.getState());
            return false;
        }
        dao.updateAuditId(id, auditId);
        return dao.updateState(id, "AUDIT", "");
    }

    private boolean isWait(String state){
        return StringUtils.equals(state, "WAIT");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean auditRefuse(String id, String remark){
        SmallDeploy t = get(id);
        if(!isAudit(t.getState())){
            LOGGER.warn("Deploy {} not apply audit state {}", id, t.getState());
            return false;
        }
        return dao.updateState(id, "REFUSE", remark);
    }

    private boolean isAudit(String state){
        return StringUtils.equals(state, "AUDIT");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean auditPass(String id){
        SmallDeploy t = get(id);
        if(!isAudit(t.getState())){
            LOGGER.warn("Deploy {} not apply audit state {}", id, t.getState());
            return false;
        }
        return dao.updateState(id, "PASS", "");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean release(String id){
        SmallDeploy t = get(id);
        if(!isPass(t.getState())){
            LOGGER.warn("Deploy {} not audit pass state {}", id, t.getState());
            return false;
        }
        return dao.updateState(id, "RELEASE", "");
    }

    private boolean isPass(String state){
        return StringUtils.equals(state, "PASS");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean reset(String id){
        return dao.updateState(id, "WAIT", "重新发布小程序");
    }
}
