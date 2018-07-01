package com.tuoshecx.server.security.token.simple;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.common.utils.SecurityUtils;
import com.tuoshecx.server.security.Credential;
import com.tuoshecx.server.security.token.TokenService;
import com.tuoshecx.server.security.token.simple.repository.TokenRepository;

import java.util.Optional;

/**
 * 访问Token服务
 *
 * @author WangWei
 */
public class SimpleTokenService implements TokenService {
    
    private final TokenRepository repos;

    public SimpleTokenService(TokenRepository repos){
    	this.repos = repos;
    }

    @Override
    public String generate(Credential credential) {
        String token = SecurityUtils.randomStr(64);
        if(!repos.save(token, credential)){
            throw new BaseException(2001,"创建认证失败");
        }
        return token;
    }

    @Override
    public boolean update(String token, Credential t) {
        return repos.save(token, t);
    }

    @Override
    public Credential validateAndGet(String token) {
        Optional<Credential> t = repos.get(token);
        return t.orElseThrow(() -> new BaseException(110, "认证不存在"));
    }

    @Override
    public void destroy(String token) {
        repos.remove(token);
    }
}
