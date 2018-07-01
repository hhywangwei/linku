package com.tuoshecx.server.marketing.dao;

import com.tuoshecx.server.common.utils.DaoUtils;
import com.tuoshecx.server.marketing.domain.SharePresent;
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
 * 多人行活动设置数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class SharePresentDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<SharePresent> mapper = (r, i) -> {
        SharePresent t = new SharePresent();

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
        t.setPerson(r.getInt("person"));
        t.setFirst(r.getBoolean("is_first"));
        t.setDays(r.getInt("days"));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public SharePresentDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(SharePresent t){
        final String sql = "INSERT INTO marketing_conf_present (id, shop_id, goods_id, name, icon, images, summary, detail, " +
                "help, price, start_time, end_time, is_open, show_order, person, is_first, days, update_time, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        final Date now = new Date();
        jdbcTemplate.update(sql, t.getId(), t.getShopId(), t.getGoodsId(), t.getName(), t.getIcon(), DaoUtils.join(t.getImages()),
                StringUtils.defaultString(t.getSummary()), StringUtils.defaultString(t.getDetail()),
                StringUtils.defaultString(t.getHelp()), t.getPrice(), DaoUtils.date(t.getStartTime()),
                DaoUtils.date(t.getEndTime()), t.getOpen(), t.getShowOrder(), t.getPerson(), t.getFirst(),
                t.getDays(), DaoUtils.timestamp(now), DaoUtils.timestamp(now));
    }

    public boolean update(SharePresent t){
        final String sql = "UPDATE marketing_conf_present SET goods_id = ?, name = ?, icon = ?, images = ?, summary = ?, " +
                "detail = ?, help = ?, price = ?, start_time = ?, end_time = ?, is_open = ?, show_order = ?, " +
                " person = ?, is_first = ?, days = ?, update_time = ? WHERE id = ?";

        return jdbcTemplate.update(sql, t.getGoodsId(), t.getName(), t.getIcon(), DaoUtils.join(t.getImages()),
                StringUtils.defaultString(t.getSummary()), StringUtils.defaultString(t.getDetail()),
                StringUtils.defaultString(t.getHelp()), t.getPrice(), DaoUtils.date(t.getStartTime()),
                DaoUtils.date(t.getEndTime()), t.getOpen(), t.getShowOrder(), t.getPerson(), t.getFirst(),
                t.getDays(), DaoUtils.timestamp(new Date()), t.getId()) > 0;
    }

    public boolean updateOpen(String id, boolean isOpen){
        final String sql = "UPDATE marketing_conf_present SET is_open = ?, update_time = ? WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, isOpen, DaoUtils.timestamp(new Date()), id) > 0;
    }

    public SharePresent findOne(String id){
        final String sql = "SELECT * FROM marketing_conf_present WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public boolean delete(String id){
        final String sql= "UPDATE marketing_conf_present SET is_delete = ?, update_time = ? WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, true, DaoUtils.timestamp(new Date()), id) > 0;
    }

    public long count(String shopId, String name, Boolean isOpen){
        StringBuilder sql = new StringBuilder(200);
        sql.append("SELECT COUNT(id) FROM marketing_conf_present ");
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

    public List<SharePresent> find(String shopId, String name, Boolean isOpen, int offset, int limit){
        StringBuilder sql = new StringBuilder(200);
        sql.append("SELECT * FROM marketing_conf_present ");
        buildWhere(sql, shopId, name, isOpen);
        sql.append(" ORDER BY show_order ASC, update_time DESC LIMIT ? OFFSET ?");

        Object[] params = DaoUtils.appendOffsetLimit(buildParams(shopId, name, isOpen), offset, limit);
        return jdbcTemplate.query(sql.toString(), params, mapper);
    }

}
