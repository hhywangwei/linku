package com.linku.server.shop.dao;

import com.linku.server.common.utils.DaoUtils;
import com.linku.server.shop.domain.Manager;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * 店铺雇员数据操作
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class ManagerDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Manager> mapper = (r, i) -> {
        Manager t = new Manager();

        t.setId(r.getString("id"));
        t.setShopId(r.getString("shop_id"));
        t.setUsername(r.getString("username"));
        t.setPassword(r.getString("password"));
        t.setName(r.getString("name"));
        t.setHeadImg(r.getString("head_img"));
        t.setPhone(r.getString("phone"));
        t.setRoles(DaoUtils.toArray(r.getString("roles")));
        t.setManager(r.getBoolean("is_manager"));
        t.setEnable(r.getBoolean("is_enable"));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public ManagerDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Manager t){
        final String sql = "INSERT INTO shop_manager " +
                "(id, shop_id, username, password, name, head_img, phone, roles, is_manager, is_enable, update_time, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        final Date now = new Date();
        jdbcTemplate.update(sql, t.getId(), t.getShopId(), t.getUsername(), t.getPassword(),
                StringUtils.defaultString(t.getName()), t.getHeadImg(),
                StringUtils.defaultString(t.getPhone()), DaoUtils.join(t.getRoles()), t.getManager(),
                t.getEnable(), DaoUtils.timestamp(now), DaoUtils.timestamp(now));
    }

    public boolean update(Manager t){
        final String sql = "UPDATE shop_manager SET name =?, head_img =?, phone =?, roles =?, update_time =? " +
                "WHERE id = ? AND is_delete =false";
        return jdbcTemplate.update(sql, StringUtils.defaultString(t.getName()),
                t.getHeadImg(), StringUtils.defaultString(t.getPhone()), DaoUtils.join(t.getRoles()),
                DaoUtils.timestamp(new Date()), t.getId()) > 0;
    }

    public boolean delete(String id){
        final String sql = "UPDATE shop_manager SET username = CONCAT(username,?), is_delete =true " +
                "WHERE id =? AND is_delete =false";
        final String random = "@" + String.valueOf(RandomUtils.nextInt(10000000, 99999999));
        return jdbcTemplate.update(sql, random, id) > 0;
    }

    public boolean updateEnable(String id, Boolean enabled){
        final String sql = "UPDATE shop_manager SET is_enable =?, update_time =? WHERE id = ? AND is_delete= false";
        return jdbcTemplate.update(sql, enabled, DaoUtils.timestamp(new Date()), id) > 0;
    }

    public boolean updatePassword(String id, String password){
        final String sql = "UPDATE shop_manager SET password = ?, update_time = ? WHERE id = ?";
        return jdbcTemplate.update(sql, password, DaoUtils.date(new Date()), id) > 0;
    }

    public boolean hasUsername(String username){
        final String sql = "SELECT COUNT(id) FROM shop_manager WHERE username =?";
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, Integer.class) > 0;
    }

    public Manager findOne(String id){
        final String sql = "SELECT * FROM shop_manager WHERE id =? AND is_delete =false";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Manager findOneByUsername(String username){
        final String sql = "SELECT * FROM shop_manager WHERE username = ? AND is_delete = false";
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, mapper);
    }

    public long count(String shopId, String username, String name, String phone){
        final String sql = "SELECT COUNT(id) FROM shop_manager " +
                "WHERE shop_id =? AND username LIKE ? AND name LIKE ? AND phone LIKE ? AND is_delete =false";
        return jdbcTemplate.queryForObject(sql, buildParameters(shopId, username, name, phone), Long.class);
    }

    private Object[] buildParameters(String shopId, String username, String name, String phone){
        return new Object[]{
                shopId, DaoUtils.blankLike(username), DaoUtils.blankLike(name), DaoUtils.blankLike(phone)
        };
    }

    public List<Manager> find(String shopId, String username, String name, String phone, int offset, int limit){
        final String sql = "SELECT * FROM shop_manager " +
                "WHERE shop_id =? AND username LIKE ? AND name LIKE ? AND phone LIKE ? AND is_delete = false " +
                "ORDER BY create_time DESC LIMIT ? OFFSET ?";

        Object[] params = DaoUtils.appendOffsetLimit(buildParameters(shopId, username, name, phone), offset, limit);
        return jdbcTemplate.query(sql, params, mapper);
    }

    public List<Manager> findByShopId(String shopId){
        final String sql = "SELECT * FROM shop_manager WHERE shop_id = ? AND is_delete = false";
        return jdbcTemplate.query(sql, new Object[]{shopId}, mapper);
    }
}
