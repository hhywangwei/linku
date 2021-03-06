package com.linku.server.api.interceptor;

import com.linku.server.security.Credential;
import com.linku.server.security.token.TokenService;
import org.apache.commons.lang3.StringUtils;

/**
 * 店铺端权限认证拦截器
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class ManagerAuthorizationInterceptor extends BaseAuthorizationInterceptor {

    public ManagerAuthorizationInterceptor(TokenService service) {
        super(service);
    }

    @Override
    protected boolean authorization(Credential credential) {
        return StringUtils.equals(credential.getType(), "manager");
    }
}
