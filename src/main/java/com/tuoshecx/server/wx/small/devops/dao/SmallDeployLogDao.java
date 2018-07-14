package com.tuoshecx.server.wx.small.devops.dao;

import com.tuoshecx.server.common.utils.DaoUtils;
import com.tuoshecx.server.wx.small.devops.domain.SmallDeployLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * 微信小程序发布日志数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class SmallDeployLogDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<SmallDeployLog> mapper = (r, i) -> {
        SmallDeployLog t = new SmallDeployLog();

        t.setId(r.getString("id"));
        t.setDeployId(r.getString("deploy_id"));
        t.setAction(r.getString("action"));
        t.setMessage(r.getString("message"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public SmallDeployLogDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(SmallDeployLog t){
        final String sql = "INSERT INTO wx_small_deploy_log (id, deploy_id, action, message, create_time) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getDeployId(), t.getAction(), t.getMessage(), DaoUtils.timestamp(new Date()));
    }

    public List<SmallDeployLog> findByDeployId(String deployId){
        final String sql = "SELECT * FROM wx_small_deploy_log WHERE deploy_id = ?";
        return jdbcTemplate.query(sql, new Object[]{deployId}, mapper);
    }
}
