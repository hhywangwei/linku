package com.linku.server.wx.pay.utils;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 微信工具类
 *
 * @author WangWei
 */
public class WxPayUtils {
    private static final Logger logger = LoggerFactory.getLogger(WxPayUtils.class);
    private static final String RANDOM_CONTENT = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    private static final Charset charset = Charset.forName("UTF-8");

    /**
     * 得到随机生成字符串
     *
     * @param len 字符串长度
     * @return
     */
    public static String nonceStr(int len){
       return RandomStringUtils.random(len, RANDOM_CONTENT);
    }

    /**
     * 时间戳
     *
     * @return
     */
    public static int timestamp(){
        return (int)(System.currentTimeMillis()/1000L);
    }

    /**
     * 微信签名
     *
     * @param params 签名参数
     * @param key    key值
     * @return
     */
    public static String sign(Map<String, String> params, String key){
        List<String> sortNames = params.keySet().stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        String m = sortNames.stream().map(e -> e + "=" + params.get(e)).collect(Collectors.joining("&"));
        String c = m + "&" + "key=" + key;
        String sign = StringUtils.upperCase(md5(c));
        logger.debug("Sign data is {}, sign is {}", m, sign);
        return sign;
    }

    /**
     * MD5编码,缺省编码字符集是UTF-8
     *
     * @param c  md5编码内容
     * @return md5加密字符串
     */
    public static String md5(String c){
        final String charsetName = "UTF-8";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            StringBuilder sb = new StringBuilder();
            byte[] bytes = md.digest(c.getBytes(charsetName));
            for (byte b : bytes){
                sb.append(byteToHexString(b));
            }
            return sb.toString();
        } catch (Exception e) {
            logger.error("MD5 encode is fail, error is {}", e.getMessage());
            return "";
        }
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * AES 256解密
     *
     * @param content 解密内容
     * @param key     解密key
     * @return 解密内容
     */
    public static String aes256Decode(String content, String key){

        try{
            SecretKey secretKey = new SecretKeySpec(key.getBytes(charset), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE,secretKey);
            byte[] bytes = cipher.doFinal(content.getBytes(charset));
            String data = new String(bytes, charset);
            logger.debug("AES decode is {}", data);
            return data;
        }catch (Exception e){
            logger.error("Decode aes 256 fail, error is {}", e.getMessage());
            return "";
        }
    }

    /**
     * 编码为Base64
     *
     * @param v 值
     * @return
     */
    public static  String base64Encode(String v){
        return Base64.getEncoder().encodeToString(v.getBytes(Charset.forName("UTF-8")));
    }

    /**
     * Base64解码
     *
     * @param v 值
     * @return
     */
    public static String base64Decode(String v){
        byte[] bytes= Base64.getDecoder().decode(v.getBytes(Charset.forName("UTF-8")));
        return new String(bytes, Charset.forName("UTF-8"));
    }
}
