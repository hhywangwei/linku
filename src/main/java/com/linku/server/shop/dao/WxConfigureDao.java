package com.linku.server.shop.dao;

import com.linku.server.shop.domain.WxConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * 店铺微信配置数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class WxConfigureDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<WxConfigure> mapper = (r, i) -> {
        WxConfigure t = new WxConfigure();

        t.setId(r.getString("id"));
        t.setShopId(r.getString("shop_id"));
        t.setAppid(r.getString("appid"));

        return t;
    };

    @Autowired
    public WxConfigureDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(WxConfigure t){
        final String sql = "INSERT INTO shop_configure (id, shop_id, appid) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getShopId(), t.getAppid());
    }

    public boolean update(WxConfigure t){
        final String sql = "UPDATE shop_configure SET appid = ? WHERE id = ?";
        return jdbcTemplate.update(sql, t.getAppid(), t.getId()) > 0;
    }

    public WxConfigure findOne(String id){
        final String sql = "SELECT * FROM shop_configure WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public WxConfigure findOneByAppid(String appid){
        final String sql = "SELECT * FROM shop_configure WHERE appid = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{appid}, mapper);
    }
}
