package com.tuoshecx.server.wx.pay.dao;

import com.tuoshecx.server.wx.pay.domain.WxUnifiedOrderNotify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 * 微信通知日志数据操作
 *
 * @author WangWei
 */
@Repository
public class WxUnifiedOrderNotifyDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(WxUnifiedOrderNotify t){
        jdbcTemplate.update("INSERT INTO wx_pay_notify (id, sign, sign_type, openid, is_subscribe, " +
                "trade_type, bank_type, total_fee, settlement_total_fee, fee_type, cash_fee, " +
                "cash_fee_type, transaction_id, out_trade_no, attach, time_end, create_time) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                t.getId(), t.getSign(), t.getSignType(), t.getOpenid(), t.getSubscribe(), t.getTradeType(),
                t.getBankType(), t.getTotalFee(), t.getSettlementTotalFee(), t.getFeeType(), t.getCashFee(),
                t.getCashFeeType(), t.getTransactionId(), t.getOutTradeNo(), t.getAttach(), t.getTimeEnd(),
                new Timestamp(System.currentTimeMillis()));
    }
}
