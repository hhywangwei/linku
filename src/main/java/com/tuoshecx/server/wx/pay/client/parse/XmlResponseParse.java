package com.tuoshecx.server.wx.pay.client.parse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 解析XML输出
 *
 * @author WangWei
 */
 class XmlResponseParse implements ResponseParse {
    private static final Logger logger = LoggerFactory.getLogger(XmlResponseParse.class);

    private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    /**
     * 忽略null值转换
     */
    private final boolean ignoreNull;

    /**
     * 实例化微信输出xml解析,忽略null值
     */
    XmlResponseParse(){
        this(true);
    }

    /**
     * 实例化微信xml解析
     *
     * @param ignoreNull true:忽略Null值
     */
    XmlResponseParse(boolean ignoreNull) {
        this.ignoreNull = ignoreNull;
    }

    @Override
    public <T> T parse(String body, Function<Map<String, String>, T> func) throws ResponseParseException {
        try{
            return func.apply(toMap(body));
        }catch (Exception e){
            throw new ResponseParseException(e);
        }
    }

    private Map<String, String> toMap(String body)throws Exception{
        Map<String, String> data = new LinkedHashMap<>(10, 1);
        try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(body)));
            if(document.hasChildNodes()){
                NodeList nodes = document.getChildNodes().item(0).getChildNodes();
                insertElement(data, nodes);
            }
            return data;
        }catch(Exception e){
            logger.error("Parse {} fail, error is {}.", body, e.getMessage());
            throw e;
        }
    }

    private void insertElement(Map<String, String> params, NodeList nodes)throws SAXException, IOException {
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
