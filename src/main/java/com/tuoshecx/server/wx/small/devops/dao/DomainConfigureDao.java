package com.tuoshecx.server.wx.small.devops.dao;

import com.tuoshecx.server.common.utils.DaoUtils;
import com.tuoshecx.server.wx.small.devops.domain.DomainConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * 微信小程序服务域名设置数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class DomainConfigureDao {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<DomainConfigure> mapper = (r, i) -> {
        DomainConfigure t = new DomainConfigure();

        t.setId(r.getString("id"));
        t.setRequestDomain(DaoUtils.toArray(r.getString("request_domain")));
        t.setWsrequestDomain(DaoUtils.toArray(r.getString("wsrequest_domain")));
        t.setUploadDomain(DaoUtils.toArray(r.getString("upload_domain")));
        t.setDownloadDomain(DaoUtils.toArray(r.getString("download_domain")));
        t.setWebViewDomain(DaoUtils.toArray(r.getString("web_view_domain")));

        return t;
    };

    @Autowired
    public DomainConfigureDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public boolean update(DomainConfigure t){
        final String sql = "UPDATE wx_small_domain SET request_domain = ?, wsrequest_domain = ?, upload_domain = ?, download_domain = ?, " +
                "web_view_domain = ? WHERE id = ?";
        return jdbcTemplate.update(sql, DaoUtils.join(t.getRequestDomain()), DaoUtils.join(t.getWsrequestDomain()),
                DaoUtils.join(t.getUploadDomain()), DaoUtils.join(t.getDownloadDomain()), DaoUtils.join(t.getWebViewDomain()),
                t.getId()) > 0;
    }

    public DomainConfigure findOne(String id){
        final String sql = "SELECT * FROM wx_small_domain WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

}
