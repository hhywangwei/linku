package com.tuoshecx.server.wx.small.devops.dao;

import com.tuoshecx.server.common.utils.DaoUtils;
import com.tuoshecx.server.wx.small.devops.domain.SmallExtConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * 小程序发布配置信息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class SmallExtConfigureDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<SmallExtConfigure> mapper = (r, i) ->{
        SmallExtConfigure t = new SmallExtConfigure();

        t.setId(r.getString("id"));
        t.setTemplateId(r.getInt("template_id"));
        t.setExt(r.getString("ext"));
        t.setExtPages(r.getString("ext_pages"));
        t.setPages(r.getString("pages"));
        t.setTabBar(r.getString("tab_bar"));
        t.setWindow(r.getString("window"));
        t.setDebug(r.getBoolean("debug"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public SmallExtConfigureDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(SmallExtConfigure t){
        final String sql = "INSERT INTO wx_small_configure (id, template_id, ext, ext_pages, pages, tab_bar, window, debug, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getTemplateId(), t.getExt(), t.getExtPages(), t.getPages(), t.getTabBar(), t.getWindow(),
                t.getDebug(), DaoUtils.timestamp(new Date()));
    }

    public boolean update(SmallExtConfigure t){
        final String sql = "UPDATE wx_small_configure SET template_id = ?, ext = ?, ext_pages = ?, pages = ?, tab_bar = ?, window = ?, debug =? " +
                "WHERE id = ?";
        return jdbcTemplate.update(sql, t.getTemplateId(), t.getExt(), t.getExtPages(), t.getPages(), t.getTabBar(), t.getWindow(), t.getDebug(),
                t.getId()) > 0;
    }

    public boolean hasTemplateId(Integer templateId){
        final String sql = "SELECT COUNT(id) FROM wx_mall_configure WHERE template_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{templateId}, Integer.class) > 0;
    }

    public SmallExtConfigure findOne(String id){
        final String sql = "SELECT * FROM wx_small_configure WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM wx_small_configure WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public List<SmallExtConfigure> findByTemplateId(Integer templateId){
        final String sql = "SELECT * FROM wx_small_configure WHERE template_id = ?";
        return jdbcTemplate.query(sql, new Object[]{templateId}, mapper);
    }

    public Long count(Integer templateId){
        final String sql = "SELECT COUNT(id) FROM wx_small_configure WHERE template_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{templateId}, Long.class);
    }

    public List<SmallExtConfigure> find(Integer templateId, int offset, int limit){
        final String sql = "SELECT * FROM wx_small_configure WHERE template_id = ? LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new Object[]{templateId, limit, offset}, mapper);
    }
}
