package com.tuoshecx.server.common.mime;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
 * XML 转换成Map
 * 
 * @author WangWei
 */
class XmlToMapConverter implements MimeConverter<String, Map<String, Object>> {
	private static final Logger logger = LoggerFactory.getLogger(XmlToMapConverter.class);
	private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	
	/**
	 * 忽略根节点转换
	 */
	private boolean ignoreRoot;
	
	/**
	 * 忽略null值转换
	 */
	private boolean ignoreNull;

	/**
	 * 实例化{@link XmlToMapConverter}
	 * 
	 * 解析转换忽略XML文档根元素和null值
	 */
	public XmlToMapConverter() {
		this(true, true);
	}
	
	/**
	 * 实例化{@link XmlToMapConverter}
	 * 
	 * @param ignoreRoot true:忽略根元素
	 * @param ignoreNull true:忽略空值
	 */
	public XmlToMapConverter(boolean ignoreRoot, boolean ignoreNull){
		this.ignoreRoot = ignoreRoot;
		this.ignoreNull = ignoreNull;
	}

	@Override
	public Map<String, Object> convert(String i) {
		Map<String, Object> root = new HashMap<String, Object>();
		try{
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(i)));
			if(document.hasChildNodes()){
				NodeList nodes = document.getChildNodes();
				if(ignoreRoot){
					nodes = nodes.item(0).getChildNodes(); 
				}
				if(nodes.getLength() > 0){
					insertElement(root, nodes);		
				}
			}
			return root;
		}catch(Exception e){
			logger.error("Parse {} fail, error is {}.", i, e.getMessage());
		}
		return root;
	}
	
	private void insertElement(Map<String, Object> o, NodeList nodes)throws SAXException, IOException{
		for(int i = 0 ; i < nodes.getLength(); i++){
			Node node = nodes.item(i);
			if(node.getNodeType() != Node.ELEMENT_NODE){
				 continue;
			}
			if(simpleElement(node)){
				 putTextValue(o, node);
			 }else{
				 putObjectValue(o, node);
			 }
		}
	}
	
	private boolean simpleElement(Node node){
		return !hasAttributes(node) && !hasChildNodes(node);
	}
	
	private boolean hasAttributes(Node node){
		return node.hasAttributes();
	}
	
	private boolean hasChildNodes(Node node){
		int len = node.getChildNodes().getLength();
		boolean has = false;
		for(int i = 0; i < len; i++){
			Node child = node.getChildNodes().item(i);
			if(child.getNodeType() == Node.ELEMENT_NODE){
				has = true;
				break;
			}
		}
		return has;
	}
	
	private void putTextValue(Map<String, Object> o, Node node){
		String name = node.getNodeName();
		String value = node.hasChildNodes() ? node.getChildNodes().item(0).getNodeValue() : null;
		putValue(o, name, value);
	}
	
	private void putObjectValue(Map<String, Object> o, Node node) throws SAXException, IOException{
		 Map<String, Object> c = new HashMap<String, Object>();
		 putValue(o, node.getNodeName(), c);
		 
		 if(hasAttributes(node)){
			 int len = node.getAttributes().getLength();
			 for(int i = 0 ; i < len ; i++){
				 Node n = node.getAttributes().item(i);
				 putValue(c, n.getNodeName(), n.getNodeValue());
			 }
		 }
		 
		 if(hasChildNodes(node)){
			 insertElement(c, node.getChildNodes());
		 }
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void putValue(Map<String, Object> o, String name, Object value){
		
		if(value == null){
			if(ignoreNull){
				return ;
			}
			value = "";
		}
		
		if(o.containsKey(name)){
			Object v = o.get(name);
			if(v instanceof List){
				((List)v).add(value);
			}else{
				List<String> array = new ArrayList<String>();
				array.add((String)v);
				array.add((String) value);
				o.put(name, array);
			}
		}else{
			o.put(name, value);
		}
	}
}
