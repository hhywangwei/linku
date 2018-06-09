package com.linku.server.wx.gzh.token;

import com.linku.server.wx.configure.properties.WxProperties;
import com.linku.server.wx.gzh.client.GzhClientService;
import com.linku.server.wx.gzh.token.repositories.WxTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 公众号Access Token更新
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Component
public class GzhTokenSchedule {
    private static final Logger logger = LoggerFactory.getLogger(GzhTokenSchedule.class);

    private final WxTokenRepository repository;
    private final GzhClientService client;
    private final WxProperties properties;

    @Autowired
    public GzhTokenSchedule(WxTokenRepository repository, GzhClientService client, WxProperties properties) {
        this.repository = repository;
        this.client = client;
        this.properties = properties;
    }

    @Autowired
    public void initToken(){
        final String appid = properties.getAppid();
        Optional<String> optional = repository.get(appid);
        if(!optional.isPresent()){
            refreshTokenAppid(client, repository, appid);
        }
    }

    @Autowired
    @Scheduled(fixedDelay = 3 * 60 * 1000L, initialDelay = 30 * 1000L)
    public void refreshToken(){
        for(String appid: repository.expired()){
            refreshTokenAppid(client, repository, appid);
        }
    }

    private void refreshTokenAppid(GzhClientService client, WxTokenRepository repository, String appid){
        client.obtainCredential().subscribe(e ->{

            if(!isOk(e.getCode())){
                logger.error("Refresh token fail, appid is {} error {}", appid, e.getMessage());
                return;
            }

            repository.refresh(appid, e.getToken(), e.getExpires());
        });
    }

    private boolean isOk(int code){
        return code == 0;
    }
}
