package com.linku.server.game.wheel.dao;

import com.linku.server.common.utils.DaoUtils;
import com.linku.server.game.wheel.domain.BigWheel;
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
 * 大转盘数据操作
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class BigWheelDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<BigWheel> mapper = (r, i) -> {
        BigWheel t = new BigWheel();

        t.setId(r.getString("id"));
        t.setShopId(r.getString("shop_id"));
        t.setName(r.getString("name"));
        t.setHelp(r.getString("help"));
        t.setFromDate(r.getDate("from_date"));
        t.setToDate(r.getDate("to_date"));
        t.setLimit(r.getInt("day_limit"));
        t.setPticketDays(r.getInt("pticket_days"));
        t.setState(BigWheel.State.valueOf(r.getString("state")));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public BigWheelDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(BigWheel t){
        final String sql = "INSERT INTO game_big_wheel (id, shop_id, name, help, state, from_date, to_date, day_limit, " +
                "pticket_days, is_delete, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getShopId(), t.getName(), t.getHelp(), t.getState().name(),
                DaoUtils.date(t.getFromDate()), DaoUtils.date(t.getToDate()), t.getLimit(), t.getPticketDays(),
                false, DaoUtils.timestamp(new Date()));
    }

    public boolean update(BigWheel t){
        final String sql = "UPDATE game_big_wheel SET name = ?, help = ?, from_date = ?, to_date = ?, day_limit = ?, pticket_days = ? " +
                "WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, t.getName(), t.getHelp(), DaoUtils.date(t.getFromDate()),
                DaoUtils.date(t.getToDate()), t.getLimit(), t.getPticketDays(), t.getId()) > 0;
    }

    public boolean updateState(String id, BigWheel.State state){
        final String sql = "UPDATE game_big_wheel SET state = ? WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, state.name(), id) > 0;
    }

    public BigWheel findOne(String id){
        final String sql = "SELECT * FROM game_big_wheel WHERE id = ? AND is_delete = false";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public boolean delete(String id){
        final String sql = "UPDATE game_big_wheel SET is_delete = true WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public long count(String shopId, String name, BigWheel.State state){
        final StringBuilder sql= new StringBuilder(50);
        sql.append("SELECT COUNT(id) FROM game_big_wheel ");
        buildWhere(sql, shopId, name, state);

        return jdbcTemplate.queryForObject(sql.toString(), params(shopId, name, state), Long.class);
    }

    private void buildWhere(StringBuilder sql, String shopId, String name, BigWheel.State state){
        sql.append(" WHERE 1 = 1 ");
        if(StringUtils.isNotBlank(shopId)){
            sql.append(" AND shop_id = ? ");
        }
        if(StringUtils.isNotBlank(name)){
            sql.append(" AND name LIKE ? ");
        }
        if(state != null){
            sql.append(" AND state = ?");
        }
    }

    private Object[] params (String shopId, String name, BigWheel.State state){
        List<Object> params = new ArrayList<>(3);
        if(StringUtils.isNotBlank(shopId)){
            params.add(shopId);
        }
        if(StringUtils.isNotBlank(name)){
            params.add(DaoUtils.like(name));
        }
        if(state != null){
            params.add(state.name());
        }

        return params.toArray();
    }

    public List<BigWheel> find(String shopId, String name, BigWheel.State state, int offset, int limit){
        final StringBuilder sql = new StringBuilder(50);
        sql.append("SELECT * FROM game_big_wheel ");
        buildWhere(sql, shopId, name, state);
        sql.append(" ORDER BY create_time DESC LIMIT ? OFFSET ?");
        final Object[] params = DaoUtils.appendOffsetLimit(params(shopId, name, state), offset, limit);
        return  jdbcTemplate.query(sql.toString(), params, mapper);
    }

}
