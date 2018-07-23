package com.tuoshecx.server.marketing.dao;

import com.tuoshecx.server.common.utils.DaoUtils;
import com.tuoshecx.server.marketing.domain.GroupRecordFinish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;

/**
 *  组团类营销活动结束记录数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class GroupRecordFinishDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupRecordFinishDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(GroupRecordFinish t){
        String sql = "INSERT INTO marketing_record_finish (id, record_id, item_id, action, state, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getRecordId(), t.getItemId(), t.getAction(),
                GroupRecordFinish.State.WAIT.name(), DaoUtils.timestamp(new Date()));
    }

    public boolean success(String recordId, String itemId){
        String sql = "UPDATE marketing_record_finish SET state = ? WHERE record_id = ? AND item_id = ?";
        return jdbcTemplate.update(sql, GroupRecordFinish.State.SUCCESS.name(), recordId, itemId) > 0;
    }

    public boolean fail(String recordId, String itemId, String message){
        String sql = "UPDATE marketing_record_finish SET state = ?, message =? WHERE record_id = ? AND item_id = ?";
        return jdbcTemplate.update(sql, GroupRecordFinish.State.FAIL.name(), message, recordId, itemId) > 0;
    }
}