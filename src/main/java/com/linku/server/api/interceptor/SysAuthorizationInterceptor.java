package com.linku.server.api.interceptor;

import com.linku.server.security.Credential;
import com.linku.server.security.token.TokenService;
import org.apache.commons.lang3.StringUtils;

/**
 * 系统管理端认证处理
 *
 * @author WangWei
 */
public class SysAuthorizationInterceptor extends BaseAuthorizationInterceptor {

    public SysAuthorizationInterceptor(TokenService service) {
        super(service);
    }

    @Override
    protected boolean authorization(Credential credential) {
        return StringUtils.equals(credential.getType(), "sys");
    }
}
