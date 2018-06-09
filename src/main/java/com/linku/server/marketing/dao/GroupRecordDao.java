package com.linku.server.marketing.dao;

import com.linku.server.marketing.domain.GroupRecord;
import com.linku.server.common.utils.DaoUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.linku.server.marketing.domain.GroupRecord.State.*;

/**
 * 团购数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class GroupRecordDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<GroupRecord> mapper = (r, i) -> {
        GroupRecord t = new GroupRecord();

        t.setId(r.getString("id"));
        t.setShopId(r.getString("shop_id"));
        t.setMarketingId(r.getString("marketing_id"));
        t.setName(r.getString("name"));
        t.setIcon(r.getString("icon"));
        t.setNeedPerson(r.getInt("need_person"));
        t.setJoinPerson(r.getInt("join_person"));
        t.setPrice(r.getInt("price"));
        t.setGoodsId(r.getString("goods_id"));
        t.setState(GroupRecord.State.valueOf(r.getString("state")));
        t.setFirst(r.getBoolean("first"));
        t.setShare(r.getBoolean("is_share"));
        t.setJoinUserDetail(r.getString("join_user_detail"));
        t.setType(r.getString("type"));
        t.setUserId(r.getString("user_id"));
        t.setVersion(r.getInt("version"));
        t.setStartTime(r.getDate("start_time"));
        t.setEndTime(r.getDate("end_time"));
        t.setCloseTime(r.getDate("close_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public GroupRecordDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(GroupRecord t){
        final String sql = "INSERT INTO marketing_record_group (id, shop_id, marketing_id, name, icon, need_person, " +
                "join_person, price, goods_id, state, first, type, user_id, version, start_time, end_time, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, t.getId(), t.getShopId(), t.getMarketingId(), t.getName(), t.getIcon(),
                t.getNeedPerson(), t.getJoinPerson(), t.getPrice(), t.getGoodsId(), t.getState().name(),
                t.getFirst(), t.getType(), t.getUserId(), 0, DaoUtils.date(t.getStartTime()),
                DaoUtils.date(t.getEndTime()), DaoUtils.timestamp(new Date()));
    }

    public boolean incJoinPerson(String id, int inc, int version){
        final String sql = "UPDATE marketing_record_group SET join_person = join_person + ?, version = version + 1 " +
                "WHERE id = ? AND version = ?";
        return jdbcTemplate.update(sql, inc, id, version) > 0;
    }

    public boolean decJoinPerson(String id, int inc, int version){
        final String sql = "UPDATE marketing_record_group SET join_person = join_person - ?, version = version + 1  " +
                "WHERE id = ? AND version =? AND join_person <= 0";
        return jdbcTemplate.update(sql, inc, id, version) > 0;
    }

    public void updateJoinUserDetail(String id, String joinUserDetail){
        final String sql = "UPDATE marketing_record_group SET join_user_detail = ? WHERE id = ?";
        jdbcTemplate.update(sql, joinUserDetail, id);
    }

    public boolean active(String id){
        final String sql = "UPDATE marketing_record_group SET state = ? WHERE id = ? AND state = ? AND join_person >= need_person";
        return jdbcTemplate.update(sql, ACTIVATE.name(), id, WAIT.name()) > 0;
    }

    public boolean close(String id){
        final String sql = "UPDATE marketing_record_group SET state = ? WHERE id = ? AND state = ?";
        return jdbcTemplate.update(sql, CLOSE.name(), id, WAIT.name()) > 0;
    }

    public void share(String id){
        final String sql = "UPDATE marketing_record_group SET is_share = ? WHERE id = ?";
        jdbcTemplate.update(sql, true, id);
    }

    public List<GroupRecord> findExpired(Date date, int limit){
        final String sql = "SELECT * FROM marketing_record_group WHERE state = ? AND end_time < ? LIMIT ?";
        return jdbcTemplate.query(sql, new Object[]{WAIT.name(), DaoUtils.timestamp(date), limit}, mapper);
    }

    public GroupRecord findOne(String id){
        final String sql = "SELECT * FROM marketing_record_group WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public GroupRecord findPresentOneWait(String marketingId, String userId){
        final String sql = "SELECT * FROM marketing_record_group WHERE marketing_id = ? AND user_id = ? AND state = ? ORDER BY create_time DESC LIMIT ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{marketingId, userId, GroupRecord.State.WAIT.name(), 1}, mapper);
    }

    public long count(String userId, String marketingId, GroupRecord.State state){
        final StringBuilder builder = new StringBuilder(50);
        builder.append("SELECT COUNT(id) FROM marketing_record_group ");
        builder.append(" WHERE 1 = 1 ");
        buildWhere(builder, userId, marketingId, state);

        return jdbcTemplate.queryForObject(builder.toString(), params(userId, marketingId, state), Long.class);
    }

    private void buildWhere(StringBuilder builder, String userId, String marketingId, GroupRecord.State state){
        if(StringUtils.isNotBlank(userId)){
            builder.append(" AND user_id = ? ");
        }
        if(StringUtils.isNotBlank(marketingId)){
            builder.append(" AND marketing_id = ?");
        }
        if(state != null){
            builder.append(" AND state = ? ");
        }
    }

    private String[] params(String userId, String marketingId, GroupRecord.State state){
        List<String> params = new ArrayList<>(3);
        if(StringUtils.isNotBlank(userId)){
            params.add(userId);
        }
        if(StringUtils.isNotBlank(marketingId)){
            params.add(marketingId);
        }
        if(state != null){
            params.add(state.name());
        }
        return params.toArray(new String[0]);
    }

    public List<GroupRecord> find(String userId, String marketingId, GroupRecord.State state, String order, int offset, int limit){
        final StringBuilder builder = new StringBuilder(50);
        builder.append("SELECT COUNT(id) FROM marketing_record_group ");
        builder.append(" WHERE 1 = 1 ");
        buildWhere(builder, userId, marketingId, state);
        String newOrder = StringUtils.isBlank(order) ? "create_time DESC" : order;
        builder.append(" ORDER BY ").append(order).append(" LIMIT ? OFFSET ?");
        Object[] params = DaoUtils.appendOffsetLimit(params(userId, marketingId, state), offset, limit);
        return jdbcTemplate.query(builder.toString(), params, mapper);
    }
}
