package com.tuoshecx.server.base.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * 通过数据库实现自只增数处理
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class AutoNumberDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AutoNumberDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(String key){
        final String sql = "INSERT INTO base_auto_number (n_key, number) VALUES (?, ?)";
        jdbcTemplate.update(sql, key, 0);
    }

    public boolean hasKey(String key){
        final String sql = "SELECT COUNT(id) FROM base_auto_number WHERE n_key = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{key}, Integer.class) > 0;
    }

    public int findNumber(String key){
        final String sql = "SELECT number FROM base_auto_number WHERE n_key = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{key}, Integer.class);
    }

    public boolean updateNumber(String key, int number, int newNumber){
        final String sql = "UPDATE base_auto_number SET number = ? WHERE n_key = ? AND number = ?";
        return jdbcTemplate.update(sql, newNumber, key, number) > 0;
    }
}
