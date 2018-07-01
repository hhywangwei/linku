package com.tuoshecx.server.wx.pay.service;

import com.tuoshecx.server.wx.pay.dao.WxUnifiedOrderNotifyDao;
import com.tuoshecx.server.wx.pay.domain.WxUnifiedOrderNotify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 微信支付通知业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class WxUnifiedOrderNotifyService {
    private final WxUnifiedOrderNotifyDao dao;

    @Autowired
    public WxUnifiedOrderNotifyService(WxUnifiedOrderNotifyDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(WxUnifiedOrderNotify t){
        dao.insert(t);
    }
}
