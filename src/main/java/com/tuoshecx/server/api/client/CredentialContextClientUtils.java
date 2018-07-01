package com.tuoshecx.server.api.client;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.security.Credential;
import com.tuoshecx.server.security.CredentialContextUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * 客户端认证用户工具类型
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class CredentialContextClientUtils {
    public static final String SHOP_ID_KEY = "shop_id";
    public static final String OPENID_KEY = "openid";


    /**
     * 获取用户认证信息
     *
     * @return 认证信息
     */
    public static Credential getCredential(){
        Optional<Credential> optional = CredentialContextUtils.getCredential();
        return optional.orElseThrow(() -> new BaseException("认证错误，非法操作"));
    }

    /**
     * 获取用户所属店铺编号
     *
     * @return 店铺编号
     */
    public static String currentShopId(){
        Credential credential = getCredential();

        if(isTemp(credential.getType())){
            throw new BaseException("临时用户无所属店铺");
        }

        return attacheValue(credential, SHOP_ID_KEY)
                .orElseThrow(() -> new BaseException("非法操作"));
    }

    public static boolean isTemp(String type){
        return StringUtils.equals("temp", type);
    }

    private static Optional<String> attacheValue(Credential t, String key){
        return t.getAttaches()
                .stream()
                .filter(e -> isKey(e, key))
                .map(Credential.Attach::getValue)
                .findFirst();
    }

    private static boolean isKey(Credential.Attach attach, String key){
        return StringUtils.equals(key, attach.getKey());
    }

    /**
     * 获取用户微信openid
     *
     * @return 微信openid
     */
    public static String currentOpenid(){
        Credential credential = getCredential();

        if(!isWxEnter(credential.getEnter())){
            throw new BaseException("非微信登陆，无微信openid");
        }

        return attacheValue(credential, OPENID_KEY)
                .orElseThrow(() -> new BaseException("非法操作"));
    }

    public static boolean isWxEnter(String enter){
        return StringUtils.equals(enter, "wx");
    }


}
