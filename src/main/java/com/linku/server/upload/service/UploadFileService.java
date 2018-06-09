package com.linku.server.upload.service;

import com.linku.server.BaseException;
import com.linku.server.configure.properties.UploadProperties;
import com.linku.server.upload.image.CompressInfo;
import com.linku.server.upload.image.ImageCompress;
import com.linku.server.upload.image.ImageCompressFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * 上传文件业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
public class UploadFileService {
    private static final Logger logger = LoggerFactory.getLogger(UploadFileService.class);

    private final UploadProperties properties;
    private final UploadService service;
    private final ImageCompress imageCompress;

    @Autowired
    public UploadFileService(UploadProperties properties, UploadService service) {
        this.properties = properties;
        this.service = service;
        this.imageCompress = ImageCompressFactory.thumbnailator(properties);
    }

    public String transferFileTo(MultipartFile file, String userId, String userType){

        String path = DateFilename.hourRandomFilePath(file.getOriginalFilename());
        try{
            file.transferTo(new File(String.format("%s/%s",properties.getRoot(), path)));
            String url = String.format("%s/%s", properties.getBaseUrl(), path);
            service.save(url, path, userId, userType);
            return url;
        }catch(Exception e){
            logger.error("Upload file {} fail, error is {}", path, e.getMessage());
            throw new BaseException(20, "上传文件失败");
        }
    }

    public String transferImageTo(MultipartFile file, String userId, String userType, int maxWidth, boolean thumb){
        try(InputStream in = file.getInputStream()){
            Optional<CompressInfo> optional = imageCompress.compress(
                    in, file.getOriginalFilename(), maxWidth, thumb);
            CompressInfo t = optional.orElseThrow(() -> new BaseException(20, "上传文件失败"));
            service.save(t.getUrl(), t.getPath(), userId, userType);
            return t.getUrl();
        }catch (IOException e){
            logger.error("Upload image file {} fail, error is {}", file.getOriginalFilename(), e.getMessage());
            throw new BaseException(20, "上传文件失败");
        }
    }
}
