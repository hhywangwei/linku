package com.linku.server.wx.gzh.client;

import com.linku.server.BaseException;
import com.linku.server.wx.configure.properties.WxProperties;
import com.linku.server.wx.gzh.client.impl.GzhHttpClients;
import com.linku.server.wx.gzh.client.request.ObtainCredentialRequest;
import com.linku.server.wx.gzh.client.response.ObtainCredentialResponse;
import com.linku.server.wx.gzh.token.GzhTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

/**
 * 调用微信公众号API服务
 *
 * @author WangWei
 */
@Service
public class GzhClientService {
    private final static Logger logger = LoggerFactory.getLogger(GzhClientService.class);

    private final GzhHttpClients clients;
    private final WxProperties properties;
    private final GzhTokenService tokenService;

    @Autowired
    public GzhClientService(WxProperties properties, GzhTokenService tokenService){
        this.clients = new GzhHttpClients(properties);
        this.properties = properties;
        this.tokenService = tokenService;
    }

    /**
     * 获取微信公众号访问凭证(access_token)
     */
    public Mono<ObtainCredentialResponse> obtainCredential(){
        final ObtainCredentialRequest t =
                new ObtainCredentialRequest(properties.getAppid(), properties.getSecret());
        return clients.obtainCredentialClient().request(t);
    }

    private String getToken(){
        Optional<String> optional = tokenService.getAccessToken(properties.getAppid());
        return optional.orElseThrow(() -> new BaseException(5001, "微信公众号Token失效"));
    }
}
