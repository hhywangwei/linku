package com.tuoshecx.server.wx.small.session;

import java.util.Optional;

/**
 * 小程序用户登陆session_key数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public interface SessionDao {

    /**
     * 保存用户session_key
     *
     * @param openid      用户微信openid
     * @param sessionKey  用户登陆成功session_key
     * @return true:保存成功
     */
    boolean saveKey(String openid, String sessionKey);

    /**
     * 得到用户session_key
     *
     * @param openid  用户微信openid
     * @return 用户登陆session_key
     */
    Optional<String> getKey(String openid);

    /**
     * 删除用户session_key
     *
     * @param openid 用户微信openid
     */
    void remove(String openid);
}
