package com.tuoshecx.server.common.id;

/**
 * 自动生成ID
 * 
 * @author WangWei
 */
public interface IdGenerator<T> {

	T generate();
	
}
