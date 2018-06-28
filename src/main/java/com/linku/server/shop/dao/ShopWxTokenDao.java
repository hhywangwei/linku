package com.linku.server.shop.dao;

import com.linku.server.common.utils.DaoUtils;
import com.linku.server.shop.domain.ShopWxToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;

/**
 * 店铺微信配置数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class ShopWxTokenDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ShopWxToken> mapper = (r, i) -> {
        ShopWxToken t = new ShopWxToken();

        t.setId(r.getString("id"));
        t.setShopId(r.getString("shop_id"));
        t.setAppid(r.getString("appid"));
        t.setAccessToken(r.getString("access_token"));
        t.setRefreshToken(r.getString("refresh_token"));
        t.setUpdateTime(r.getDate("update_time"));

        return t;
    };

    @Autowired
    public ShopWxTokenDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(ShopWxToken t){
        final String sql = "INSERT INTO shop_wx_token (id, shop_id, appid, access_token, refresh_token, update_time) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getShopId(), t.getAppid(), t.getAccessToken(), t.getRefreshToken(), DaoUtils.timestamp(new Date()));
    }

    public boolean updateToken(String appid, String accessToken, String refreshToken){
        final String sql = "UPDATE shop_wx_token SET access_token = ?, refresh_token = ? WHERE appid = ?";
        return jdbcTemplate.update(sql, accessToken, refreshToken, appid) > 0;
    }

    public ShopWxToken findOne(String id){
        final String sql = "SELECT * FROM shop_wx_token WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public boolean hasAppid(String appid){
        final String sql = "SELECT COUNT(id) FROM shop_wx_token WHERE appid = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{appid}, Integer.class) > 0;
    }

    public ShopWxToken findOneByAppid(String appid){
        final String sql = "SELECT * FROM shop_wx_token WHERE appid = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{appid}, mapper);
    }
}
