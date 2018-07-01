package com.tuoshecx.server.upload.cron;

import com.tuoshecx.server.configure.properties.UploadProperties;
import com.tuoshecx.server.upload.service.DateFilename;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

/**
 * 创建上传目录定时任务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
public class MkdirUploadDirCorn {
    private static final Logger logger = LoggerFactory.getLogger(MkdirUploadDirCorn.class);

    private final UploadProperties properties;

    @Autowired
    public MkdirUploadDirCorn(UploadProperties properties){
        this.properties  = properties;
        cronMkdir();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void cronMkdir(){
        mkdirs(new Date(), properties);
    }

    private void mkdirs(Date date, UploadProperties properties){
        List<String> dirs = DateFilename.hourDirs(date, properties.getMaxDay());
        dirs.stream().map(p -> String.format("%s/%s", properties.getRoot(), p))
                .filter(p -> Files.notExists(Paths.get(p)))
                .forEach(this::mkdir);
    }

    private void mkdir(String p){
        try{
            Files.createDirectories(Paths.get(p));
            logger.debug("Create {} is success", p);
        }catch(Exception e){
            logger.error("Create {} is fail, error is {}", p, e.toString());
        }
    }
}
