package com.tuoshecx.server.game.play.dao;

import com.tuoshecx.server.common.utils.DaoUtils;
import com.tuoshecx.server.game.play.domain.Play;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

/**
 * 游戏参与数据操作
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class PlayDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Play> mapper = (r, i) -> {
        Play t = new Play();

        t.setId(r.getString("id"));
        t.setGameId(r.getString("game_id"));
        t.setGameName(r.getString("game_name"));
        t.setUserId(r.getString("user_id"));
        t.setHeadImg(r.getString("head_img"));
        t.setName(r.getString("name"));
        t.setPrize(r.getString("prize"));
        t.setMoney(r.getInt("money"));
        t.setPticketId(r.getString("pticket_id"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public PlayDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Play t){
        final String sql = "INSERT INTO game_play (id, game_id, game_name, user_id, name, head_img, prize, money, " +
                "pticket_id, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, t.getId(), t.getGameId(), t.getGameName(), t.getUserId(), t.getName(), t.getHeadImg(),
                t.getPrize(), t.getMoney(), t.getPticketId(), DaoUtils.timestamp(new Date()));
    }

    public Play findOne(String id){
        final String sql = "SELECT * FROM game_play WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public long count(String gameId, String prize){
        final String sql = "SELECT COUNT(id) FROM game_play WHERE game_id = ? AND prize LIKE ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{gameId, DaoUtils.like(prize)}, Long.class);
    }

    public List<Play> find(String gameId, String prize, int offset, int limit){
        final String sql = "SELECT * FROM game_play WHERE game_id ? AND prize LIKE ? LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new Object[]{gameId, DaoUtils.like(prize), limit, offset}, mapper);
    }

    public Map<String, Integer> groupPrize(String gameId) {
        final String sql = "SELECT prize, COUNT(id) count FROM game_play WHERE game_id = ? ORDER BY money DESC";
        Map<String, Integer> data = new LinkedHashMap<>();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, gameId);

        if (rowSet.isLast()) {
            return data;
        }

        do {
            data.put(rowSet.getString("prize"), rowSet.getInt("count"));
        } while (rowSet.next());

        return data;
    }
}
