package com.linku.server.order.service;

import com.linku.server.order.dao.MarketingPayDao;
import com.linku.server.order.domain.MarketingPay;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MarketingPayService {
    private final MarketingPayDao dao;

    @Autowired
    public MarketingPayService(MarketingPayDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public MarketingPay save(String id, String marketingId, String marketingType){
        MarketingPay t = new MarketingPay();
        t.setId(id);
        t.setMarketingId(marketingId);
        t.setMarketingType(marketingType);
        t.setState(MarketingPay.State.WAIT);
        dao.insert(t);

        return dao.findOne(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean success(String id){
        return dao.updateState(id, MarketingPay.State.SUCCESS, StringUtils.EMPTY);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean fail(String id, String message){
        return dao.updateState(id, MarketingPay.State.FAIL, message);
    }
}
