package com.tuoshecx.server.wx.small.devops.service;

import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.wx.small.devops.dao.SmallDeployLogDao;
import com.tuoshecx.server.wx.small.devops.domain.SmallDeployLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 小程序发布日志业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class SmallDeployLogService {
    private final SmallDeployLogDao dao;

    @Autowired
    public SmallDeployLogService(SmallDeployLogDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(String deployId, String action, String message){
        SmallDeployLog t = new SmallDeployLog();

        t.setId(IdGenerators.uuid());
        t.setDeployId(deployId);
        t.setAction(action);
        t.setMessage(message);

        dao.insert(t);
    }

    public List<SmallDeployLog> query(String deployId){
        return dao.findByDeployId(deployId);
    }
}
