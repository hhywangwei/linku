package com.linku.server.goods.dao;

import com.linku.server.common.utils.DaoUtils;
import com.linku.server.goods.domain.GroupItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * 组合商品
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class GroupItemDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<GroupItem> mapper = (r, i) -> {
        GroupItem t = new GroupItem();

        t.setId(r.getString("id"));
        t.setGoodsId(r.getString("goods_id"));
        t.setItemGoodsId(r.getString("item_goods_id"));
        t.setName(r.getString("name"));
        t.setIcon(r.getString("icon"));
        t.setCount(r.getInt("count"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public GroupItemDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(GroupItem t){
        final String sql = "INSERT INTO goods_group " +
                "(id, goods_id, item_goods_id, name, icon, count, create_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getGoodsId(), t.getItemGoodsId(), t.getName(),
                t.getIcon(), t.getCount(), DaoUtils.timestamp(new Date()));
    }

    public boolean update(GroupItem t){
        final String sql = "UPDATE goods_group SET item_goods_id = ?, name = ?, icon = ?, count = ? WHERE id = ?";
        return jdbcTemplate.update(sql, t.getItemGoodsId(), t.getName(), t.getIcon(), t.getCount(), t.getId()) > 0;
    }

    public GroupItem findOne(String id){
        final String sql = "SELECT * FROM goods_group WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM goods_group WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public void updateItemGoods(String goodsId, String name, String icon){
        final String sql = "UPDATE goods_group SET name = ?, icon = ? WHERE item_goods_id = ?";
        jdbcTemplate.update(sql, goodsId, name, icon);
    }

    public void deleteByGoodsId(String goodsId){
        final String sql = "DELETE FROM goods_group WHERE goods_id = ?";
        jdbcTemplate.update(sql, goodsId);
    }

    public List<GroupItem> findByGoodsId(String goodsId){
        final String sql = "SELECT * FROM goods_group WHERE goods_id = ?";
        return jdbcTemplate.query(sql, new Object[]{goodsId}, mapper);
    }

    public boolean hasItem(String itemGoodsId){
        final String sql = "SELECT COUNT(id) FROM goods_group WHERE item_goods_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{itemGoodsId}, Integer.class) > 0;
    }

    public List<String> findGoodsIdByItemGoodsId(String itemGoodsId){
        final String sql = "SELECT goods_id FROM goods_group WHERE item_goods_id = ?";
        return jdbcTemplate.queryForList(sql, new Object[]{itemGoodsId}, String.class);
    }
}
