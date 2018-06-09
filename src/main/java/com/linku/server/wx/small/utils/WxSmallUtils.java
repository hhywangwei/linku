package com.linku.server.wx.small.utils;

import com.linku.server.common.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;
import java.util.Optional;

/**
 * 微信小程序工具类
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class WxSmallUtils {
    private static final Logger logger = LoggerFactory.getLogger(WxSmallUtils.class);

    private static final String AES = "AES";
    private static final String AES_CBC_PKCS7 = "AES/CBC/PKCS5Padding";

    /**
     * 验证微信小程序数据签名
     *
     * @param data        被签名数据
     * @param sessionKey  用户登陆session_key
     * @param signature   数据签名
     * @return true:是该数据签名
     */
    public static boolean isSignature(String data, String sessionKey, String signature){
        final String s = SecurityUtils.sha1(data + sessionKey);

        logger.debug("Signature content is {}, signature is {}", data, s);

        return StringUtils.equals(s, signature);
    }

    /**
     * 微信小程序数据解密
     *
     * @param data        明文
     * @param sessionKey  用户登陆session_key
     * @param vi          加密算法的初始向量
     * @return 解密数据
     */
    public static Optional<String> decrypt(String data, String sessionKey, String vi){
        try{
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS7);
            Key sKeySpec = new SecretKeySpec(decodeBase64(sessionKey), AES);
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec,  generateIv(decodeBase64(vi)));
            String c = new String(cipher.doFinal(decodeBase64(data)), Charset.forName("UTF-8"));

            logger.debug("Wx small decrypt is {}", c);

            return Optional.of(c);
        }catch (Exception e){
            logger.debug("Wx small decrypt fail, error is {}", e.getMessage());
            return Optional.empty();
        }
    }

    private static byte[] decodeBase64(String c){
        return Base64.decode(c);
    }

    private static AlgorithmParameters generateIv(byte[] iv) throws Exception{
        AlgorithmParameters params = AlgorithmParameters.getInstance(AES);
        params.init(new IvParameterSpec(iv));
        return params;
    }

}
