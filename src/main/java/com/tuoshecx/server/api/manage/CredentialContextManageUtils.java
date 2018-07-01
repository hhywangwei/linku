package com.tuoshecx.server.api.manage;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.security.Credential;
import com.tuoshecx.server.security.CredentialContextUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * 店铺认证用户工具类型
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class CredentialContextManageUtils {
    public static final String SHOP_ID_KEY = "shop_id";

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
        Credential t = getCredential();
        Optional<String> optional = t.getAttaches().stream()
                .filter(e -> StringUtils.equals(SHOP_ID_KEY, e.getKey()))
                .map(Credential.Attach::getValue)
                .findFirst();

        return optional.orElseThrow(() -> new BaseException("认证错误，非法操作"));
    }
}
