package com.tuoshecx.server.marketing.dao;

import com.tuoshecx.server.marketing.domain.GroupRecordItem;
import com.tuoshecx.server.common.utils.DaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * 团购明细数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class GroupRecordItemDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<GroupRecordItem> mapper = (r, i) -> {
        GroupRecordItem t = new GroupRecordItem();

        t.setId(r.getString("id"));
        t.setRecordId(r.getString("record_id"));
        t.setUserId(r.getString("user_id"));
        t.setNickname(r.getString("nickname"));
        t.setHeadImg(r.getString("head_img"));
        t.setPhone(r.getString("phone"));
        t.setOrderId(r.getString("order_id"));
        t.setOwner(r.getBoolean("is_owner"));
        t.setFirst(r.getBoolean("is_first"));
        t.setCancel(r.getBoolean("is_cancel"));
        t.setCreateTime(r.getTimestamp("create_time"));
        t.setCancelTime(r.getTimestamp("cancel_time"));

        return t;
    };

    @Autowired
    public GroupRecordItemDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(GroupRecordItem t){
        final String sql = "INSERT INTO marketing_record_group_item (id, record_id, user_id, nickname, head_img, phone, is_owner, " +
                "is_first, order_id, is_cancel, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getRecordId(), t.getUserId(), t.getNickname(), t.getHeadImg(), t.getPhone(),
                t.getOwner(), t.getFirst(), t.getOrderId(), false, DaoUtils.timestamp(new Date()));
    }

    public boolean cancel(String id){
        final String sql = "UPDATE marketing_record_group_item SET is_cancel = true, cancel_time = ? WHERE id = ? AND is_cancel = false";
        return jdbcTemplate.update(sql, DaoUtils.timestamp(new Date()), id) > 0;
    }

    public boolean hasOrderId(String orderId){
        final String sql = "SELECT COUNT(id) FROM marketing_record_group_item WHERE order_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{orderId}, Integer.class) > 0;
    }

    public List<GroupRecordItem> findLimit(String recordId, int limit){
        final String sql = "SELECT * FROM marketing_record_group_item WHERE record_id = ? AND is_cancel = false " +
                "ORDER BY create_time ASC LIMIT ?";
        return jdbcTemplate.query(sql, new Object[]{recordId, limit}, mapper);
    }
}
