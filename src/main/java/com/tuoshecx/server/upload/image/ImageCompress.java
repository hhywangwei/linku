package com.tuoshecx.server.upload.image;

import java.io.InputStream;
import java.util.Optional;

/**
 * 图片压缩处理
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public interface ImageCompress {

    /**
     * 压缩图片
     *
     * @param in 图片输入流
     * @param filename 文件名
     * @param maxWidth 图片最大宽度
     * @param thumb 生成缩略图
     * @return 图片访问地址
     */
    Optional<CompressInfo> compress(InputStream in , String filename, int maxWidth, boolean thumb);

}
