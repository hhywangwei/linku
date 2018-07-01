package com.tuoshecx.server.upload.service;

import com.tuoshecx.server.common.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 按照日期生成文件目录和文件路径工具类
 * 
 * @author WangWei
 */
public class DateFilename {
	private static final Logger logger = LoggerFactory.getLogger(DateFilename.class);
	private static final String HOUR_FILE_PATH_PATTER = "yyyyMMdd"+File.separator+"HH";
	private static final String EMPTY_FILE_EXT = "";
	
	/**
	 * 按照小时生成的上传文件目录
	 * 
	 * @param start 开始时间
	 * @param day 开始时间天数后目录
	 * @return
	 */
	public static List<String> hourDirs(Date start, int day){
		DateFormat dateFormat = new SimpleDateFormat(HOUR_FILE_PATH_PATTER);
		List<String> dirs = new ArrayList<String>(24 * day);
		Calendar calendar = Calendar.getInstance();
		for(int i = 0 ; i < 24 * day; i++){
			calendar.setTime(start);
			calendar.add(Calendar.HOUR, i);
			String dir = dateFormat.format(calendar.getTime());
			dirs.add(dir);
			logger.debug("Hour random dir is {}",dir);
		}
		return dirs;
	}
	
	/**
	 * 按照小时目录结构生成随机文件保存路径
	 * 
	 * @param filename 文件名
	 * @return
	 */
	public static String hourRandomFilePath(String filename){
		DateFormat dateFormat = new SimpleDateFormat(HOUR_FILE_PATH_PATTER);
		String dir = dateFormat.format(new Date());
		String newFilename = SecurityUtils.randomStr(32);
		String ext = getFileExt(filename);
		if(StringUtils.isNotBlank(ext)){
			newFilename = newFilename + "." + ext;
		}
		String path = dir + File.separator + newFilename;
		logger.debug("Hour random file path is {}",path);
		
		return path;
	}
	
	/**
	 * 得到文件后缀名
	 * 
	 * @param filename 文件名
	 * @return
	 */
	public static String getFileExt(String filename){
		if(StringUtils.isBlank(filename)){
			return EMPTY_FILE_EXT;
		}else{
			int index = StringUtils.lastIndexOf(filename,  ".");
			if(index == -1){
				return EMPTY_FILE_EXT;
			}
			return StringUtils.lowerCase(StringUtils.substring(filename, index+1));
		}
	}
}
