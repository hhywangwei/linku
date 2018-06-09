package com.linku.server.base.dao;

import com.linku.server.common.utils.DaoUtils;
import com.linku.server.base.domain.Sys;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 系统管理员数据操作
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class SysDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Sys> mapper = (r, i) -> {
        Sys t = new Sys();

        t.setId(r.getString("id"));
        t.setUsername(r.getString("username"));
        t.setPassword(r.getString("password"));
        t.setName(r.getString("name"));
        t.setHeadImg(r.getString("head_img"));
        t.setPhone(r.getString("phone"));
        t.setPassword(r.getString("password"));
        t.setEnable(r.getBoolean("is_enable"));
        t.setManager(r.getBoolean("is_manager"));
        t.setRoles(DaoUtils.toArray(r.getString("roles")));
        t.setCreateTime(r.getTimestamp("create_time"));
        t.setUpdateTime(r.getTimestamp("update_time"));

        return t;
    };

    @Autowired
    public SysDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Sys t){
        final String sql = "INSERT INTO base_sys (id, username, password, name, head_img, phone, email, is_enable, " +
                "is_manager, roles, create_time, update_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Date now = new Date();
        jdbcTemplate.update(sql, t.getId(), t.getUsername(), t.getPassword(), t.getName(), t.getHeadImg(), t.getPhone(),
                t.getEmail(), t.getEnable(), t.getManager(), DaoUtils.join(t.getRoles()), DaoUtils.timestamp(now), DaoUtils.timestamp(now));
    }

    public boolean update(Sys t){
        final String sql = "UPDATE base_sys SET name = ?, head_img = ?, phone = ?, email = ?, roles = ?, " +
                "update_time = ? WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, t.getName(), t.getHeadImg(), t.getPhone(), t.getEmail(),
                DaoUtils.join(t.getRoles()), DaoUtils.timestamp(new Date()), t.getId()) > 0;
    }

    public boolean updateEnable(String id, boolean enable){
        final String sql = "UPDATE base_sys SET is_enable = ?, update_time = ? WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, enable, DaoUtils.timestamp(new Date()), id) > 0;
    }

    public boolean updatePassword(String id, String password){
        final String sql = "UPDATE base_sys SET password = ?, update_time = ? WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, password, DaoUtils.date(new Date()), id) > 0;
    }

    public Sys findOne(String id){
        return jdbcTemplate.queryForObject("SELECT * FROM base_sys WHERE id = ? AND is_delete = false", new Object[]{id}, mapper);
    }

    public boolean hasUsername(String username){
        return jdbcTemplate.queryForObject("SELECT COUNT(id) FROM base_sys WHERE username = ?",
                new Object[]{username}, Integer.class) > 0;
    }

    public Sys findOneByUsername(String username){
        return jdbcTemplate.queryForObject("SELECT * FROM base_sys WHERE username = ? AND is_delete = false",
                new Object[]{username}, mapper);
    }

    public boolean delete(String id){
        final String sql = "UPDATE base_sys SET username = CONCAT(username,?), is_delete =true " +
                "WHERE id =? AND is_delete = false";
        final String random = "@" + String.valueOf(RandomUtils.nextInt(10000000, 99999999));
        return jdbcTemplate.update(sql, random, id) > 0;
    }

    public long count(String username, String name, String phone){
        final String sql = "SELECT COUNT(id) FROM base_sys " +
                "WHERE username LIKE ? AND name LIKE ? AND phone LIKE ?";
        return jdbcTemplate.queryForObject(sql, buildParameters(username, name, phone), Long.class);
    }

    private Object[] buildParameters(String username, String name, String phone){
        return new Object[]{DaoUtils.blankLike(username), DaoUtils.blankLike(name), DaoUtils.blankLike(phone)};
    }

    public List<Sys> find(String username, String name, String phone, int offset, int limit){
        final String sql = "SELECT * FROM base_sys " +
                "WHERE username LIKE ? AND name LIKE ? AND phone LIKE ? " +
                "ORDER BY update_time DESC LIMIT ? OFFSET ?";

        Object[] params = Arrays.copyOf(buildParameters(username, name, phone), 5);
        params[3] = limit;
        params[4] = offset;
        return jdbcTemplate.query(sql, params, mapper);
    }
}
