package com.tuoshecx.server.shop.dao;

import com.tuoshecx.server.common.utils.DaoUtils;
import com.tuoshecx.server.shop.domain.ShopWxPay;
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
public class ShopWxPayDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ShopWxPay> mapper = (r, i) -> {
        ShopWxPay t = new ShopWxPay();

        t.setId(r.getString("id"));
        t.setShopId(r.getString("shop_id"));
        t.setAppid(r.getString("appid"));
        t.setMchId(r.getString("mch_id"));
        t.setKey(r.getString("pay_key"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public ShopWxPayDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(ShopWxPay t){
        final String sql = "INSERT INTO shop_wx_pay (id, shop_id, mch_id, appid, pay_key, create_time) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getShopId(), t.getMchId(), t.getAppid(), t.getKey(), DaoUtils.timestamp(new Date()));
    }

    public boolean update(ShopWxPay t){
        final String sql = "UPDATE shop_wx_pay SET mch_id = ?, appid = ?, pay_key = ? WHERE id = ?";
        return jdbcTemplate.update(sql, t.getMchId(), t.getAppid(), t.getKey(), t.getId())> 0;
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM shop_wx_pay WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public ShopWxPay findOne(String id){
        final String sql = "SELECT * FROM shop_wx_pay WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public ShopWxPay findOneByShopId(String shopId){
        final String sql = "SELECT * FROM shop_wx_pay WHERE shop_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{shopId}, mapper);
    }

    public ShopWxPay findOneByAppid(String appid){
        final String sql = "SELECT * FROM shop_wx_pay WHERE appid = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{appid}, mapper);
    }

    public Long count(String shopId, String appid){
        StringBuilder sql = new StringBuilder(128);
        sql.append("SELECT COUNT(id) FROM shop_wx_pay ");
        buildWhere(sql, shopId, appid);
        Object[] params = params(shopId, appid);

        return jdbcTemplate.queryForObject(sql.toString(), params, Long.class);
    }

    private void buildWhere(StringBuilder sql, String shopId, String appid){
        sql.append(" WHERE 1 =1 ");
        if(StringUtils.isNotBlank(shopId)){
            sql.append(" AND shop_id = ? ");
        }
        if(StringUtils.isNotBlank(appid)){
            sql.append(" AND appid = ? ");
        }
    }

    private Object[] params(String shopId, String appid){
        List<String> params = new ArrayList<>(2);
        if(StringUtils.isNotBlank(shopId)){
            params.add(shopId);
        }
        if(StringUtils.isNotBlank(appid)){
            params.add(appid);
        }
        return params.toArray();
    }

    public List<ShopWxPay> find(String shopId, String appid, int offset, int limit){
        StringBuilder sql = new StringBuilder(128);
        sql.append("SELECT * FROM shop_wx_pay ");
        buildWhere(sql, shopId, appid);
        sql.append(" ORDER BY create_time DESC LIMIT ? OFFSET ?");
        Object[] params = DaoUtils.appendOffsetLimit(params(shopId, appid), offset, limit);

        return jdbcTemplate.query(sql.toString(), params, mapper);
    }
}
