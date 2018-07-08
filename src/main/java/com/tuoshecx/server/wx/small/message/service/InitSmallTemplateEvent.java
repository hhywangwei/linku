package com.tuoshecx.server.wx.small.message.service;

import org.springframework.context.ApplicationEvent;

/**
 * 初始微信小程序消息模板Event
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class InitSmallTemplateEvent extends ApplicationEvent {

    private final String appid;

    public InitSmallTemplateEvent( Object source, String appid) {
        super(source);
        this.appid = appid;
    }

    public String getAppid() {
        return appid;
    }
}
