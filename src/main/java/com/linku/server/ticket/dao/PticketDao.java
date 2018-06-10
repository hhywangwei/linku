package com.linku.server.ticket.dao;

import com.linku.server.common.utils.DaoUtils;
import com.linku.server.ticket.domain.Pticket;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 优惠券数据操作
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class PticketDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Pticket> mapper = (r, i) -> {
        Pticket t = new Pticket();

        t.setId(r.getString("id"));
        t.setCode(r.getString("code"));
        t.setShopId(r.getString("shop_id"));
        t.setUserId(r.getString("user_id"));
        t.setName(r.getString("name"));
        t.setHeadImg(r.getString("head_img"));
        t.setFromDate(r.getDate("from_date"));
        t.setToDate(r.getDate("to_date"));
        t.setState(Pticket.State.valueOf(r.getString("state")));
        t.setMoney(r.getInt("money"));
        t.setVersion(r.getInt("version"));
        t.setCreateTime(r.getDate("create_time"));
        t.setUseTime(r.getDate("use_time"));

        return t;
    };

    @Autowired
    public PticketDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Pticket t){
        final String sql = "INSERT INTO shop_pticket (id, code, shop_id, user_id, name, head_img, from_date, to_date, " +
                "state, money, version, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getCode(), t.getShopId(), t.getUserId(), t.getName(), t.getHeadImg(), t.getFromDate(),
                t.getToDate(), t.getState().name(), t.getMoney(), 0, DaoUtils.timestamp(new Date()));
    }

    public Pticket findOne(String id){
        final String sql = "SELECT * FROM shop_pticket WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Pticket findOneByCode(String code){
        final String sql = "SELECT * FROM shop_pticket WHERE code = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{code}, mapper);
    }

    public boolean updateState(String id, Pticket.State state, Integer version){
        final String sql = "UPDATE shop_pticket SET statue = ?, version = version + 1 WHERE id = ? AND version = ?";
        return jdbcTemplate.update(sql, state.name(), id, version) > 0;
    }

    public Long count(String userId, String shopId, Pticket.State state){
        final StringBuilder sql = new StringBuilder(60);
        sql.append("SELECT COUNT(id) FROM shop_pticket ");
        buildWhere(sql, userId, shopId, state);
        return jdbcTemplate.queryForObject(sql.toString(), params(userId, shopId, state), Long.class);
    }

    private void buildWhere(StringBuilder sql, String userId, String shopId, Pticket.State state){
        sql.append(" WHERE 1 = 1 ");
        if(StringUtils.isNotBlank(userId)){
            sql.append(" AND user_id = ? ");
        }
        if(StringUtils.isNotBlank(shopId)){
            sql.append(" AND shop_id = ? ");
        }
        if(state != null){
            sql.append(" AND state = ? ");
        }
    }

    private Object[] params(String userId, String shopId, Pticket.State state){
        List<Object> params = new ArrayList<>(3);
        if(StringUtils.isNotBlank(userId)){
            params.add(userId);
        }
        if(StringUtils.isNotBlank(shopId)){
            params.add(shopId);
        }
        if(state != null){
            params.add(shopId);
        }
        return params.toArray();
    }

    public List<Pticket> find(String userId, String shopId, Pticket.State state, int offset, int limit){
        final StringBuilder sql = new StringBuilder(80);
        sql.append("SELECT * FROM shop_pticket ");
        buildWhere(sql, userId, shopId, state);
        sql.append(" ORDER BY create_time LIMIT ? OFFSET ?");
        Object[] params = DaoUtils.appendOffsetLimit(params(userId, shopId, state), offset, limit);
        return jdbcTemplate.query(sql.toString(), params, mapper);
    }
}
