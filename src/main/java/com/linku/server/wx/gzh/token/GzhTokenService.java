package com.linku.server.wx.gzh.token;

import com.linku.server.wx.gzh.token.repositories.WxTokenRepository;

import java.util.Optional;

/**
 * 微信访问Token业务服务
 *
 * @author WangWei
 */
public class GzhTokenService {
    private final WxTokenRepository repository;

    GzhTokenService(WxTokenRepository repository) {
        this.repository = repository;
    }

    /**
     * 得到微信访问Token
     *
     * @return 微信访问Token
     */
    public Optional<String> getAccessToken(String appid){
        return repository.get(appid);
    }
}
