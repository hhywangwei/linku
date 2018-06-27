package com.linku.server.api.wx;

import com.linku.server.api.vo.ResultVo;
import com.linku.server.wx.component.client.ComponentClientService;
import com.linku.server.wx.component.encrypt.WxEncrypt;
import com.linku.server.wx.component.encrypt.WxEncryptException;
import com.linku.server.wx.component.event.ComponentEventHandlers;
import com.linku.server.wx.configure.properties.WxComponentProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/wx/component")
public class WxComponentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WxComponentController.class);

    private WxComponentProperties properties;
    private final ComponentEventHandlers eventHandlers;
    private final ComponentClientService clientService;
    private final WxEncrypt crypt;
    private final Charset charsetUTF = Charset.forName("UTF-8");

    @Autowired
    public WxComponentController(WxComponentProperties properties,
                                 ComponentEventHandlers eventHandlers,
                                 ComponentClientService clientService) {

        this.properties = properties;
        this.eventHandlers = eventHandlers;
        this.clientService = clientService;
        this.crypt = new WxEncrypt(properties.getValidateToken(), properties.getEncodingAesKey(), properties.getAppid());
    }

    @PostMapping(value="event")
    public ResponseEntity<?> event(
            @RequestParam String signature, @RequestParam String timestamp,
            @RequestParam String nonce, @RequestParam(value="msg_signature")String msgSignature,
            @RequestParam(value="encrypt_type")String encryptType, @RequestBody byte[] bytes){

        LOGGER.debug("Timestamp is {}, nonce is {}, signature is {}", timestamp, nonce, msgSignature);

        String body = new String(bytes, charsetUTF);
        LOGGER.debug("Body is {}", body);

        try{
            String decBody = crypt.decryptMsg(msgSignature, timestamp, nonce, body);
            LOGGER.debug("Desc body is {}", decBody);
            return eventHandlers.handler(decBody);
        }catch(WxEncryptException e){
            LOGGER.debug("Receive message is fail, error is {}", e.getMessage());
        }
        return ResponseEntity.ok("fail");
    }

    @GetMapping(value = "preAuthorizer", produces = APPLICATION_JSON_UTF8_VALUE)
    public DeferredResult<ResultVo<String>> preAuthorizer(){
        DeferredResult<ResultVo<String>> result = new DeferredResult<>();

        clientService.obtainPreAuthCode().subscribe(e ->{
            if(e.getCode() == 0){
                String c = String.format("https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid=%s&pre_auth_code=%s&" +
                        "redirect_uri=%s&auth_type=%d", properties.getAppid(), e.getPreAuthCode(), "http://", 2);
                result.setResult(ResultVo.success(c));
            }else {
                LOGGER.error("Obtain pre auth code fail, code {} message {}", e.getCode(), e.getMessage());
                result.setResult(ResultVo.error(150, "得到预授权码失败"));
            }
        });

        return result;
    }
}
