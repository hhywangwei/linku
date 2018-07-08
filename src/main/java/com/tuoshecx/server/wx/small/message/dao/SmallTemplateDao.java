package com.tuoshecx.server.wx.small.message.dao;

import com.tuoshecx.server.common.utils.DaoUtils;
import com.tuoshecx.server.wx.small.message.domain.SmallTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 实现微信消息模板数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class SmallTemplateDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<SmallTemplate> mapper = (r, i) -> {
        SmallTemplate t = new SmallTemplate();

        t.setId(r.getString("id"));
        t.setAppid(r.getString("appid"));
        t.setGlobalId(r.getString("global_id"));
        t.setCallKey(r.getString("call_key"));
        t.setTemplateId(r.getString("template_id"));
        t.setTitle(r.getString("title"));
        t.setContent(r.getString("content"));
        t.setExample(r.getString("example"));
        t.setRemark(r.getString("remark"));
        t.setCreateTime(r.getTimestamp("create_time"));
        return t;
    };

    @Autowired
    public SmallTemplateDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(SmallTemplate t){
        String sql = "INSERT INTO wx_small_msg_template (id, appid, global_id, call_key, template_id, remark, create_time) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getAppid(), t.getGlobalId(), t.getCallKey(), t.getTemplateId(),
                t.getRemark(), DaoUtils.timestamp(new Date()));
    }

    public boolean updateInfo(String id, String title, String content, String example){
        String sql = "UPDATE wx_small_msg_template SET title = ?, content = ?, example = ? WHERE id = ? ";
        return jdbcTemplate.update(sql, title, content, example, id) > 0;
    }

    public SmallTemplate findOne(String id){
        String sql = "SELECT * FROM wx_small_msg_template WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }


    public SmallTemplate findOneByCallKey(String appid, String callKey){
        String sql = "SELECT * FROM wx_small_msg_template WHERE appid = ? AND call_key = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{appid, callKey}, mapper);
    }

    public List<SmallTemplate> findAll(String appid){
        String sql = "SELECT * FROM wx_small_msg_template WHERE appid = ?";
        return jdbcTemplate.query(sql, new Object[]{appid}, mapper);
    }

    public boolean delete(String id){
        String sql = "DELETE FROM wx_small_msg_template WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public long count(String appid, String callKey, String title, String remark){
        StringBuilder sql = new StringBuilder(30);
        sql.append("SELECT COUNT(id) FROM wx_small_msg_template ");
        buildWhere(sql, appid, callKey, title, remark);
        return jdbcTemplate.queryForObject(sql.toString(), params(appid, callKey, title, remark), Long.class);
    }

    private void buildWhere(StringBuilder sql, String appid, String callKey, String title, String remark){
        sql.append(" WHERE 1 = 1 ");
        if(StringUtils.isNotBlank(appid)){
            sql.append(" AND appid = ? ");
        }
        if(StringUtils.isNotBlank(callKey)){
            sql.append(" AND call_key LIKE ? ");
        }
        if(StringUtils.isNotBlank(title)){
            sql.append(" AND title LIKE ? ");
        }
        if(StringUtils.isNotBlank(remark)){
            sql.append(" AND remark LIKE ? ");
        }
    }

    private Object[] params(String appid, String callKey, String title, String remark){
        List<Object> params = new ArrayList<>(3);
        if(StringUtils.isNotBlank(appid)){
            params.add(appid);
        }
        if(StringUtils.isNotBlank(callKey)){
            params.add(DaoUtils.like(callKey));
        }
        if(StringUtils.isNotBlank(title)){
            params.add(DaoUtils.like(title));
        }
        if(StringUtils.isNotBlank(remark)){
            params.add(DaoUtils.like(remark));
        }

        return params.toArray();
    }

    public List<SmallTemplate> find(String appid, String callKey, String title, String remark, int offset, int limit){
        StringBuilder sql = new StringBuilder(100);

        sql.append("SELECT * FROM wx_small_msg_template ");
        buildWhere(sql, appid, callKey, title, remark);
        sql.append(" ORDER BY global_id ASC LIMIT ? OFFSET ?");

        Object[] params = DaoUtils.appendOffsetLimit(params(appid, callKey, title, remark), offset, limit);

        return jdbcTemplate.query(sql.toString(), params, mapper);
    }
}
