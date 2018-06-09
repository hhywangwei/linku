package com.linku.server.order.dao;

import com.linku.server.order.domain.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * 订单明细数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class OrderItemDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<OrderItem> mapper = (r, i) -> {
        OrderItem t = new OrderItem();

        t.setId(r.getString("id"));
        t.setOrderId(r.getString("order_id"));
        t.setGoodsId(r.getString("goods_id"));
        t.setName(r.getString("name"));
        t.setIcon(r.getString("icon"));
        t.setPrice(r.getInt("price"));
        t.setCount(r.getInt("count"));
        t.setTotal(r.getInt("total"));

        return t;
    };

    @Autowired
    public OrderItemDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(OrderItem t){
        final String sql = "INSERT INTO order_item " +
                "(id, order_id, goods_id, name, icon, price, count, total) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getOrderId(), t.getGoodsId(), t.getName(), t.getIcon(),
                t.getPrice(), t.getCount(), t.getTotal());
    }

    public OrderItem findOne(String id){
        final String sql = "SELECT * FROM order_item WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public List<OrderItem> find(String orderId){
        final String sql = "SELECT * FROM order_item WHERE order_id = ?";
        return jdbcTemplate.query(sql, new Object[]{orderId}, mapper);
    }
}
