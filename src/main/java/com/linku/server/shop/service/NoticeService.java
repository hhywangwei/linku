package com.linku.server.shop.service;

import com.linku.server.BaseException;
import com.linku.server.common.id.IdGenerators;
import com.linku.server.shop.dao.NoticeDao;
import com.linku.server.shop.domain.Notice;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 店铺通知业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class NoticeService {
    private static final Logger logger = LoggerFactory.getLogger(NoticeService.class);

    private final NoticeDao dao;

    @Autowired
    public NoticeService(NoticeDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Notice save(Notice t){

        if(StringUtils.isNotBlank(t.getSource()) && dao.has(t.getSource())){
            logger.warn("Save notice has, source is {}", t.getSource());
            throw new BaseException("通知消息已经保存");
        }

        t.setId(IdGenerators.uuid());
        dao.insert(t);
        return dao.findOne(t.getId());
    }

    public Notice get(String id){
        try{
            return dao.findOne(id);
        }catch (BaseException e){
            throw new BaseException("通知消息不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean read(String shopId, String id){
        Notice t = get(id);
        if(!StringUtils.equals(shopId, t.getShopId())){
            throw new BaseException("通知消息不存在");
        }
        return dao.read(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String shopId, String id){
        Notice t = get(id);
        if(!StringUtils.equals(shopId, t.getShopId())){
            throw new BaseException("通知消息不存在");
        }
        return dao.delete(id);
    }

    public long count(String shopId, String title){
        return dao.count(shopId, title);
    }

    public List<Notice> query(String shopId, String title, int offset, int limit){
        return dao.find(shopId, title, offset, limit);
    }
}
