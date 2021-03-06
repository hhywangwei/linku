package com.linku.server.wx.small;

import com.linku.server.BaseException;
import com.linku.server.wx.configure.properties.WxProperties;
import com.linku.server.wx.gzh.token.GzhTokenService;
import com.linku.server.wx.small.client.impl.WxSmallClients;
import com.linku.server.wx.small.session.RedisSessionDao;
import com.linku.server.wx.small.session.SessionDao;
import com.linku.server.wx.small.utils.WxSmallUtils;
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
    private final GzhTokenService tokenService;
    private final SessionDao sessionDao;
    private final WxSmallClients clients;
    private final WxProperties properties;

    @Autowired
    public WxSmallService(GzhTokenService tokenService,
                          StringRedisTemplate redisTemplate,
                          WxProperties properties) {

        this.tokenService = tokenService;
        this.sessionDao = new RedisSessionDao(redisTemplate);
        this.clients = new WxSmallClients(properties);
        this.properties = properties;
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
     * @param builder 构建模板消息
     * @return
     */
    public Mono<Boolean> sendTmpMsg(SendTmpMsgRequestBuilder builder){
        String token = getAccessToken();
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

    private String getAccessToken(){
        Optional<String> optional = tokenService.getAccessToken(properties.getAppid());
        return optional.orElseThrow(() -> new BaseException(5001, "微信公众号Token失效"));
    }
}