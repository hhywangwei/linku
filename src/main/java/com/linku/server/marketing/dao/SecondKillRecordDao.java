package com.linku.server.marketing.dao;

import com.linku.server.common.utils.DaoUtils;
import com.linku.server.common.utils.DateUtils;
import com.linku.server.marketing.domain.SecondKillRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class SecondKillRecordDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<SecondKillRecord> mapper = (r, i) -> {
        SecondKillRecord t = new SecondKillRecord();

        t.setId(r.getString("id"));
        t.setGoodsId(r.getString("goods_id"));
        t.setShopId(r.getString("shop_id"));
        t.setMarketingId(r.getString("marketing_id"));
        t.setOrderId(r.getString("order_id"));
        t.setName(r.getString("name"));
        t.setIcon(r.getString("icon"));
        t.setPrice(r.getInt("price"));
        t.setUserId(r.getString("user_id"));
        t.setNickname(r.getString("nickname"));
        t.setHeadImg(r.getString("head_img"));
        t.setState(SecondKillRecord.State.valueOf(r.getString("state")));
        t.setCreateTime(r.getTimestamp("create_time"));
        t.setUpdateTime(r.getTimestamp("update_time"));

        return t;
    };

    @Autowired
    public SecondKillRecordDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(SecondKillRecord t){
        final String sql = "INSERT INTO marketing_record_second_kill (id, goods_id, shop_id, marketing_id, order_id, name, " +
                "icon, price, user_id, nickname, head_img, state, create_time, update_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        final Date now = new Date();
        jdbcTemplate.update(sql, t.getId(), t.getGoodsId(), t.getShopId(), t.getMarketingId(), t.getOrderId(),
                t.getName(), t.getIcon(), t.getPrice(), t.getUserId(), t.getNickname(), t.getHeadImg(),
                SecondKillRecord.State.WAIT.name(), now, now);
    }

    public boolean updateOrderId(String id, String orderId){
        final String sql = "UPDATE marketing_record_second_kill SET order_id = ?, update_time = ? WHERE id = ?";
        return jdbcTemplate.update(sql, orderId, new Date(), id) > 0;
    }

    public boolean pay(String id){
        final String sql = "UPDATE marketing_record_second_kill SET state = ?, update_time = ? WHERE id = ? AND state = ?";
        return jdbcTemplate.update(sql, SecondKillRecord.State.PAY.name(), new Date(), id, SecondKillRecord.State.WAIT.name()) > 0;
    }

    public boolean cancel(String id){
        final String sql = "UPDATE marketing_record_second_kill SET state = ?, update_time = ? WHERE id = ? AND state = ?";
        return jdbcTemplate.update(sql, SecondKillRecord.State.CANCEL.name(), new Date(), id, SecondKillRecord.State.WAIT.name()) > 0;
    }

    public SecondKillRecord findOne(String id){
        final String sql = "SELECT * FROM marketing_record_second_kill WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public boolean has(String userId, String marketingId){
        final String sql = "SELECT COUNT(id) FROM marketing_record_second_kill WHERE user_id = ? AND marketing_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId, marketingId}, Integer.class) > 0;
    }

    public List<SecondKillRecord> findExpired(int minutes, int limit) {
        final Date date = DateUtils.plusMinutes(new Date(), minutes);
        final String sql = "SELECT * FROM marketing_record_second_kill " +
                "WHERE create_time < ? AND state = ? ORDER BY create_time ASC LIMIT ?";
        return jdbcTemplate.query(sql, new Object[]{date, SecondKillRecord.State.WAIT.name(), limit}, mapper);
    }

    public long count(String shopId, String marketingId, String orderId, String name){
        StringBuilder sql = new StringBuilder(100);
        sql.append("SELECT COUNT(id) FROM marketing_record_second_kill ");
        buildWhere(sql, shopId, marketingId, orderId, name);
        Object[] params = params(shopId, marketingId, orderId, name);
        return jdbcTemplate.queryForObject(sql.toString(), params, Long.class);
    }

    private void buildWhere(StringBuilder sql, String shopId, String marketingId, String orderId, String name){
        sql.append(" WHERE 1 = 1");
        if(StringUtils.isNotBlank(shopId)){
            sql.append(" AND shop_id = ? ");
        }
        if(StringUtils.isNotBlank(marketingId)){
            sql.append(" AND marketing_id = ? ");
        }
        if(StringUtils.isNotBlank(orderId)){
            sql.append(" AND order_id = ? ");
        }
        if(StringUtils.isNotBlank(name)){
            sql.append(" AND name LIKE ?");
        }
    }

    private Object[] params(String shopId, String marketingId, String orderId, String name){
        List<String> params = new ArrayList<>(4);
        if(StringUtils.isNotBlank(shopId)){
            params.add(shopId);
        }
        if(StringUtils.isNotBlank(marketingId)){
            params.add(marketingId);
        }
        if(StringUtils.isNotBlank(orderId)){
            params.add(orderId);
        }
        if(StringUtils.isNotBlank(name)){
            params.add(DaoUtils.like(name));
        }

        return params.toArray(new Object[0]);
    }

    public List<SecondKillRecord> find(String shopId, String marketingId, String orderId, String name, int offset, int limit){
        StringBuilder sql = new StringBuilder(100);
        sql.append("SELECT * FROM marketing_record_second_kill ");
        buildWhere(sql, shopId, marketingId, orderId, name);
        sql.append(" ORDER BY create_time DESC LIMIT ? OFFSET ?");
        Object[] params = DaoUtils.appendOffsetLimit(params(shopId, marketingId, orderId, name), offset, limit);
        return jdbcTemplate.query(sql.toString(), params, mapper);
    }
}
