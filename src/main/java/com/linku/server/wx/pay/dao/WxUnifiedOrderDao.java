package com.linku.server.wx.pay.dao;

import com.linku.server.common.utils.DaoUtils;
import com.linku.server.wx.pay.domain.WxUnifiedOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 微信统一下单数据处理
 *
 * @author WangWei
 */
@Repository
public class WxUnifiedOrderDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WxUnifiedOrderDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<WxUnifiedOrder> mapper = (r, i) -> {
        WxUnifiedOrder t = new WxUnifiedOrder();

        t.setId(r.getString("id"));
        t.setShopId(r.getString("shop_Id"));
        t.setUserId(r.getString("user_id"));
        t.setOpenid(r.getString("openid"));
        t.setOutTradeNo(r.getString("out_trade_no"));
        t.setBody(r.getString("detail"));
        t.setAttach(r.getString("attach"));
        t.setFeeType(r.getString("fee_type"));
        t.setTotalFee(r.getInt("total_fee"));
        t.setRealTotalFee(r.getInt("real_total_fee"));
        t.setTradeType(r.getString("trade_type"));
        t.setTransactionNo(r.getString("transaction_no"));
        t.setState(r.getString("state"));
        t.setRefundFee(r.getInt("refund_fee"));
        t.setCreateTime(r.getTimestamp("create_time"));
        t.setRefundTime(r.getTimestamp("refund_time"));
        t.setPayTime(r.getTimestamp("pay_time"));

        return t;
    };

    public void insert(WxUnifiedOrder t){
        final String sql = "INSERT INTO wx_pay_order (id, shop_id, user_id, openid, out_trade_no, detail, attach, fee_type, " +
                "total_fee, trade_type, state, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getShopId(), t.getUserId(), t.getOpenid(), t.getOutTradeNo(), t.getBody(),
                t.getAttach(), t.getFeeType(), t.getTotalFee(), t.getTradeType(), "wait", new Timestamp(System.currentTimeMillis()));
    }

    public boolean hasOutTradeNo(String outTradeNo){
        final String sql = "SELECT COUNT(id) FROM wx_pay_order WHERE out_trade_no = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{outTradeNo}, Integer.class) > 0;
    }

    public boolean pay(String id, String transactionNo, int realTotalFee){
        final String sql = "UPDATE wx_pay_order SET transaction_no = ?, state = ?, real_total_fee =?, pay_time = ? WHERE id = ? AND state = ?";
        return jdbcTemplate.update(sql, transactionNo, "pay", realTotalFee, DaoUtils.timestamp(new Date()), id, "wait") > 0;
    }

    public boolean refund(String id, int refundFee){
        final String sql = "UPDATE wx_pay_order SET refund_fee =?, state = ?, refund_time = ? WHERE id = ? AND state = ?";
        return jdbcTemplate.update(sql, refundFee, "refund", DaoUtils.timestamp(new Date()), id, "pay") > 0;
    }

    public WxUnifiedOrder findOne(String id){
        return jdbcTemplate.queryForObject("SELECT * FROM wx_pay_order WHERE id = ?",
                new Object[]{id}, mapper);
    }

    public WxUnifiedOrder findOneByOutTradeNo(String outTradeNo){
        return jdbcTemplate.queryForObject("SELECT * FROM wx_pay_order WHERE out_trade_no = ?",
                new Object[]{outTradeNo}, mapper);
    }

}
