package com.tuoshecx.server.configure.properties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 本地上传文件配置信息
 * 
 * @author WangWei
 */
@ConfigurationProperties(prefix="upload")
public class UploadProperties {
	
	private int maxDay = 3;
	private String root;
	private String baseUrl;
	private Integer maxImageLength = 700 * 1024;
	private Integer maxWidth = 1024;
	private Integer thumbWidth = 256;
	private String thumbPrefix = "_thumb_";
	private String orgiPrefix = "_orgi_";
	
	public int getMaxDay() {
		return maxDay;
	}
	public void setMaxDay(int maxDay) {
		this.maxDay = maxDay;
	}
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
	public String getBaseUrl() {
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	} 
	public Integer getMaxImageLength() {
		return maxImageLength;
	}
	public void setMaxImageLength(Integer maxImageLength) {
		this.maxImageLength = maxImageLength;
	}
	public Integer getMaxWidth() {
		return maxWidth;
	}
	public void setMaxWidth(Integer maxWidth) {
		this.maxWidth = maxWidth;
	}
	public Integer getThumbWidth() {
		return thumbWidth;
	}
	public void setThumbWidth(Integer thumbWidth) {
		this.thumbWidth = thumbWidth;
	}
	public String getThumbPrefix() {
		return thumbPrefix;
	}
	public void setThumbPrefix(String thumbPrefix) {
		this.thumbPrefix = thumbPrefix;
	}
	public String getOrgiPrefix() {
		return orgiPrefix;
	}
	public void setOrgiPrefix(String orgiPrefix) {
		this.orgiPrefix = orgiPrefix;
	}

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("maxDay", maxDay)
                .append("root", root)
                .append("baseUrl", baseUrl)
                .append("maxImageLength", maxImageLength)
                .append("maxWidth", maxWidth)
                .append("thumbWidth", thumbWidth)
                .append("thumbPrefix", thumbPrefix)
                .append("orgiPrefix", orgiPrefix)
                .toString();
    }
}
