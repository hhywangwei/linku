package com.tuoshecx.server.wx.small;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.shop.domain.ShopWxToken;
import com.tuoshecx.server.shop.service.ShopWxService;
import com.tuoshecx.server.wx.component.token.ComponentTokenService;
import com.tuoshecx.server.wx.configure.properties.WxComponentProperties;
import com.tuoshecx.server.wx.small.client.impl.WxSmallClients;
import com.tuoshecx.server.wx.small.session.RedisSessionDao;
import com.tuoshecx.server.wx.small.session.SessionDao;
import com.tuoshecx.server.wx.small.utils.WxSmallUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * 微信小程API接口业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
public class WxSmallService {
    private final ShopWxService wxService;
    private final SessionDao sessionDao;
    private final WxSmallClients clients;

    @Autowired
    public WxSmallService(ShopWxService wxService,
                          StringRedisTemplate redisTemplate,
                          WxComponentProperties properties,
                          ComponentTokenService tokenService) {

        this.wxService = wxService;
        this.sessionDao = new RedisSessionDao(redisTemplate);
        this.clients = new WxSmallClients(properties, tokenService);
    }

    /**
     * 微信小程序用户登陆
     *
     * @param code 登陆code
     * @return
     */
    public Mono<Optional<String>> login(String code){
        return clients.loginClient().request(code).map(e -> {

            if(!e.isOk()){
                return Optional.empty();
            }

            boolean ok = sessionDao.saveKey(e.getOpenid(), e.getSessionKey());
            return ok? Optional.of(e.getOpenid()): Optional.empty();
        });
    }

    /**
     * 发送模板消息
     *
     * @param appid appid
     * @param builder 构建模板消息
     * @return
     */
    public Mono<Boolean> sendTmpMsg(String appid, SendTmpMsgRequestBuilder builder){
        String token = getAccessToken(appid);
        return clients.sendTmpMsgClient()
                .request(builder.build(token))
                .map(e -> e.getCode() == 0);
    }

    /**
     * 判读数据签名是否正确
     *
     * @param openid    用户openid
     * @param rawData   明文
     * @param signature 签名
     * @return true:签名正确
     */
    public boolean isSignature(String openid, String rawData, String signature){
        return sessionDao.getKey(openid)
                .map(e -> WxSmallUtils.isSignature(rawData, e, signature))
                .orElse(false);
    }

    /**
     * 解密微信加密数据
     *
     * @param openid         用户openid
     * @param encryptedData  明文
     * @param vi             加密算法的初始向量
     * @return 解密字符串
     */
    public Optional<String> decrypt(String openid, String encryptedData, String vi){
        return sessionDao.getKey(openid)
                .map(e -> WxSmallUtils.decrypt(encryptedData, e, vi).orElse(null));
    }

    /**
     * 删除微信用户登陆session_key
     *
     * @param openid 用户openid
     */
    public void removeSessionKey(String openid){
        sessionDao.remove(openid);
    }

    private String getAccessToken(String appid){
        Optional<ShopWxToken> optional = wxService.getToken(appid);
        return optional.orElseThrow(() -> new BaseException(5001, "微信公众号Token失效")).getAccessToken();
    }
}