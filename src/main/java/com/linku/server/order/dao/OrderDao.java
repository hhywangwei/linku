package com.linku.server.order.dao;

import com.linku.server.marketing.domain.Marketing;
import com.linku.server.common.utils.DaoUtils;
import com.linku.server.order.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

import static com.linku.server.order.domain.Order.State.*;

/**
 * 订单数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class OrderDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Order> mapper = (r, i) -> {
        Order t = new Order();

        t.setId(r.getString("id"));
        t.setShopId(r.getString("shop_id"));
        t.setUserId(r.getString("user_id"));
        t.setName(r.getString("name"));
        t.setPhone(r.getString("phone"));
        t.setCount(r.getInt("count"));
        t.setTotal(r.getInt("total"));
        t.setPayTotal(r.getInt("pay_total"));
        t.setDetail(r.getString("detail"));
        t.setMarketingId(r.getString("marketing_id"));
        t.setMarketingType(r.getString("marketing_type"));
        t.setState(Order.State.valueOf(r.getString("state")));
        t.setVersion(r.getInt("version"));
        t.setCreateTime(r.getTimestamp("create_time"));
        t.setPayTime(r.getTimestamp("pay_time"));
        t.setCancelTime(r.getTimestamp("cancel_time"));

        return t;
    };

    @Autowired
    public OrderDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Order t){
        final String sql = "INSERT INTO order_info (id, shop_id, user_id, name, phone, count, total, pay_total, detail, state, " +
                "marketing_id, marketing_type, version, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getShopId(), t.getUserId(), t.getName(), t.getPhone(),
                t.getCount(), t.getTotal(), t.getPayTotal(), t.getDetail(), t.getState().name(), t.getMarketingId(),
                t.getMarketingType(),  0, DaoUtils.timestamp(new Date()));
    }

    public Order findOne(String id){
        final String sql = "SELECT * FROM order_info WHERE id = ? AND is_delete = false";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public boolean pay(String id, int version){
        final String sql = "UPDATE order_info SET state = ?, pay_time = ?, version = version + 1 WHERE id = ? AND version = ? AND is_delete = false";
        return jdbcTemplate.update(sql, PAY.name(), DaoUtils.timestamp(new Date()), id, version) > 0;
    }

    public boolean updateActiveId(String id, String activeId){
        final String sql = "UPDATE order_info SET active_id = ? WHERE id = ?";
        return jdbcTemplate.update(sql, activeId, id) > 0;
    }

    public boolean roll(String id, int version){
        final String sql = "UPDATE order_info SET state = ?, pay_time = ?, version = version + 1 WHERE id = ? AND version = ? AND is_delete = false";
        return jdbcTemplate.update(sql, ROLL.name(), DaoUtils.timestamp(new Date()), id, version) > 0;
    }

    public boolean cancel(String id, int version){
        final String sql = "UPDATE order_info SET state = ?, cancel_time = ?, version = version + 1 WHERE id = ? AND version = ? AND is_delete = false";
        return jdbcTemplate.update(sql, CANCEL.name(), DaoUtils.timestamp(new Date()), id, version) > 0;
    }

    public boolean delete(String id, int version){
        final String sql = "UPDATE order_info SET is_delete = true, delete_time = ?, version = version + 1 WHERE id = ? AND version = ?";
        return jdbcTemplate.update(sql, DaoUtils.timestamp(new Date()), id, version) > 0;
    }

    public List<Order> findWaitExpired(int limit){
        final String sql = "SELECT * FROM order_info WHERE state = ? ORDER BY create_time ASC LIMIT ?";
        return jdbcTemplate.query(sql, new Object[]{Order.State.WAIT.name(), limit}, mapper);
    }

    public long countByShop(String shopId, Order.State state){
        final String sql = "SELECT COUNT(id) FROM order_info WHERE shop_id = ? AND is_delete = false AND state LIKE ? ";

        String stateLike = DaoUtils.blankLike(state == null? "": state.name());
        return jdbcTemplate.queryForObject(sql, new Object[]{shopId, stateLike}, Long.class);
    }

    public List<Order> findByShop(String shopId, Order.State state, int offset, int limit){
        final String sql = "SELECT * FROM order_info WHERE shop_id = ? AND is_delete = false AND state LIKE ? LIMIT ? OFFSET ?";
        String stateLike = DaoUtils.blankLike(state == null? "": state.name());
        return jdbcTemplate.query(sql, new Object[]{shopId, stateLike, limit, offset}, mapper);
    }

    public long countByUser(String userId, Order.State state){
        final String sql = "SELECT COUNT(id) FROM order_info WHERE user_id = ? AND is_delete = false AND state LIKE ?";
        String stateLike = DaoUtils.blankLike(state == null? "": state.name());
        return jdbcTemplate.queryForObject(sql, new Object[]{userId, stateLike}, Long.class);
    }

    public List<Order> findByUser(String userId, Order.State state, int offset, int limit){
        final String sql = "SELECT * FROM order_info WHERE user_id = ? AND is_delete = false AND state LIKE ? LIMIT ? OFFSET ?";
        String stateLike = DaoUtils.blankLike(state == null? "": state.name());
        return jdbcTemplate.query(sql, new Object[]{userId, stateLike, limit, offset}, mapper);
    }

    public boolean hasUserIdAndActiveId(String userId, String activeId){
        final String sql = "SELECT COUNT(id) FROM order_info WHERE user_id = ? AND active_id = ? AND state IN (?, ?)";
        return jdbcTemplate.queryForObject(sql,
                new Object[]{userId, activeId, Order.State.WAIT.name(), Order.State.PAY.name()}, Integer.class) > 0;
    }
}
