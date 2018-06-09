package com.linku.server.base.service;

import com.linku.server.BaseException;
import com.linku.server.common.id.IdGenerators;
import com.linku.server.base.dao.SysDao;
import com.linku.server.base.domain.Sys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统管理员数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class SysService {
    private static final Logger logger = LoggerFactory.getLogger(SysService.class);

    private final SysDao dao;

    @Autowired
    public SysService(SysDao dao){
        this.dao = dao;
    }

    public Sys get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            logger.error("Get Sys fail, error is {}", e.getMessage());
            throw new BaseException("系统操作员不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Sys save(Sys t){
        if(dao.hasUsername(t.getUsername())){
            throw new BaseException("用户名已经存在");
        }
        t.setId(IdGenerators.uuid());
        t.setPassword(encodePassword(t.getPassword()));
        dao.insert(t);
        return get(t.getId());
    }

    private String encodePassword(String password){
        return password;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Sys update(Sys t){
        Sys o = get(t.getId());
        if(!dao.update(t)){
            throw new BaseException("修改系统操作员失败");
        }
        return get(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        Sys o = get(id);
        if(o.getManager()){
            throw new BaseException("超级管理员不能删除");
        }
        return dao.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Sys active(String id){
        Sys t = get(id);
        if(t.getEnable()){
            throw new BaseException("系统操作员已经激活");
        }
        if(!dao.updateEnable(id, true)){
            throw new BaseException("激活失败");
        }
        return get(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Sys inactive(String id){
        Sys t = get(id);
        if(!t.getEnable()){
            throw new BaseException("系统操作员已经禁用");
        }
        if(t.getManager()){
            throw new BaseException("超级管理员不能被禁用");
        }
        if(!dao.updateEnable(id, false)){
            throw new BaseException("禁用失败");
        }
        return get(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updatePassword(String id, String password, String newPassword){
        Sys t = get(id);
        if(!isPasswordEquals(t.getPassword(), password)){
            throw new BaseException("密码错误");
        }
        return dao.updatePassword(id, newPassword);
    }

    private boolean isPasswordEquals(String realPassword, String password){
        return realPassword.equals(encodePassword(password));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean resetPassword(String id, String newPassword){
        Sys t = get(id);
        return dao.updatePassword(id, newPassword);
    }

    public Sys getValidate(String username, String password){
        try{
            Sys o = dao.findOneByUsername(username);
            if(!o.getEnable()){
                throw new BaseException("系统操作员已经禁用");
            }
            if(!isPasswordEquals(o.getPassword(), password)){
                throw new BaseException("密码错误");
            }
            return o;
        }catch (DataAccessException e){
            throw new BaseException("用户或密码错误");
        }
    }

    public long count(String username, String name, String phone){
        return dao.count(username, name, phone);
    }

    public List<Sys> query(String username, String name, String phone, int offset, int limit){
        return dao.find(username, name, phone, offset, limit);
    }
}
