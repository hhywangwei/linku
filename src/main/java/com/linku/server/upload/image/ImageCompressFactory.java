package com.linku.server.upload.image;

import com.linku.server.configure.properties.UploadProperties;

/**
 * 图片压缩Factory
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ImageCompressFactory {

    public static ImageCompress thumbnailator(UploadProperties properties){
        return new ThumbnailatorCompress(properties);
    }
}
