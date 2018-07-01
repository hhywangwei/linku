package com.tuoshecx.server.upload.image;

/**
 * 压缩图片路径信息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class CompressInfo {
    private final String url;
    private final String path;

    public CompressInfo(String url, String path) {
        this.url = url;
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public String getPath() {
        return path;
    }
}
