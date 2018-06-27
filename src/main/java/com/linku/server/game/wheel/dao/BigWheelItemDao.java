package com.linku.server.game.wheel.dao;

import com.linku.server.common.utils.DaoUtils;
import com.linku.server.game.wheel.domain.BigWheelItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class BigWheelItemDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<BigWheelItem> mapper = (r, i) -> {
        BigWheelItem t = new BigWheelItem();

        t.setId(r.getString("id"));
        t.setBigWheelId(r.getString("big_wheel_id"));
        t.setIndex(r.getInt("item_index"));
        t.setFromCursor(r.getInt("from_cursor"));
        t.setToCursor(r.getInt("to_cursor"));
        t.setMoney(r.getInt("money"));
        t.setTitle(r.getString("title"));
        t.setRatio(r.getInt("ratio"));

        return t;
    };

    @Autowired
    public BigWheelItemDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(BigWheelItem t){
        final String sql = "INSERT INTO game_big_wheel_item (id, big_wheel_id, item_index, from_cursor, to_cursor, " +
                "title, money, ratio) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getBigWheelId(), t.getIndex(), t.getFromCursor(), t.getToCursor(),
                t.getTitle(), t.getMoney(), t.getRatio());
    }

    public void deleteOfBigWheel(String bigWheelId){
        final String sql = "DELETE FROM game_big_wheel_item WHERE big_wheel_id = ? ";
        jdbcTemplate.update(sql, bigWheelId);
    }

    public List<BigWheelItem> find(String bigWheelId){
        final String sql = "SELECT * FROM game_big_wheel_item WHERE big_wheel_id = ? ORDER BY item_index ASC";
        return jdbcTemplate.query(sql, new Object[]{bigWheelId}, mapper);
    }
}
