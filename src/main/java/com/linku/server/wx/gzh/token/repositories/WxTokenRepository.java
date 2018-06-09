package com.linku.server.wx.gzh.token.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 微信Token存储操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public interface WxTokenRepository {

    /**
     * 得到Token对象
     *
     * @param appid 微信appid
     * @return Token
     */
    Optional<String> get(String appid);

    /**
     * 刷新Token
     *
     * @param appid 微信appid
     * @param token access token
     * @param expireSecond 过期秒
     * @return true:刷新成功
     */
    boolean refresh(String appid,  String token, int expireSecond);

    /**
     * 过期token
     *
     * @return 过期公众号集合
     */
    Set<String> expired();
}
