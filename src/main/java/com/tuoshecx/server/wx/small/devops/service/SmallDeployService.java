package com.tuoshecx.server.wx.small.devops.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.shop.service.ShopWxService;
import com.tuoshecx.server.wx.small.devops.dao.SmallDeployDao;
import com.tuoshecx.server.wx.small.devops.domain.SmallDeploy;
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
        t.setId(IdGenerators.uuid());
        dao.insert(t);
        return get(t.getId());
    }

    private SmallDeploy get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            LOGGER.error("Get small deploy fail, error is {}", e.getMessage());
            throw new BaseException("小程序发布信息不存在");
        }
    }
}
