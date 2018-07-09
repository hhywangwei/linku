package com.tuoshecx.server.api.common;

import com.tuoshecx.server.api.manage.CredentialContextManageUtils;
import com.tuoshecx.server.api.common.vo.UploadVo;
import com.tuoshecx.server.api.vo.ResultVo;
import com.tuoshecx.server.security.Credential;
import com.tuoshecx.server.upload.service.UploadFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 上传文件
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/upload")
@Api(value = "/upload", tags = "P-上传文件API接口")
public class UploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private UploadFileService service;

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("上传文件")
    public ResultVo<UploadVo> upload(
            @RequestParam(value = "file") @ApiParam(value = "上传文件", required = true)  MultipartFile file,
            @RequestHeader(value = "x-thumb", defaultValue = "false") @ApiParam(value = "是否生产缩略图") boolean thumb,
            @RequestHeader(value = "x-max-size", defaultValue = "640") @ApiParam(value = "图片最大尺寸") int maxSize) {

        LOGGER.debug("Upload file is {}, content type {} length {}",
                file.getOriginalFilename(), file.getContentType(), file.getSize());

        if (file.isEmpty()) {
            return ResultVo.error(1, "上传文件为空");
        }

        maxSize = (maxSize <= 0) ? 640: maxSize;

        Credential credential = credential();
        String userId = credential.getId();
        String userType = credential.getType();
        String url = isSupportImage(file.getContentType())?
                service.transferImageTo(file, userId, userType, maxSize, thumb) :
                service.transferFileTo(file, userId, userType);

        return ResultVo.success(new UploadVo(url));
    }

    private boolean isSupportImage(String contentType) {
        String c = StringUtils.trim(StringUtils.lowerCase(contentType));
        return StringUtils.equals("image/jpeg", c) || StringUtils.equals("image/pjpeg", c)
                || StringUtils.equals("image/x-png", c) || StringUtils.equals("image/png", c)
                || StringUtils.equals("image/bmp", c) || StringUtils.equals("image/gif", c);
    }

    private Credential credential(){
        return CredentialContextManageUtils.getCredential();
    }
}
