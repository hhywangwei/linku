package com.tuoshecx.server.wx.component.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

class NoneEventHandler implements ComponentEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoneEventHandler.class);

    @Override
    public Optional<ResponseEntity<String>> handler(Map<String, String> data) {
        LOGGER.warn("Not handler {} event", getInfoType(data));
        return Optional.of(ResponseEntity.ok().body("success"));
    }
}
