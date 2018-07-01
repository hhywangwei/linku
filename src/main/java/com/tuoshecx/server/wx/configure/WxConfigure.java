package com.tuoshecx.server.wx.configure;

import com.tuoshecx.server.wx.configure.properties.WxComponentProperties;
import com.tuoshecx.server.wx.configure.properties.WxPayProperties;
import io.netty.handler.ssl.SslContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;

import javax.net.ssl.KeyManagerFactory;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * 微信配置信息
 *
 * @author WangWei
 */
@Configuration
@EnableConfigurationProperties(value = {WxComponentProperties.class, WxPayProperties.class})
public class WxConfigure {
    private static final Logger logger = LoggerFactory.getLogger(WxConfigure.class);

    @Autowired
    @Bean(name = "wxPayConnector")
    public ClientHttpConnector wxPayConnector(ResourceLoader resourceLoader, WxPayProperties properties){
        final char[] password = properties.getKeystore().getPassword().toCharArray();

        final KeyStore keyStore;
        try(InputStream in = resourceLoader.getResource(properties.getKeystore().getUri()).getInputStream()) {
            keyStore  = KeyStore.getInstance("PKCS12");
            keyStore.load(in, password);
        }catch (Exception e){
            logger.error("Local keystore fail, error is {}", e.getMessage());
            throw new RuntimeException("Local keystore fail, error is " + e.getMessage());
        }

        return new ReactorClientHttpConnector(
                builder -> builder.compression(true).sslSupport(e -> setSslContext(e, keyStore, password)));
    }

    private void setSslContext(SslContextBuilder builder, KeyStore keyStore, char[] password){
        try{
            String alg = KeyManagerFactory.getDefaultAlgorithm();
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(alg);
            kmf.init(keyStore, password);
            builder.keyManager(kmf)
                    .protocols("TLSv1");
        }catch (Exception e){
            logger.error("Set ssl context fail, error is {}", e.getMessage());
            throw new RuntimeException("Set ssl context fail, error is " + e.getMessage());
        }
    }
}
