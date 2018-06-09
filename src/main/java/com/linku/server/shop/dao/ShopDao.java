package com.linku.server.shop.dao;

import com.linku.server.common.utils.DaoUtils;
import com.linku.server.shop.domain.Shop;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 店铺数据操作
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class ShopDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Shop> mapper =(r, i) -> {
        Shop t = new Shop();

        t.setId(r.getString("id"));
        t.setName(r.getString("name"));
        t.setPhone(r.getString("phone"));
        t.setContact(r.getString("contact"));
        t.setProvince(r.getString("province"));
        t.setCity(r.getString("city"));
        t.setCounty(r.getString("county"));
        t.setAddress(r.getString("address"));
        t.setLocations(DaoUtils.toArray(r.getString("location")));
        t.setIcon(r.getString("icon"));
        t.setImages(DaoUtils.toArray(r.getString("images")));
        t.setSummary(r.getString("summary"));
        t.setDetail(r.getString("detail"));
        t.setOpenTime(r.getString("open_time"));
        t.setServices(DaoUtils.toArray(r.getString("services")));
        t.setState(Shop.State.valueOf(r.getString("state")));
        t.setTryUse(r.getBoolean("is_try"));
        t.setTryFromTime(r.getDate("try_from_time"));
        t.setTryToTime(r.getDate("try_to_time"));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public ShopDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Shop t){
        final String sql = "INSERT INTO shop_info " +
                "(id, name, phone, contact, province, city, county, address, location, icon, images, summary, detail, " +
                "open_time, services, state, is_try, try_from_time, try_to_time, update_time, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        final Date now = new Date();
        jdbcTemplate.update(sql, t.getId(), t.getName(), t.getPhone(), t.getContact(), t.getProvince(),
                t.getCity(), t.getCounty(), t.getAddress(), DaoUtils.join(t.getLocations()), t.getIcon(),
                DaoUtils.join(t.getImages()), StringUtils.defaultString(t.getSummary()),
                StringUtils.defaultString(t.getDetail()), t.getOpenTime(), DaoUtils.join(t.getServices()),
                t.getState().name(), t.getTryUse(), DaoUtils.date(t.getTryFromTime()), DaoUtils.date(t.getTryToTime()),
                DaoUtils.timestamp(now), DaoUtils.timestamp(now));
    }

    public boolean update(Shop t){
        final String sql = "UPDATE shop_info " +
                "SET name =?, phone =?, contact =?, province =?, city =?, county =?, address =?, location =?, " +
                "icon =?, images =?, summary = ?, detail =?, open_time =?, services =?, is_try =?, try_from_time=?, " +
                "try_to_time =?, update_time =? WHERE id =?";
        return jdbcTemplate.update(sql, t.getName(), t.getPhone(), t.getContact(), t.getProvince(), t.getCity(),
                t.getCounty(), t.getAddress(), DaoUtils.join(t.getLocations()), t.getIcon(), DaoUtils.join(t.getImages()),
                StringUtils.defaultString(t.getSummary()), StringUtils.defaultString(t.getDetail()), t.getOpenTime(),
                DaoUtils.join(t.getServices()), t.getTryUse(), DaoUtils.date(t.getTryFromTime()), DaoUtils.date(t.getTryToTime()),
                DaoUtils.timestamp(new Date()), t.getId()) > 0;
    }

    public Shop findOne(String id){
        final String sql = "SELECT * FROM shop_info WHERE id =? AND is_delete =false";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public boolean delete(String id){
        final String sql = "UPDATE shop_info SET is_delete =true, update_time =? WHERE id =?";
        return jdbcTemplate.update(sql, DaoUtils.timestamp(new Date()), id) > 0;
    }

    public boolean updateState(String id, Shop.State state){
        final String sql = "UPDATE shop_info SET state =?, update_time =? WHERE id =?";
        return jdbcTemplate.update(sql, state.name(), DaoUtils.timestamp(new Date()), id) > 0;
    }

    public long count(String name, String phone, String province, String city, String county, String address){
        final String sql = "SELECT COUNT(id) FROM shop_info " +
                "WHERE is_delete =false AND name LIKE ? AND phone LIKE ? AND province LIKE ? AND city LIKE ? AND county LIKE ? AND address LIKE ?";

        return jdbcTemplate.queryForObject(sql, buildParameters(name, phone, province, city, county, address), Long.class);
    }

    private Object[] buildParameters(String name, String phone, String province, String city, String county, String address){
        return new Object[]{
                DaoUtils.blankLike(name), DaoUtils.blankLike(phone), DaoUtils.blankLike(phone),
                DaoUtils.blankLike(city), DaoUtils.blankLike(county), DaoUtils.blankLike(address)};
    }

    public List<Shop> find(String name, String phone, String province, String city, String county, String address,
                            int offset, int limit){

        final String sql = "SELECT * FROM shop_info " +
                "WHERE is_delete =false AND name LIKE ? AND phone LIKE ? AND province LIKE ? AND city LIKE ? AND county LIKE ? AND address LIKE ? " +
                "ORDER BY create_time DESC LIMIT ? OFFSET ?";

        Object[] params = DaoUtils.appendOffsetLimit(
                buildParameters(name, phone, province, city, county, address), offset, limit);
        return jdbcTemplate.query(sql, params, mapper);
    }

    public List<Shop> findTryExpired(Date expiredTime, int limit){
        final String sql = "SELECT * FROM shop_info " +
                "WHERE is_delete =false AND state =? AND is_try =true AND try_to_time <? " +
                "ORDER BY create_time ASC LIMIT ?";

        return jdbcTemplate.query(sql, new Object[]{Shop.State.OPEN, DaoUtils.date(expiredTime), limit}, mapper);
    }
}

