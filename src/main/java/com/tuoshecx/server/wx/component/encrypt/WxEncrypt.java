package com.tuoshecx.server.wx.component.encrypt;

import java.io.StringReader;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.tuoshecx.server.common.utils.SecurityUtils;
import com.tuoshecx.server.wx.component.encrypt.aes.AESEncoder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 * 提供接收和推送给公众平台消息的加解密接口(UTF8编码的字符串).
 * <ol>
 * 	<li>第三方回复加密消息给公众平台</li>
 * 	<li>第三方收到公众平台发送的消息，验证消息的安全性，并对消息进行解密。</li>
 * </ol>
 * 说明：异常java.security.InvalidKeyException:illegal Key Size的解决方案
 * <ol>
 * 	<li>在官方网站下载JCE无限制权限策略文件（JDK7的下载地址：
 *      http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html</li>
 * 	<li>下载后解压，可以看到local_policy.jar和US_export_policy.jar以及readme.txt</li>
 * 	<li>如果安装了JRE，将两个jar文件放到%JRE_HOME%\lib\security目录下覆盖原来的文件</li>
 * 	<li>如果安装了JDK，将两个jar文件放到%JDK_HOME%\jre\lib\security目录下覆盖原来文件</li>
 * </ol>
 */
public class WxEncrypt {
	private final static Logger logger = LoggerFactory.getLogger(WxEncrypt.class);
	private final static String GENERATE_FORMAT = "<xml>\n" + "<Encrypt><![CDATA[%1$s]]></Encrypt>\n"
			+ "<MsgSignature><![CDATA[%2$s]]></MsgSignature>\n<TimeStamp>%3$s</TimeStamp>\n" + "<Nonce><![CDATA[%4$s]]></Nonce>\n" + "</xml>";
	
	private final Base64 base64 = new Base64();
	private final byte[] aesKey;
	private final String token;
	private final String appid;

	/**
	 * 构造函数
	 * @param token 公众平台上，开发者设置的token
	 * @param encodingAesKey 公众平台上，开发者设置的EncodingAESKey
	 * @param appId 公众平台appid
	 * 
	 * @throws WxEncryptException 执行失败，请查看该异常的错误码和具体的错误信息
	 */
	public WxEncrypt(String token, String encodingAesKey, String appId) {
		if (encodingAesKey.length() != 43) {
			throw new RuntimeException("SymmetricKey非法,不是43位字符串");
		}
		this.token = token;
		this.appid = appId;
		aesKey = Base64.decodeBase64(encodingAesKey + "=");
	}

	/**
	 * 将公众平台回复用户的消息加密打包.
	 * <ol>
	 * 	<li>对要发送的消息进行AES-CBC加密</li>
	 * 	<li>生成安全签名</li>
	 * 	<li>将消息密文和安全签名打包成xml格式</li>
	 * </ol>
	 * 
	 * @param replyMsg 公众平台待回复用户的消息，xml格式的字符串
	 * @param timeStamp 时间戳，可以自己生成，也可以用URL参数的timestamp
	 * @param nonce 随机串，可以自己生成，也可以用URL参数的nonce
	 * 
	 * @return 加密后的可以直接回复用户的密文，包括msg_signature, timestamp, nonce, encrypt的xml格式的字符串
	 * @throws WxEncryptException 执行失败，请查看该异常的错误码和具体的错误信息
	 */
	public String encryptMsg(String replyMsg, String timeStamp, String nonce) throws WxEncryptException {
		// 加密
		
		String randomStr = SecurityUtils.randomStr(16);
		String encrypt = AESEncoder.encode(appid, replyMsg, randomStr, aesKey, base64);;

		// 生成安全签名
		if (StringUtils.isBlank(timeStamp)) {
			timeStamp = Long.toString(System.currentTimeMillis());
		}
        String signature = sha1(token, timeStamp, nonce, encrypt);
		logger.debug("Send weixin signature is {}", signature);
			
		return generate(encrypt, signature, timeStamp, nonce);
	}

	/**
	 * 检验消息的真实性，并且获取解密后的明文.
	 * <ol>
	 * 	<li>利用收到的密文生成安全签名，进行签名验证</li>
	 * 	<li>若验证通过，则提取xml中的加密消息</li>
	 * 	<li>对消息进行解密</li>
	 * </ol>
	 * 
	 * @param msgSignature 签名串，对应URL参数的msg_signature
	 * @param timeStamp 时间戳，对应URL参数的timestamp
	 * @param nonce 随机串，对应URL参数的nonce
	 * @param postData 密文，对应POST请求的数据
	 * 
	 * @return 解密后的原文
	 * @throws WxEncryptException 执行失败，请查看该异常的错误码和具体的错误信息
	 */
	public String decryptMsg(String msgSignature, String timeStamp, String nonce, String postData)throws WxEncryptException {
		
		String encrypt = extractEncrypt(postData);

		// 验证安全签名
		String signature = sha1(token, timeStamp, nonce, encrypt);
		if (!signature.equals(msgSignature)) {
			throw new WxEncryptException(WxEncryptException.ValidateSignatureError);
		}

		// 解密
		return AESEncoder.decode(encrypt, appid, aesKey);
	}

	/**
	 * 验证URL
	 * @param msgSignature 签名串，对应URL参数的msg_signature
	 * @param timeStamp 时间戳，对应URL参数的timestamp
	 * @param nonce 随机串，对应URL参数的nonce
	 * @param echoStr 随机串，对应URL参数的echostr
	 * 
	 * @return 解密之后的echostr
	 * @throws WxEncryptException 执行失败，请查看该异常的错误码和具体的错误信息
	 */
	public String verifyUrl(String msgSignature, String timeStamp, String nonce, String echoStr)throws WxEncryptException {
		try{
			String signature = sha1(token, timeStamp, nonce, echoStr);

			if (!signature.equals(msgSignature)) {
				throw new WxEncryptException(WxEncryptException.ValidateSignatureError);
			}
			String result = AESEncoder.decode(echoStr, appid, aesKey);
			return result;
		}catch(Exception e){
			logger.error("verifyUrl is fail, error is {}", e.getMessage());
			throw new WxEncryptException(WxEncryptException.DecryptAESError);
		}
	}
	
	/**
	 * 对提供参数签名
	 * 
	 * @param params 加密参数
	 * @return sh1签名
	 */
	private String sha1(String... params) {
		Arrays.sort(params);
		String str = StringUtils.join(params);
		return SecurityUtils.sha1(str);
	}
	
	/**
	 * 提前加密字段内容
	 * 
	 * @param xmltext XML文档
	 * @return Encrypt字段内容
	 * @throws WxEncryptException
	 */
	private String extractEncrypt(String xmltext) throws WxEncryptException{
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(xmltext);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);

			Element root = document.getDocumentElement();
			return root.getElementsByTagName("Encrypt").item(0).getTextContent();
		} catch (Exception e) {
			logger.error("Parse {} error is {}", xmltext, e.getMessage());
			throw new WxEncryptException(WxEncryptException.ParseXmlError);
		}
	}
	
	/**
	 * 生成xml消息
	 * 
	 * @param encrypt 加密后的消息密文
	 * @param signature 安全签名
	 * @param timestamp 时间戳
	 * @param nonce 随机字符串
	 * @return 生成的xml字符串
	 */
	private String generate(String encrypt, String signature, String timestamp, String nonce) {
		return String.format(GENERATE_FORMAT, encrypt, signature, timestamp, nonce);
	}

}