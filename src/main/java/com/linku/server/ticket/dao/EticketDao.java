package com.linku.server.ticket.dao;

import com.linku.server.common.utils.DaoUtils;
import com.linku.server.ticket.domain.Eticket;
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
 * 电子券数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class EticketDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Eticket> mapper = (r, i) -> {
        Eticket t = new Eticket();

        t.setId(r.getString("id"));
        t.setCode(r.getString("code"));
        t.setShopId(r.getString("shop_id"));
        t.setUserId(r.getString("user_id"));
        t.setName(r.getString("name"));
        t.setHeadImg(r.getString("head_img"));
        t.setOrderId(r.getString("order_id"));
        t.setGoodsId(r.getString("goods_id"));
        t.setGoodsName(r.getString("goods_name"));
        t.setGoodsIcon(r.getString("goods_icon"));
        t.setState(Eticket.State.valueOf(r.getString("state")));
        t.setVersion(r.getInt("version"));
        t.setFromDate(r.getDate("from_date"));
        t.setToDate(r.getDate("to_date"));
        t.setCreateTime(r.getTimestamp("create_time"));
        t.setUseTime(r.getTimestamp("use_time"));

        return t;
    };

    @Autowired
    public EticketDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Eticket t){
        final String sql = "INSERT INTO shop_eticket (id, code, shop_id, user_id, name, head_img, order_id, goods_id, goods_name, " +
                "goods_icon, state, version, from_date, to_date, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, t.getId(), t.getCode(), t.getShopId(), t.getUserId(), t.getName(), t.getHeadImg(),
                t.getOrderId(), t.getGoodsId(), t.getGoodsName(), t.getGoodsIcon(), Eticket.State.WAIT.name(), 0, t.getFromDate(),
                t.getToDate(), DaoUtils.timestamp(new Date()));
    }

    public Eticket findOne(String id){
        final String sql = "SELECT * FROM shop_eticket WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Eticket findOneByCode(String code){
        final String sql = "SELECT * FROM shop_eticket WHERE code = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{code}, mapper);
    }

    public boolean updateState(String id, Eticket.State state, int version){
        final String sql = "UPDATE shop_eticket SET state = ?, version = version + 1, use_time = ? WHERE id =? AND version = ?";
        return jdbcTemplate.update(sql, state.name(), DaoUtils.timestamp(new Date()), id, version) > 0;
    }

    public long count(String userId, String shopId, Eticket.State state, String name, String goodsName){
        StringBuilder sql = new StringBuilder(80);
        sql.append("SELECT COUNT(id) FROM shop_eticket ");
        buildWhere(sql, userId, shopId, state, name, goodsName);
        return jdbcTemplate.queryForObject(sql.toString(), params(userId, shopId, state, name, goodsName), Long.class);
    }

    private void buildWhere(StringBuilder sql, String userId, String shopId, Eticket.State state, String name, String goodsName){
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
        if(StringUtils.isNotBlank(name)){
            sql.append(" AND name LIKE ? ");
        }
        if(StringUtils.isNotBlank(goodsName)){
            sql.append(" AND goods_name LIKE ?");
        }
    }

    private Object[] params(String userId, String shopId, Eticket.State state, String name, String goodsName){
        List<String> params = new ArrayList<>(5);
        if(StringUtils.isNotBlank(userId)){
            params.add(userId);
        }
        if(StringUtils.isNotBlank(shopId)){
            params.add(shopId);
        }
        if(state != null){
            params.add(state.name());
        }
        if(StringUtils.isNotBlank(name)){
            params.add(DaoUtils.like(name));
        }
        if(StringUtils.isNotBlank(goodsName)){
            params.add(DaoUtils.like(goodsName));
        }

        return params.toArray(new Object[0]);
    }

    public List<Eticket> find(String userId, String shopId, Eticket.State state, String name, String goodsName, int offset, int limit){
        StringBuilder sql = new StringBuilder(80);
        sql.append("SELECT * FROM shop_eticket ");
        buildWhere(sql, userId, shopId, state, name, goodsName);
        sql.append(" ORDER BY create_time ASC LIMIT ? OFFSET ? ");
        Object[] params = DaoUtils.appendOffsetLimit(params(userId, shopId, state, name, goodsName), offset, limit);
        return jdbcTemplate.query(sql.toString(), params, mapper);
    }
}
