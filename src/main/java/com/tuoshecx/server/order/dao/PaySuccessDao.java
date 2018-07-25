package com.tuoshecx.server.order.dao;

import com.tuoshecx.server.common.utils.DaoUtils;
import com.tuoshecx.server.order.domain.PaySuccess;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * 营销活动订单支付处理日志数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class PaySuccessDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<PaySuccess> mapper = (r, i) -> {
      PaySuccess t = new PaySuccess();

      t.setId(r.getString("id"));
      t.setState(PaySuccess.State.valueOf(r.getString("state")));
      t.setMessage(r.getString("message"));
      t.setUpdateTime(r.getDate("update_time"));
      t.setCreateTime(r.getTimestamp("create_time"));

      return t;
    };

    public PaySuccessDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(PaySuccess t){
        final String sql = "INSERT INTO order_pay_success (id, state, update_time, create_time) VALUES (?, ?, ?, ?)";
        Date now = new Date();
        jdbcTemplate.update(sql, t.getId(), t.getState().name(), DaoUtils.timestamp(now), DaoUtils.timestamp(now));
    }

    public boolean updateState(String id, PaySuccess.State state, String message){
        final String sql = "UPDATE order_pay_success SET state = ?, message = ? WHERE id = ?";
        return jdbcTemplate.update(sql, state.name(), StringUtils.defaultString(message), id) > 0;
    }

    public PaySuccess findOne(String id){
        final String sql = "SELECT * FROM order_pay_success WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public List<PaySuccess> findWait(Date fromDate, Date toDate, int limit){
        final String sql = "SELECT * FROM order_pay_success WHERE state =? AND create_time BETWEEN(?, ?) ORDER BY create_time LIMIT ?";
        return jdbcTemplate.query(sql, new Object[]{PaySuccess.State.WAIT.name(), fromDate, toDate, limit}, mapper);
    }
}
