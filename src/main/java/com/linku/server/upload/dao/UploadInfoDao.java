package com.linku.server.upload.dao;

import com.linku.server.common.utils.DaoUtils;
import com.linku.server.upload.domain.UploadInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;

/**
 * 上传文件数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class UploadInfoDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UploadInfoDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(UploadInfo t){
        final String sql = "INSERT INTO base_upload (id, user_id, user_type, url, path, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getId(), t.getUserId(), t.getUserType(), t.getUrl(), t.getPath(), DaoUtils.timestamp(new Date()));
    }
}
