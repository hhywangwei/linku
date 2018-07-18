package com.tuoshecx.server.wx.small.devops.dao;

import com.tuoshecx.server.common.utils.DaoUtils;
import com.tuoshecx.server.wx.small.devops.domain.SmallAuditConfigure;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 小程序提交审核配置信息数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class SmallAuditConfigureDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<SmallAuditConfigure> mapper = (r, i) -> {
        SmallAuditConfigure t = new SmallAuditConfigure();

        t.setId(r.getString("id"));
        t.setShopId(r.getString("shop_id"));
        t.setAppid(r.getString("appid"));
        t.setTitle(r.getString("title"));
        t.setTag(r.getString("tag"));
        t.setAddress(r.getString("address"));
        t.setFirstId(r.getInt("first_id"));
        t.setFirstClass(r.getString("first_class"));
        t.setSecondId(r.getInt("second_id"));
        t.setSecondClass(r.getString("second_class"));
        t.setThirdId(r.getInt("third_id"));
        t.setThirdClass(r.getString("third_class"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public SmallAuditConfigureDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(SmallAuditConfigure t){
        final String sql = "INSERT INTO wx_small_audit_configure (id, shop_id, appid, title, address, tag, first_id, first_class, " +
                "second_id, second_class, third_id, third_class, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getShopId(), t.getAppid(), t.getTitle(), t.getAddress(), t.getTag(), t.getFirstId(),
                t.getFirstClass(), t.getSecondId(), t.getSecondClass(), t.getThirdId(), t.getThirdClass(), DaoUtils.timestamp(new Date()));
    }

    public boolean update(SmallAuditConfigure t) {
        final String sql = "UPDATE wx_small_audit_configure SET title = ?, address = ?, tag = ?, first_id = ?, first_class =?," +
                "second_id = ?, second_class =?, third_id = ?, third_class =? WHERE id = ?";
        return jdbcTemplate.update(sql, t.getTitle(), t.getAddress(), t.getTag(), t.getFirstId(), t.getFirstClass(), t.getSecondId(),
                t.getSecondClass(), t.getThirdId(), t.getThirdClass(), t.getId()) > 0;
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM wx_small_audit_configure WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public void deleteByAppid(String appid){
        final String sql = "DELETE FROM wx_small_audit_configure WHERE appid = ?";
        jdbcTemplate.update(sql, appid);
    }

    public SmallAuditConfigure findOne(String id){
        final String sql = "SELECT * FROM wx_small_audit_configure WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public List<SmallAuditConfigure> findByAppid(String appid){
        final String sql = "SELECT * FROM wx_small_audit_configure WHERE appid = ?";
        return jdbcTemplate.query(sql, new Object[]{appid}, mapper);
    }

    public long count(String shopId, String appid, String title, String tag){
        StringBuilder sql = new StringBuilder(50);
        sql.append("SELECT COUNT(id) FROM wx_small_audit_configure ");
        buildWhere(sql, shopId, appid, title, tag);
        return jdbcTemplate.queryForObject(sql.toString(), params(shopId, appid, title, tag), Long.class);
    }

    private void buildWhere(StringBuilder sql, String shopId, String appid, String title, String tag){
        sql.append(" WHERE 1 = 1 ");
        if(StringUtils.isNotBlank(shopId)){
            sql.append(" AND shop_id = ? ");
        }
        if(StringUtils.isNotBlank(appid)){
            sql.append(" AND appid = ? ");
        }
        if(StringUtils.isNotBlank(title)){
            sql.append(" AND title LIKE ? ");
        }
        if(StringUtils.isNotBlank(tag)){
            sql.append(" AND tag LIKE ? ");
        }
    }

    private Object[] params(String shopId, String appid, String title, String tag){
        List<String> params = new ArrayList<>(4);
        if(StringUtils.isNotBlank(shopId)){
            params.add(shopId);
        }
        if(StringUtils.isNotBlank(appid)){
            params.add(appid);
        }
        if(StringUtils.isNotBlank(title)){
            params.add(DaoUtils.like(title));
        }
        if(StringUtils.isNotBlank(tag)){
            params.add(DaoUtils.like(tag));
        }
        return params.toArray();
    }

    public List<SmallAuditConfigure> find(String shopId, String appid, String title, String tag, int offset, int limit){
        StringBuilder sql = new StringBuilder(80);
        sql.append("SELECT * FROM wx_small_audit_configure ");
        buildWhere(sql, shopId, appid, title, tag);
        sql.append(" ORDER BY create_time DESC LIMIT ? OFFSET ?");
        Object[] params = DaoUtils.appendOffsetLimit(params(shopId, appid, title, tag), offset, limit);

        return jdbcTemplate.query(sql.toString(), params, mapper);
    }
}
