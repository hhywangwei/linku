package com.tuoshecx.server.shop.dao;

import com.tuoshecx.server.common.utils.DaoUtils;
import com.tuoshecx.server.shop.domain.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * 店铺通知数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class NoticeDao {
    private final JdbcTemplate jdbcTemplate ;

    private final RowMapper<Notice> mapper = (r, i) -> {
        Notice t = new Notice();

        t.setId(r.getString("id"));
        t.setShopId(r.getString("shop_id"));
        t.setTitle(r.getString("title"));
        t.setType(r.getString("type"));
        t.setContent(r.getString("content"));
        t.setRead(r.getBoolean("is_read"));
        t.setUri(r.getString("uri"));
        t.setCreateTime(r.getDate("create_time"));
        t.setReadTime(r.getDate("read_time"));
        return t;
    };

    @Autowired
    public NoticeDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Notice t){
        final String sql = "INSERT INTO shop_notice (id, shop_id, title, type, content, is_read, uri, source, create_time, read_time) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        final Date now = new Date();
        jdbcTemplate.update(sql, t.getId(), t.getShopId(),t.getTitle(), t.getType(), t.getContent(), t.getRead(), t.getUri(),
                t.getSource(), DaoUtils.date(now), DaoUtils.date(now));
    }

    public boolean update(Notice t){
        final String sql = "UPDATE shop_notice SET title =?, type =?, content =?, uri =? WHERE id = ?";
        return jdbcTemplate.update(sql, t.getTitle(), t.getType(), t.getContent(), t.getUri(), t.getId()) > 0;
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM shop_notice WHERE id =?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public boolean read(String id){
        final String sql = "UPDATE shop_notice SET is_read = ?, read_time = ? WHERE id = ?";
        return jdbcTemplate.update(sql, true, DaoUtils.timestamp(new Date()), id) > 0;
    }

    public Notice findOne(String id){
        final String sql = "SELECT * FROM shop_notice WHERE id =?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public boolean has(String source){
        final String sql = "SELECT COUNT(id) FROM shop_notice WHERE source = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{source}, Integer.class) > 0;
    }

    public long count(String shopId, String title){
        final String sql = "SELECT COUNT(id) FROM shop_notice WHERE shop_id =? AND title LIKE ?";
        return jdbcTemplate.queryForObject(sql, buildParameters(shopId, title), long.class);
    }

    private Object[] buildParameters(String shopId, String title){
        return new Object[] {
                shopId, DaoUtils.blankLike(title)
        };
    }

    public List<Notice> find(String shopId, String title, int offset, int limit){
        final String sql = "SELECT * FROM shop_notice WHERE shop_id =? AND title LIKE ? " +
                "ORDER BY create_time DESC LIMIT ? OFFSET ?";

        Object[] params = DaoUtils.appendOffsetLimit(buildParameters(shopId, title), offset, limit);
        return jdbcTemplate.query(sql, params, mapper);
    }
}
