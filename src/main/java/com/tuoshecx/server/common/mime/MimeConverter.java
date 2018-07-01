package com.tuoshecx.server.common.mime;

/**
* 转换到指定的媒体格式（如：XML到JSON格式)
 * 
 * @author WangWei
 *
 * @param <I> 转换前格式类型
 * @param <O> 转换后格式类型
 */
public interface MimeConverter<I, O> {

	/**
	 * 转换方法
	 * 
	 * @param i 转换前对象
	 * @return  转换后对象
	 */
	O convert(I i);
}
