package com.tuoshecx.server.shop.dao;

import com.tuoshecx.server.common.utils.DaoUtils;
import com.tuoshecx.server.shop.domain.ShopWxAuthorized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;

@Repository
public class ShopWxUnauthorizedDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ShopWxUnauthorizedDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(ShopWxAuthorized t){
        final String sql = "INSERT INTO shop_wx_unauthorized (id, shop_id, appid, nickname, head_img, service_type_info," +
                "verify_type_info, username, name, business_info, mini_program_info, qrcode_url, authorization_info, create_time) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Date now = new Date();
        jdbcTemplate.update(sql, t.getId(), t.getShopId(), t.getAppid(), t.getNickname(), t.getHeadImg(), t.getServiceTypeInfo(),
                t.getVerifyTypeInfo(), t.getUsername(), t.getName(), t.getBusinessInfo(), t.getMiniProgramInfo(), t.getQrcodeUrl(),
                t.getAuthorizationInfo(), DaoUtils.timestamp(now));
    }
}
