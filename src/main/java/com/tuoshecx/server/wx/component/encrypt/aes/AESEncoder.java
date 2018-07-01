package com.tuoshecx.server.wx.component.encrypt.aes;

import java.nio.charset.Charset;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.tuoshecx.server.wx.component.encrypt.WxEncryptException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 提供基于AES算法的加解密接口.
 *
 * @author <a href="hhywangwei@gmail.com>WangWei</a>
 */
public class AESEncoder {
	private static final Logger logger = LoggerFactory.getLogger(AESEncoder.class);
	private static final Charset CHARSET = Charset.forName("UTF-8");
	private static final int BLOCK_SIZE = 32;

	/**
	 * 加密传输内容
	 * 
	 * @param appid       微信appid
	 * @param text        加密内容
	 * @param randomStr   随机字符串
	 * @param aesKey      微信加密key
	 * @param base64      {@link Base64}
	 * @return            加密字符串
	 */
	public static String encode(String appid, String text, 
			String randomStr, byte[] aesKey, Base64 base64) throws WxEncryptException {
		
		ByteGroup byteCollector = new ByteGroup();
		byte[] randomStrBytes = randomStr.getBytes(CHARSET);
		byte[] textBytes = text.getBytes(CHARSET);
		byte[] networkBytesOrder = getNetworkBytesOrder(textBytes.length);
		byte[] appidBytes = appid.getBytes(CHARSET);

		// randomStr + networkBytesOrder + text + appid
		byteCollector.addBytes(randomStrBytes);
		byteCollector.addBytes(networkBytesOrder);
		byteCollector.addBytes(textBytes);
		byteCollector.addBytes(appidBytes);

		// ... + pad: 使用自定义的填充方式对明文进行补位填充
		byte[] padBytes = pkcs7Encode(byteCollector.size());
		byteCollector.addBytes(padBytes);

		// 获得最终的字节流, 未加密
		byte[] unencrypted = byteCollector.toBytes();

		try{
			// 设置加密模式为AES的CBC模式
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
			// 加密
			byte[] encrypted = cipher.doFinal(unencrypted);
			// 使用BASE64对加密后的字符串进行编码
			return base64.encodeToString(encrypted);
		}catch(Exception e){
			logger.error("AES encode error is {}", e.getMessage());
			throw new WxEncryptException(WxEncryptException.EncryptAESError);
		}
	}
	
	// 生成4个字节的网络字节序
	private static byte[] getNetworkBytesOrder(int sourceNumber) {
		byte[] orderBytes = new byte[4];
		orderBytes[3] = (byte) (sourceNumber & 0xFF);
		orderBytes[2] = (byte) (sourceNumber >> 8 & 0xFF);
		orderBytes[1] = (byte) (sourceNumber >> 16 & 0xFF);
		orderBytes[0] = (byte) (sourceNumber >> 24 & 0xFF);
		return orderBytes;
	}
	
	/**
	  * 获得对明文进行补位填充的字节.
	 * 
	 * @param count 需要进行填充补位操作的明文字节个数
	 * @return 补齐用的字节数组
	 */
	private static byte[] pkcs7Encode(int count){
		int amountToPad = BLOCK_SIZE - (count % BLOCK_SIZE);
		if (amountToPad == 0) {
			amountToPad = BLOCK_SIZE;
		}
		// 获得补位所用的字符
		char padChr = chr(amountToPad);
		StringBuilder tmp = new StringBuilder();
		for (int index = 0; index < amountToPad; index++) {
			tmp.append(padChr);
		}
		return tmp.toString().getBytes(CHARSET);
	}

    /**
     * 解密传送内容
     *
     * @param text   加密内容
     * @param appid  appid
     * @param aesKey aesKey
     * @return 解密的内容
     */
	public static String decode(String text, String appid, byte[] aesKey)throws WxEncryptException{
		
		byte[] original;
		
		try{
			// 设置解密模式为AES的CBC模式
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec key_spec = new SecretKeySpec(aesKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
			cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);
			// 使用BASE64对密文进行解码
			byte[] encrypted = Base64.decodeBase64(text);
			// 解密
			original = cipher.doFinal(encrypted);
		}catch(Exception e){
			logger.error("AES decode error is {}", e.getMessage());
			throw new WxEncryptException(WxEncryptException.DecryptAESError);
		}
		
		byte[] bytes = pkcs7Decode(original);
		byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);
		int xmlLength = recoverNetworkBytesOrder(networkOrder);
		
		String fromAppid = new String(Arrays.copyOfRange(bytes, 20 + xmlLength, bytes.length), CHARSET);
		if(!StringUtils.equals(appid, fromAppid)){
			throw new WxEncryptException(WxEncryptException.ValidateAppidError);
		}
		
		return new String(Arrays.copyOfRange(bytes, 20, 20 + xmlLength), CHARSET);
    }
	
	/**
	 * 删除解密后明文的补位字符
	 * 
	 * @param decrypted 解密后的明文
	 * @return 删除补位字符后的明文
	 */
	private static byte[] pkcs7Decode(byte[] decrypted){
		int pad = (int) decrypted[decrypted.length - 1];
		if (pad < 1 || pad > 32) {
			pad = 0;
		}
		return  Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
	}
	
	private static int recoverNetworkBytesOrder(byte[] orderBytes) {
		int sourceNumber = 0;
		for (int i = 0; i < 4; i++) {
			sourceNumber <<= 8;
			sourceNumber |= orderBytes[i] & 0xff;
		}
		return sourceNumber;
	}

	/**
	 * 将数字转化成ASCII码对应的字符，用于对明文进行补码
	 * 
	 * @param a 需要转化的数字
	 * @return 转化得到的字符
	 */
	private static char chr(int a) {
		byte target = (byte) (a & 0xFF);
		return (char) target;
	}

}
