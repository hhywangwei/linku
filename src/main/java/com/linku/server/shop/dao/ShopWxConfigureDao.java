package com.linku.server.shop.dao;

import com.linku.server.common.utils.DaoUtils;
import com.linku.server.shop.domain.ShopWxConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

@Repository
public class ShopWxConfigureDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ShopWxConfigure> mapper = (r, i) -> {
        ShopWxConfigure t = new ShopWxConfigure();

        t.setId(r.getString("id"));
        t.setShopId(r.getString("shop_id"));
        t.setAppid(r.getString("appid"));
        t.setNickname(r.getString("nickname"));
        t.setHeadImg(r.getString("head_img"));
        t.setServiceTypeInfo(r.getInt("service_type_info"));
        t.setVerifyTypeInfo(r.getInt("verify_type_info"));
        t.setUsername(r.getString("username"));
        t.setName(r.getString("name"));
        t.setBusinessInfo(r.getString("business_info"));
        t.setQrcodeUrl(r.getString("qrcode_url"));
        t.setAuthorizationInfo(r.getString("authorization_info"));
        t.setAuthorization(r.getBoolean("authorization"));
        t.setMiniProgramInfo(r.getString("mini_program_info"));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return  t;
    };

    @Autowired
    public ShopWxConfigureDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(ShopWxConfigure t){
        final String sql = "INSERT INTO shop_wx_configure (id, shop_id, appid, nickname, head_img, service_type_info," +
                "verify_type_info, username, name, business_info, mini_program_info, qrcode_url, authorization_info, authorization, update_time, create_time) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Date now = new Date();
        jdbcTemplate.update(sql, t.getId(), t.getShopId(), t.getAppid(), t.getNickname(), t.getHeadImg(), t.getServiceTypeInfo(),
                t.getVerifyTypeInfo(), t.getUsername(), t.getName(), t.getBusinessInfo(), t.getMiniProgramInfo(), t.getQrcodeUrl(),
                t.getAuthorizationInfo(), t.getAuthorization(), DaoUtils.timestamp(now), DaoUtils.timestamp(now));
    }

    public boolean update(ShopWxConfigure t) {
        final String sql = "UPDATE shop_wx_configure SET nickname = ?, head_img = ?, service_type_info = ?," +
                "verify_type_info = ?, username = ?, name = ?, business_info = ?, mini_program_info = ?, qrcode_url = ?," +
                "authorization_info = ?, authorization = ?, update_time = ? WHERE appid = ? ";

        return jdbcTemplate.update(sql, t.getNickname(), t.getHeadImg(), t.getServiceTypeInfo(), t.getVerifyTypeInfo(),
                t.getUsername(), t.getName(), t.getBusinessInfo(), t.getMiniProgramInfo(), t.getQrcodeUrl(), t.getAuthorizationInfo(),
                t.getAuthorization(), DaoUtils.timestamp(new Date()), t.getAppid()) > 0;
    }

    public boolean delete(String appid){
        final String sql = "DELETE FROM shop_wx_configure WHERE appid = ?";
        return jdbcTemplate.update(sql, appid) > 0;
    }

    public boolean hasAppid(String appid){
        return jdbcTemplate.queryForObject("SELECT COUNT(id) FROM shop_wx_configure WHERE appid = ?",
                new Object[]{appid}, Integer.class) > 0;
    }

    public ShopWxConfigure findOne(String id){
        return jdbcTemplate.queryForObject("SELECT * FROM shop_wx_configure WHERE id = ?", new Object[]{id}, mapper);
    }

    public ShopWxConfigure findOneByAppid(String appid){
        return jdbcTemplate.queryForObject("SELECT * FROM shop_wx_configure WHERE appid = ?", new Object[]{appid}, mapper);
    }

    public List<ShopWxConfigure> findByShopId(String shopId){
        return jdbcTemplate.query("SELECT * FROM shop_wx_configure WHERE shop_id = ?", new Object[]{shopId}, mapper);
    }
}
