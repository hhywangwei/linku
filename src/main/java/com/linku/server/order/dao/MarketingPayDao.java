package com.linku.server.order.dao;

import com.linku.server.common.utils.DaoUtils;
import com.linku.server.order.domain.MarketingPay;
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
public class MarketingPayDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<MarketingPay> mapper = (r, i) -> {
      MarketingPay t = new MarketingPay();

      t.setId(r.getString("id"));
      t.setMarketingId(r.getString("marketing_id"));
      t.setMarketingType(r.getString("marketing_type"));
      t.setState(MarketingPay.State.valueOf(r.getString("state")));
      t.setMessage(r.getString("message"));
      t.setUpdateTime(r.getDate("update_time"));
      t.setCreateTime(r.getTimestamp("create_time"));

      return t;
    };

    public MarketingPayDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(MarketingPay t){
        final String sql = "INSERT INTO order_marketing_pay (id, marketing_id, marketing_type, state, update_time, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getMarketingId(), t.getMarketingType(), t.getState().name(),
                DaoUtils.timestamp(new Date()), DaoUtils.timestamp(new Date()));
    }

    public boolean updateState(String id, MarketingPay.State state, String message){
        final String sql = "UPDATE order_marketing_pay SET state = ?, message = ? WHERE id = ?";
        return jdbcTemplate.update(sql, state.name(), StringUtils.defaultString(message), id) > 0;
    }

    public MarketingPay findOne(String id){
        final String sql = "SELECT * FROM order_marketing_pay WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public List<MarketingPay> findWait(Date fromDate, Date toDate, int limit){
        final String sql = "SELECT * FROM order_marketing_pay WHERE state =? AND create_time BETWEEN(?, ?) ORDER BY create_time LIMIT ?";
        return jdbcTemplate.query(sql, new Object[]{MarketingPay.State.WAIT.name(), fromDate, toDate, limit}, mapper);
    }
}
