package com.linku.server.common.mime;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 简单JSON数据转换为Map,不支持嵌套和集合
 * 
 * @author WangWei
 */
public class SimpleJsonToMapConverter implements MimeConverter<String, Map<String, String>>{
	private final static Logger logger = LoggerFactory.getLogger(SimpleJsonToMapConverter.class);
	private final static ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public Map<String, String> convert(String i) {
		Map<String, String> map = new HashMap<>();
		
		try{
			Map<?, ?> m = mapper.readValue(i, Map.class);
			m.forEach((k, v) -> map.put(k.toString(), v.toString()));
		}catch(Exception e){
			logger.error("Json to mapper fail, error is {}", e.getMessage());
		}
		return map;
	}
	
}
