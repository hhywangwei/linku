package com.linku.server.wx.pay.dao;

import com.linku.server.wx.pay.domain.WxRefundNotify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 * 微信退款通知数据操作
 *
 * @author WangWei
 */
@Repository
public class WxRefundNotifyDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WxRefundNotifyDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(WxRefundNotify t){
        jdbcTemplate.update("INSERT INTO re_wx_refund_notify (id, transaction_no, out_trade_no, refund_id, out_refund_no ," +
                "total_fee, settlement_total_fee, refund_fee, settlement_refund_fee, state, success_time, refund_recv_accout, " +
                "refund_account, refund_request_source, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                t.getId(), t.getTransactionNo(), t.getOutTradeNo(), t.getRefundId(), t.getOutRefundNo(), t.getTotalFee(),
                t.getSettlementTotalFee(), t.getRefundFee(), t.getSettlementRefundFee(), t.getState(), t.getSuccessTime(),
                t.getRefundRecvAccout(), t.getRefundAccount(), t.getRefundRequestSource(), now());
    }

    private Timestamp now(){
        return new Timestamp(System.currentTimeMillis());
    }
}
