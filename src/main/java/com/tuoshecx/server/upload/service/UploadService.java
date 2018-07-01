package com.tuoshecx.server.upload.service;

import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.upload.dao.UploadInfoDao;
import com.tuoshecx.server.upload.domain.UploadInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 上传文件业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class UploadService {
    private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

    private final UploadInfoDao dao;

    @Autowired
    public UploadService(UploadInfoDao dao){
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String save(String url, String path, String userId, String userType){
        UploadInfo t = new UploadInfo();

        t.setId(IdGenerators.uuid());
        t.setUrl(url);
        t.setPath(path);
        t.setUserId(userId);
        t.setUserType(userType);

        dao.insert(t);

        return t.getPath();
    }
}
