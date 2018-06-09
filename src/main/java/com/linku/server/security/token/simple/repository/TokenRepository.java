package com.linku.server.security.token.simple.repository;

import com.linku.server.security.Credential;

import java.util.Optional;

/**
 * Token存储接口
 * 
 * @author WangWei
 */
public interface TokenRepository {

	/**
	 * 保存新的token
     *
	 * @param token token值
	 * @param t 内容
     * @return true 保存成功
	 */
	boolean save(String token, Credential t);
	
	/**
	 * 得到token内容
	 * 
	 * @param token token值
	 * @return 内容
	 */
	Optional<Credential> get(String token);
	
	/**
	 * 删除token
	 * 
	 * @param token token值
	 * @return true 删除成功
	 */
	boolean remove(String token);
}
