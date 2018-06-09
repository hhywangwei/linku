package com.linku.server.upload.image;

import com.linku.server.configure.properties.UploadProperties;
import com.linku.server.upload.service.DateFilename;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import javax.imageio.ImageIO;

/**
 *  通过thumbnailator压缩图片
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class ThumbnailatorCompress implements ImageCompress {
    private static final Logger logger = LoggerFactory.getLogger(ThumbnailatorCompress.class);
    private static final int NONE_COMPRESS_RATIO = 1;

    private final UploadProperties properties;

    ThumbnailatorCompress(UploadProperties properties) {
        this.properties = properties;
    }

    @Override
    public Optional<CompressInfo> compress(InputStream in, String filename, int maxWidth, boolean thumb) {
        try{
            BufferedImage bi = ImageIO.read(in);

            String path = DateFilename.hourRandomFilePath(filename);
            int ratio = compressRatio(bi, maxWidth);

            if(ratio != NONE_COMPRESS_RATIO){
                write(bi, 1, path, properties.getOrgiPrefix());
            }

            String asbPath = write(bi, ratio, path, "");

            if(thumb){
                ratio = compressRatio(bi, 64);
                write(bi, ratio, path, properties.getThumbPrefix());
            }
            String url = String.format("%s/%s", properties.getBaseUrl(), path);

            return Optional.of(new CompressInfo(url, asbPath));
        }catch (IOException e){
            logger.error("Read image fail, error is {}", e.getMessage());
        }
        return Optional.empty();
    }

    private int compressRatio(BufferedImage bi, int maxWidth){
        if(bi.getWidth() < maxWidth && bi.getHeight() < maxWidth){
            return NONE_COMPRESS_RATIO;
        }
        return bi.getHeight() > bi.getWidth() ?
                bi.getHeight() / maxWidth : bi.getWidth() / maxWidth;
    }

    private String write(BufferedImage bi, int ratio, String path, String perfix)throws IOException{

        String absPath = String.format("%s/%s", properties.getRoot(), appendPerfix(path, perfix));
        int width = bi.getWidth() / ratio;
        int height = bi.getHeight()/ ratio;

        Thumbnails.of(bi)
                .width(width)
                .height(height)
                .toFile(absPath);

        return absPath;
    }

    private String appendPerfix(String path , String perfix){
        if(StringUtils.isBlank(perfix)){
            return path;
        }
        int index = StringUtils.lastIndexOf(path, ".");
        return index == -1 ? String.format("%s%s", path, perfix) :
                String.format("%s%s.%s", StringUtils.left(path, index), perfix, StringUtils.substring(path, index + 1));
    }


}
