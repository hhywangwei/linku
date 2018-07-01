package com.tuoshecx.server.common.id;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**
 * 通过UUID生成ID
 * 
 * @author WangWei
 */
public class UUIDGenerator implements IdGenerator<String> {

	@Override
	public String generate() {
		return StringUtils.remove(UUID.randomUUID().toString(), "-");
	}
}
