package com.linku.server.goods.dao;

import com.linku.server.common.utils.DaoUtils;
import com.linku.server.goods.domain.Goods;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * 商品数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class GoodsDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Goods> mapper = (r, i) -> {
        Goods t = new Goods();

        t.setId(r.getString("id"));
        t.setShopId(r.getString("shop_id"));
        t.setName(r.getString("name"));
        t.setIcon(r.getString("icon"));
        t.setImages(DaoUtils.toArray(r.getString("images")));
        t.setSummary(r.getString("summary"));
        t.setDetail(r.getString("detail"));
        t.setPrice(r.getInt("price"));
        t.setRealPrice(r.getInt("real_price"));
        t.setDiscount(r.getInt("discount"));
        t.setCatalog(r.getString("catalog"));
        t.setTag(r.getString("tag"));
        t.setSell(r.getInt("sell"));
        t.setStock(r.getInt("stock"));
        t.setGroup(r.getBoolean("is_group"));
        t.setOpen(r.getBoolean("is_open"));
        t.setShowOrder(r.getInt("show_order"));
        t.setDelete(r.getBoolean("is_delete"));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public GoodsDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Goods t){
        final String sql = "INSERT INTO goods_info (id, shop_id, name, icon, images, summary, detail, price, real_price, " +
                "discount, catalog, tag, sell, stock, is_group, is_open, show_order, update_time, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        final Date now = new Date();
        jdbcTemplate.update(sql, t.getId(), t.getShopId(), t.getName(), t.getIcon(), DaoUtils.join(t.getImages()),
                StringUtils.defaultString(t.getSummary()), StringUtils.defaultString(t.getDetail()),
                t.getPrice(), t.getRealPrice(), t.getDiscount(), t.getCatalog(), t.getTag(), t.getSell(), t.getStock(),
                t.getGroup(), t.getOpen(), t.getShowOrder(), DaoUtils.timestamp(now), DaoUtils.timestamp(now));
    }

    public boolean update(Goods t){
        final String sql = "UPDATE goods_info SET name = ?, icon = ?, images = ?, summary = ?, detail = ?, price = ?, " +
                "real_price = ?, discount = ?, catalog = ?, tag = ?, is_group = ?, show_order = ?, update_time = ? " +
                "WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, t.getName(), t.getIcon(), DaoUtils.join(t.getImages()),
                StringUtils.defaultString(t.getSummary()), StringUtils.defaultString(t.getDetail()),
                t.getPrice(), t.getRealPrice(), t.getDiscount(), t.getCatalog(), t.getTag(),
                t.getGroup(), t.getShowOrder(), DaoUtils.timestamp(new Date()), t.getId()) > 0;
    }

    public boolean incSell(String id, int inc){
        final String sql = "UPDATE goods_info SET sell = sell + ? WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, inc, id) > 0;
    }

    public boolean incStock(String id, int inc){
        final String sql = "UPDATE goods_info SET stock = stock + ? WHERE id = ? AND (stock + ?) > 0 AND is_delete = false";
        return jdbcTemplate.update(sql, inc, id, inc) > 0;
    }

    public boolean updateOpen(String id, boolean isOpen){
        final String sql = "UPDATE goods_info SET is_open = ?, update_time = ? WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, isOpen, DaoUtils.timestamp(new Date()), id) > 0;
    }

    public boolean delete(String id){
        final String sql = "UPDATE goods_info SET is_delete = true WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public Goods findOne(String id){
        final String sql = "SELECT * FROM goods_info WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public long count(String shopId, String catalog, String name, Boolean isOpen){
        String sql = "SELECT COUNT(id) FROM goods_info WHERE shop_id = ? AND catalog LIKE ? AND name LIKE ?";
        if(isOpen != null){
            sql = sql + "AND is_open = ?";
        }
        final Object[] params = buildParams(shopId, catalog, name, isOpen);
        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    private Object[] buildParams(String shopId, String catalog, String name, Boolean isOpen){
        final String catalogLike = DaoUtils.like(catalog);
        final String nameLike = DaoUtils.like(name);
        return isOpen != null?
                new Object[]{shopId, catalogLike, nameLike, isOpen}: new Object[]{shopId, catalogLike, nameLike};
    }

    public List<Goods> find(String shopId, String catalog, String name, Boolean isOpen, int offset, int limit){
        String sql = "SELECT * FROM goods_info WHERE shop_id = ? AND catalog LIKE ? AND name LIKE ? ";
        if(isOpen != null){
            sql = sql + " AND is_open = ? ";
        }
        sql = sql + " ORDER BY show_order ASC, update_time DESC LIMIT ? OFFSET ?";

        final Object[] params = DaoUtils.appendOffsetLimit(buildParams(shopId, catalog, name, isOpen), offset, limit);
        return jdbcTemplate.query(sql, params, mapper);
    }
}
