package com.tuoshecx.server.wx.small.message.dao;

import com.tuoshecx.server.common.utils.DaoUtils;
import com.tuoshecx.server.wx.small.message.domain.SendMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class SendMessageDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<SendMessage> mapper = (r, i) -> {
        SendMessage t = new SendMessage();

        t.setId(r.getString("id"));
        t.setAppid(r.getString("appid"));
        t.setCallKey(r.getString("call_key"));
        t.setColor(r.getString("color"));
        t.setTitle(r.getString("title"));
        t.setContent(r.getString("content"));
        t.setEmphasisKeyword(r.getString("emphasis_keyword"));
        t.setError(r.getString("error"));
        t.setFormId(r.getString("form_id"));
        t.setOpenid(r.getString("openid"));
        t.setPage(r.getString("page"));
        t.setRetry(r.getInt("retry"));
        t.setState(r.getString("status"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public SendMessageDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(SendMessage t){
        final String sql = "INSERT INTO wx_msg_message (id, appid, openid, call_key, form_id, page, title, content, color, emphasis_keyword, state, error, create_time) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getAppid(), t.getOpenid(), t.getCallKey(), t.getFormId(), t.getPage(), t.getTitle(),
                t.getContent(), t.getColor(), t.getEmphasisKeyword(), t.getState(), t.getError(), DaoUtils.timestamp(new Date()));
    }

    public boolean updateStatus(String id, String state, String error){
        final String sql = "UPDATE wx_msg_message SET state = ?, error = ?, retry = retry + 1 WHERE id = ?";
        return jdbcTemplate.update(sql, state, error, id) > 0;
    }

    public SendMessage findOne(String id){
        final String sql = "SELECT * FROM wx_msg_message WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public long count(String appid, String title, String callKey, String openid, String state, Date fromTime, Date toTime){
        StringBuilder sql = new StringBuilder(200);
        sql.append("SELECT COUNT(id) FROM wx_msg_message ");
        return jdbcTemplate.queryForObject(sql.toString(), params(appid, title, callKey, openid, state, fromTime, toTime), Long.class);
    }

    private void buildWhere(StringBuilder sql, String appid, String title, String callKey, String openid, String state, Date fromTime, Date toTime){
        sql.append(" WHERE 1 = 1 ");
        if(StringUtils.isNotBlank(appid)){
            sql.append(" AND appid = ? ");
        }
        if(StringUtils.isNotBlank(title)){
            sql.append(" AND title LIKE ? ");
        }
        if(StringUtils.isNotBlank(callKey)){
            sql.append(" AND call_key LIKE ? ");
        }
        if(StringUtils.isNotBlank(openid)){
            sql.append(" AND openid = ? ");
        }
        if(StringUtils.isNotBlank(state)){
            sql.append(" AND state = ? ");
        }
        if(fromTime != null){
            sql.append(" AND create_time >= ? ");
        }
        if(toTime != null){
            sql.append(" AND create_time <= ?");
        }
    }

    private Object[] params(String appid, String title, String callKey, String openid, String state, Date fromTime, Date toTime){
        List<Object> params = new ArrayList<>();

        if(StringUtils.isNotBlank(appid)){
            params.add(appid);
        }
        if(StringUtils.isNotBlank(title)){
            params.add(title);
        }
        if(StringUtils.isNotBlank(callKey)){
            params.add(callKey);
        }
        if(StringUtils.isNotBlank(openid)){
            params.add(openid);
        }
        if(StringUtils.isNotBlank(state)){
            params.add(state);
        }
        if(fromTime != null){
            params.add(fromTime);
        }
        if(toTime != null){
            params.add(toTime);
        }

        return params.toArray();
    }

    public List<SendMessage> find(String appid, String title, String callKey, String openid, String state, Date fromTime, Date toTime, int offset, int limit){
        StringBuilder sql = new StringBuilder(200);
        sql.append("SELECT * FROM wx_msg_message ");
        buildWhere(sql, appid, title, callKey, openid, state, fromTime, toTime);
        sql.append(" ORDER BY create_time DESC LIMIT ? OFFSET ?");

        Object[] params = DaoUtils.appendOffsetLimit(params(appid, title, callKey, openid, state, fromTime, toTime), offset, limit);

        return jdbcTemplate.query(sql.toString(), params, mapper);
    }
}
