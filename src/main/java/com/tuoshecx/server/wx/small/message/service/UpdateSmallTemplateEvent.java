package com.tuoshecx.server.wx.small.message.service;

/**
 *  更新微信模板信息Event
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class UpdateSmallTemplateEvent extends InitSmallTemplateEvent {

    public UpdateSmallTemplateEvent(Object source, String appid) {
        super(source, appid);
    }
}
