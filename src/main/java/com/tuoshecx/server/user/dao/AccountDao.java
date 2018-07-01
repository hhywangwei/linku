package com.tuoshecx.server.user.dao;

import com.tuoshecx.server.common.utils.DaoUtils;
import com.tuoshecx.server.user.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;

/**
 * 用户账户数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class AccountDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Account> mapper = (r, i) -> {
        Account t = new Account();

        t.setId(r.getString("id"));
        t.setUserId(r.getString("user_id"));
        t.setPayCode(r.getString("pay_code"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public AccountDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Account t){
        final String sql = "INSERT INTO shop_account (id, user_id, pay_code, create_time) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getUserId(), t.getPayCode(), DaoUtils.timestamp(new Date()));
    }

    public boolean updatePayCode(String id, String payCode){
        final String sql = "UPDATE shop_account SET pay_code = ? WHERE id = ?";
        return jdbcTemplate.update(sql, payCode, id) > 0;
    }

    public Account findOne(String id){
        final String sql = "SELECT * FROM shop_account WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Account findOneByUserId(String userId){
        final String sql = "SELECT * FROM shop_account WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, mapper);
    }

    public boolean lock(String userId){
        final String sql = "SELECT id FROM shop_account WHERE user_id = ? FOR UPDATE";
        jdbcTemplate.queryForObject(sql, new Object[]{userId}, String.class);
        return true;
    }
}
