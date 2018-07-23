package com.tuoshecx.server.order.service;

import com.tuoshecx.server.order.dao.PaySuccessDao;
import com.tuoshecx.server.order.domain.PaySuccess;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PaySuccessService {
    private final PaySuccessDao dao;

    @Autowired
    public PaySuccessService(PaySuccessDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public PaySuccess save(String id, String marketingId, String marketingType){
        PaySuccess t = new PaySuccess();
        t.setId(id);
        t.setMarketingId(marketingId);
        t.setMarketingType(marketingType);
        t.setState(PaySuccess.State.WAIT);
        dao.insert(t);

        return dao.findOne(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean success(String id){
        return dao.updateState(id, PaySuccess.State.SUCCESS, StringUtils.EMPTY);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean fail(String id, String message){
        return dao.updateState(id, PaySuccess.State.FAIL, message);
    }
}