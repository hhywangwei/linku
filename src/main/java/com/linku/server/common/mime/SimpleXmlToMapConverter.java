package com.linku.server.common.mime;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 解析XML到map对象中，只支持一级简单结构转换，不支持子对象和集合处理
 * 
 * @author WangWei
 */
class SimpleXmlToMapConverter implements MimeConverter<String, Map<String, String>> {
	private static final Logger logger = LoggerFactory.getLogger(SimpleXmlToMapConverter.class);
	private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	
	/**
	 * 忽略null值转换
	 */
	private final boolean ignoreNull;
	
	
	public SimpleXmlToMapConverter(boolean ignoreNull) {
		this.ignoreNull = ignoreNull;
	}
	
	@Override
	public Map<String, String> convert(String i) {
		Map<String, String> root = new HashMap<String, String>();
		try{
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(i)));
			if(document.hasChildNodes()){
				NodeList nodes = document.getChildNodes().item(0).getChildNodes();
				insertElement(root, nodes);		
			}
			return root;
		}catch(Exception e){
			logger.error("Parse {} fail, error is {}.", i, e.getMessage());
		}
		return root;
	}
	
	private void insertElement(Map<String, String> params, NodeList nodes)throws SAXException, IOException{
		for(int i = 0 ; i < nodes.getLength(); i++){
			Node node = nodes.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE){
				putTextValue(params, node);
			}
		}
	}
	
	private void putTextValue(Map<String, String> params, Node node){
		String name = node.getNodeName();
		String value = node.getTextContent();
		if(ignoreNull && value == null){
			return ;
		}
		logger.debug("Node {} value is {}", name, value);
		params.put(name, value);
	}
}
