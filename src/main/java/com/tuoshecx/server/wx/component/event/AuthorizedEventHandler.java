package com.tuoshecx.server.wx.component.event;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.Map;

class AuthorizedEventHandler extends BaseEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizedEventHandler.class);

    @Override
    protected boolean isHandler(String infoType) {
        return StringUtils.equals(infoType, "authorized");
    }

    @Override
    protected ResponseEntity<String> doHandler(Map<String, String> data) {
        String appid = data.get("AuthorizerAppid");
        String code = data.get("AuthorizationCode");
        String preCode = data.get("PreAuthCode");
        LOGGER.info("Authorized pass appid {} code {} preCode {}", appid, code, preCode);
        return ResponseEntity.ok("success");
    }
}
