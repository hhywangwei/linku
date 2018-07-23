package com.tuoshecx.server.marketing.service;

import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.marketing.dao.GroupRecordFinishDao;
import com.tuoshecx.server.marketing.domain.GroupRecordFinish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *  组团类营销活动记录结束业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class GroupRecordFinishService {
    private final GroupRecordFinishDao dao;

    @Autowired
    public GroupRecordFinishService(GroupRecordFinishDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(String recordId, String itemId, String action){
        GroupRecordFinish t = new GroupRecordFinish();
        t.setId(IdGenerators.uuid());
        t.setRecordId(recordId);
        t.setItemId(itemId);
        t.setAction(action);

        dao.insert(t);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean success(String recordId, String itemId){
        return dao.success(recordId, itemId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean fail(String recordId, String itemId, String message){
        return dao.fail(recordId, itemId, message);
    }
}
