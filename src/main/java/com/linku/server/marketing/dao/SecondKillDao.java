package com.linku.server.marketing.dao;

import com.linku.server.common.utils.DaoUtils;
import com.linku.server.marketing.domain.SecondKill;
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
 * 秒杀活动设置数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class SecondKillDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<SecondKill> mapper = (r, i) -> {
        SecondKill t = new SecondKill();

        t.setId(r.getString("id"));
        t.setName(r.getString("name"));
        t.setIcon(r.getString("icon"));
        t.setImages(DaoUtils.toArray(r.getString("images")));
        t.setSummary(r.getString("summary"));
        t.setDetail(r.getString("detail"));
        t.setGoodsId(r.getString("goods_id"));
        t.setHelp(r.getString("help"));
        t.setOpen(r.getBoolean("is_open"));
        t.setShowOrder(r.getInt("show_order"));
        t.setPrice(r.getInt("price"));
        t.setShopId(r.getString("shop_id"));
        t.setStartTime(r.getTimestamp("start_time"));
        t.setEndTime(r.getTimestamp("end_time"));
        t.setStock(r.getInt("stock"));
        t.setRemain(r.getInt("remain"));
        t.setVersion(r.getInt("version"));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public SecondKillDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(SecondKill t){
        final String sql = "INSERT INTO marketing_conf_second_kill (id, shop_id, goods_id, name, icon, images, summary, detail, " +
                "help, price, start_time, end_time, is_open, show_order, stock, remain, version, update_time, create_time)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        final Date now = new Date();
        jdbcTemplate.update(sql, t.getId(), t.getShopId(), t.getGoodsId(), t.getName(), t.getIcon(),
                DaoUtils.join(t.getImages()), StringUtils.defaultString(t.getSummary()), StringUtils.defaultString(t.getDetail()),
                StringUtils.defaultString(t.getHelp()), t.getPrice(), DaoUtils.date(t.getStartTime()), DaoUtils.date(t.getEndTime()),
                t.getOpen(), t.getShowOrder(), t.getStock(), t.getStock(), 0, DaoUtils.timestamp(now), DaoUtils.timestamp(now));
    }

    public boolean update(SecondKill t){
        final String sql = "UPDATE marketing_conf_second_kill SET goods_id = ?, name = ?, icon = ?, images = ?, summary = ?, " +
                "detail = ?, help = ?, price = ?, start_time = ?, end_time = ?, is_open = ?, show_order = ?, update_time = ? " +
                "WHERE id = ?";
        return jdbcTemplate.update(sql, t.getGoodsId(), t.getName(), t.getIcon(), DaoUtils.join(t.getImages()),
                StringUtils.defaultString(t.getSummary()), StringUtils.defaultString(t.getDetail()),
                StringUtils.defaultString(t.getHelp()), t.getPrice(), DaoUtils.date(t.getStartTime()),
                DaoUtils.date(t.getEndTime()), t.getOpen(), t.getShowOrder(), DaoUtils.timestamp(new Date()), t.getId()) > 0;
    }

    public boolean updateOpen(String id, boolean isOpen){
        final String sql = "UPDATE marketing_conf_second_kill SET is_open = ?, update_time = ? WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, isOpen, DaoUtils.timestamp(new Date()), id) > 0;
    }

    public boolean incStock(String id, int number, int version){
        final String sql = "UPDATE marketing_conf_second_kill SET stock = stock + ?, remain = remain + ?, version = version + 1 " +
                "WHERE id = ? AND remain > 0 AND version = ?";
        return jdbcTemplate.update(sql, number, number, id, version) > 0;
    }

    public boolean incRemain(String id, int number, int version){
        final String sql = "UPDATE marketing_conf_second_kill SET remain = remain + ?, version = version + 1 " +
                "WHERE id = ? AND remain > 0 AND version = ?";
        return jdbcTemplate.update(sql, number, id, version) > 0;
    }

    public SecondKill findOne(String id){
        final String sql = "SELECT * FROM marketing_conf_second_kill WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public boolean delete(String id){
        final String sql= "UPDATE marketing_conf_second_kill SET is_delete = ?, update_time = ? WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, true, DaoUtils.timestamp(new Date()), id) > 0;
    }

    public long count(String shopId, String name, Boolean isOpen){
        StringBuilder sql = new StringBuilder(200);
        sql.append("SELECT COUNT(id) FROM marketing_conf_second_kill ");
        buildWhere(sql, shopId, name, isOpen);

        Object[] params = buildParams(shopId, name, isOpen);
        return jdbcTemplate.queryForObject(sql.toString(), params, Long.class);
    }

    private void buildWhere(StringBuilder sql, String shopId, String name, Boolean isOpen){
        sql.append(" WHERE 1 = 1 ");
        if(StringUtils.isNotBlank(shopId)){
            sql.append(" AND shop_id = ? ");
        }
        if(StringUtils.isNotBlank(name)){
            sql.append(" AND name LIKE ? ");
        }
        if(isOpen != null){
            sql.append(" AND is_open = ? ");
        }
    }

    private Object[] buildParams(String shopId, String name, Boolean isOpen){
        List<Object> params = new ArrayList<>(3);
        if(StringUtils.isNotBlank(shopId)){
            params.add(shopId);
        }
        if(StringUtils.isNotBlank(name)){
            params.add(DaoUtils.like(name));
        }
        if(isOpen != null){
            params.add(isOpen);
        }
        return params.toArray(new Object[0]);
    }

    public List<SecondKill> find(String shopId, String name, Boolean isOpen, int offset, int limit){
        StringBuilder sql = new StringBuilder(200);
        sql.append("SELECT * FROM marketing_conf_second_kill ");
        buildWhere(sql, shopId, name, isOpen);
        sql.append(" ORDER BY show_order ASC, update_time DESC LIMIT ? OFFSET ?");

        Object[] params = DaoUtils.appendOffsetLimit(buildParams(shopId, name, isOpen), offset, limit);
        return jdbcTemplate.query(sql.toString(), params, mapper);
    }
}
