package com.linku.server.base.service;

import com.linku.server.BaseException;
import com.linku.server.base.dao.AutoNumberDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 自增序号业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class AutoNumberService {
    private static final Logger logger = LoggerFactory.getLogger(AutoNumberService.class);
    private static final String MAIN_KEY = "main_insert_key";

    private final AutoNumberDao dao;

    @Autowired
    public AutoNumberService(AutoNumberDao dao){
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int maxNumber(String key){
        ifPresentKey(key);
        return max(key);
    }

    private Optional<Integer> getNumber(String key){
        try{
            return Optional.of(dao.findNumber(key));
        }catch (DataAccessException e){
            logger.error("Get auto number fail, error is {}", e.getMessage());
            return Optional.empty();
        }
    }

    private void ifPresentKey(String key){
        for(int i = 0; i < 10; i++){
            if(dao.hasKey(key)){
                return ;
            }

            Integer v = getNumber(MAIN_KEY).orElseThrow(() -> new IllegalStateException("None main key, please init main key ..."));
            if(dao.updateNumber(MAIN_KEY, v, v +1 )){
                dao.insert(key);
                return;
            }
        }
        logger.error("Create auto number fail");
        throw new IllegalStateException("Create " + key + " auto number fail");
    }

    private Integer max(String key){
        for(int i = 0 ; i< 5 ; i++){
            int n = getNumber(key).orElseThrow(() -> new IllegalStateException("None "+ key + " number ..."));
            int m = n + 1;
            if(dao.updateNumber(key, n , m)){
                return m;
            }
        }
        logger.error("Get auto number fail");
        throw new IllegalStateException("Get " + key + " auto number fail");
    }
}
