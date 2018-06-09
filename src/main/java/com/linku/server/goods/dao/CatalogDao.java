package com.linku.server.goods.dao;

import com.linku.server.common.utils.DaoUtils;
import com.linku.server.goods.domain.Catalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * 商品分类数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class CatalogDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Catalog> mapper = (r, i) ->{
        Catalog t = new Catalog();

        t.setId(r.getString("id"));
        t.setShopId(r.getString("shop_id"));
        t.setName(r.getString("name"));
        t.setIcon(r.getString("icon"));
        t.setShowOrder(r.getInt("show_order"));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public CatalogDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Catalog t){
        final String sql = "INSERT INTO goods_catalog " +
                "(id, shop_id, name, icon, show_order, update_time, create_time) VALUES (?, ?, ?, ?, ?, ?, ?)";

        final Date now = new Date();
        jdbcTemplate.update(sql, t.getId(), t.getShopId(), t.getName(), t.getIcon(),
                t.getShowOrder(), DaoUtils.timestamp(now), DaoUtils.timestamp(now));
    }

    public boolean update(Catalog t){
        final String sql = "UPDATE goods_catalog SET name = ?, icon = ?, show_order = ?, update_time = ? WHERE id = ?";
        return jdbcTemplate.update(sql, t.getName(), t.getIcon(),
                t.getShowOrder(), DaoUtils.timestamp(new Date()), t.getId()) > 0;
    }

    public Catalog findOne(String id){
        final String sql = "SELECT * FROM goods_catalog WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public boolean delete(String id){
        final String sql= "DELETE FROM goods_catalog WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public long count(String shopId, String name){
        final String sql = "SELECT COUNT(id) FROM goods_catalog WHERE shop_id = ? AND name LIKE ?";
        final String nameLike = DaoUtils.like(name);
        return jdbcTemplate.queryForObject(sql, new Object[]{shopId, nameLike}, Long.class);
    }

    public List<Catalog> find(String shopId, String name, int offset, int limit){
        final String sql = "SELECT * FROM goods_catalog WHERE shop_id = ? AND name LIKE ? " +
                "ORDER BY show_order, create_time ASC LIMIT ? OFFSET ?";
        final String nameLike = DaoUtils.like(name);
        return jdbcTemplate.query(sql, new Object[]{shopId, nameLike, limit, offset}, mapper);
    }
}
