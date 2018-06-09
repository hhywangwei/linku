package com.linku.server.wx.pay.dao;

import com.linku.server.wx.pay.domain.WxRefund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 * 退款数据操作
 *
 * @author WangWei
 */
@Repository
public class WxRefundDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WxRefundDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(WxRefund t){
        jdbcTemplate.update("INSERT INTO wx_pay_refund (id, user_id, openid, shop_id, out_trade_no, " +
                        "total_fee, refund_fee, refund_desc, state, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                t.getId(), t.getUserId(), t.getOpenid(), t.getShopId(), t.getOutTradeNo(),
                t.getTotalFee(), t.getRefundFee(), t.getRefundDesc(), "wait", now());
    }

    public WxRefund findOne(String id){
        return jdbcTemplate.queryForObject("SELECT * FROM wx_pay_refund WHERE id = ?", new Object[]{id}, mapper);
    }

    public WxRefund findOneByOutTradeNo(String outTradeNo){
        return jdbcTemplate.queryForObject("SELECT * FROM wx_pay_refund WHERE out_trade_no = ?",
                new Object[]{outTradeNo}, mapper);
    }

    public boolean fail(String refundId){
        return jdbcTemplate.update("UPDATE wx_pay_refund SET state = ? WHERE refund_id = ? AND state = ?",
                "fail", refundId, "wait") > 0;
    }

    public boolean apply(String id, String refundId){
        return jdbcTemplate.update("UPDATE wx_pay_refund SET state = ?, refund_id = ? WHERE id = ? AND state = ?",
                "apply", refundId, id, "wait") > 0;
    }

    public boolean success(String refundId){
        return jdbcTemplate.update("UPDATE wx_pay_refund SET state = ?, success_time =?  WHERE refund_id = ? AND state = ?",
                "success", refundId, now(), "apply") > 0;
    }

    private Timestamp now(){
        return new Timestamp(System.currentTimeMillis());
    }

    private final RowMapper<WxRefund> mapper = (r, i) -> {
      WxRefund t = new WxRefund();

      t.setId(r.getString("id"));
      t.setUserId(r.getString("user_id"));
      t.setOpenid(r.getString("openid"));
      t.setShopId(r.getString("shop_id"));
      t.setOutTradeNo(r.getString("out_trade_no"));
      t.setRefundId(r.getString("refund_id"));
      t.setTotalFee(r.getInt("total_fee"));
      t.setRefundFee(r.getInt("refund_fee"));
      t.setRefundDesc(r.getString("refund_desc"));
      t.setState(r.getString("state"));
      t.setCreateTime(r.getTimestamp("create_time"));
      t.setSuccessTime(r.getTimestamp("success_time"));

      return t;
    };
}
