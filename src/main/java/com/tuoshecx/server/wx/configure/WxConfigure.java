package com.tuoshecx.server.wx.configure;

import com.tuoshecx.server.wx.component.encrypt.WxEncrypt;
import com.tuoshecx.server.wx.configure.properties.WxComponentProperties;
import com.tuoshecx.server.wx.configure.properties.WxMessageTemplateProperties;
import com.tuoshecx.server.wx.configure.properties.WxPayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信配置信息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Configuration
@EnableConfigurationProperties(value = {WxComponentProperties.class, WxMessageTemplateProperties.class, WxPayProperties.class})
public class WxConfigure {

    @Bean
    @Autowired
    public WxEncrypt initWxEncrypt(WxComponentProperties properties){
        return new WxEncrypt(properties.getValidateToken(), properties.getEncodingAesKey(), properties.getAppid());
    }
}
