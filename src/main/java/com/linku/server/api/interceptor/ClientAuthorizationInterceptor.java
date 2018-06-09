package com.linku.server.api.interceptor;

import com.linku.server.security.Credential;
import com.linku.server.security.token.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 小程序端权限认证拦截器
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class ClientAuthorizationInterceptor extends BaseAuthorizationInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(ClientAuthorizationInterceptor.class);

    public ClientAuthorizationInterceptor(TokenService service) {
        super(service);
    }

    @Override
    protected boolean authorization(Credential credential){
        return StringUtils.equals(credential.getType(), "user") ||
                StringUtils.equals(credential.getType(), "temp");
    }
}
