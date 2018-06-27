package com.linku.server.common.mime;

import java.util.Map;

/**
 * 媒体类型转换
 * 
 * @author WangWei
 */
public class MimeConverters {

	/**
	 * XML转换成Map
	 * 
	 * @param xml XML内容
	 * @return
	 */
	public static Map<String, Object> xmlToMap(String xml){
		return xmlToMap(xml, true, true);
	}
	
	/**
	 * XML转换成Map
	 *
	 * @param xml        XML内容
	 * @param ignoreRoot 忽略根节点解析
	 * @param ignoreNull 忽略空节点解析
	 * @return
	 */
	public static  Map<String, Object> xmlToMap(String xml, boolean ignoreRoot, boolean ignoreNull){
		MimeConverter<String,  Map<String, Object>> converter = new XmlToMapConverter(ignoreRoot, ignoreNull);
		return converter.convert(xml);
	}

	/**
	 * Simple XML转换成Map
	 * 
	 * @param xml XML内容
	 * @return
	 */
	public static Map<String, String> simpleXmlToMap(String xml){
		return simpleXmlToMap(xml, true);
	}
	
	/**
	 * Simple XML转换成Map
	 *
	 * @param xml        XML内容
	 * @param ignoreNull 忽略空节点解析
	 * @return
	 */
	public static  Map<String, String> simpleXmlToMap(String xml, boolean ignoreNull){
		MimeConverter<String,  Map<String, String>> converter = new SimpleXmlToMapConverter(ignoreNull);
		return converter.convert(xml);
	}
	
	/**
	 * Simple JSON转换成Map
	 * 
	 * @param json JSON内容
	 * @return
	 */
	public static Map<String, String> simpleJsonToMap(String json){
		MimeConverter<String,  Map<String, String>> converter = new SimpleJsonToMapConverter();
		return converter.convert(json);
	}

}
