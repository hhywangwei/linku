package com.tuoshecx.server.api.wx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.api.vo.ResultVo;
import com.tuoshecx.server.common.utils.SecurityUtils;
import com.tuoshecx.server.shop.domain.Shop;
import com.tuoshecx.server.shop.domain.ShopWxAuthorized;
import com.tuoshecx.server.shop.domain.ShopWxToken;
import com.tuoshecx.server.shop.service.ShopService;
import com.tuoshecx.server.shop.service.ShopWxService;
import com.tuoshecx.server.wx.component.client.ComponentClientService;
import com.tuoshecx.server.wx.component.client.response.ObtainQueryAuthResponse;
import com.tuoshecx.server.wx.component.encrypt.WxEncrypt;
import com.tuoshecx.server.wx.component.encrypt.WxEncryptException;
import com.tuoshecx.server.wx.component.event.ComponentEventHandlers;
import com.tuoshecx.server.wx.configure.properties.WxComponentProperties;
import com.tuoshecx.server.wx.small.event.SmallEventHandlers;
import com.tuoshecx.server.wx.small.message.service.InitSmallTemplateEvent;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/wx/component")
public class WxComponentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WxComponentController.class);
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    private WxComponentProperties properties;
    private final ComponentEventHandlers eventHandlers;
    private final ComponentClientService clientService;
    private final ShopService shopService;
    private final ShopWxService shopWxService;
    private final ApplicationContext applicationContext;
    private final ObjectMapper objectMapper;
    private final WxEncrypt encrypt;
    private final SmallEventHandlers smallEventHandlers;

    @Autowired
    public WxComponentController(WxComponentProperties properties,
                                 ComponentEventHandlers eventHandlers,
                                 ComponentClientService clientService,
                                 ShopService shopService,
                                 ShopWxService shopWxService,
                                 ApplicationContext applicationContext,
                                 ObjectMapper objectMapper,
                                 WxEncrypt encrypt,
                                 SmallEventHandlers smallEventHandlers) {

        this.properties = properties;
        this.eventHandlers = eventHandlers;
        this.clientService = clientService;
        this.shopService = shopService;
        this.shopWxService = shopWxService;
        this.applicationContext = applicationContext;
        this.objectMapper = objectMapper;
        this.encrypt = encrypt;
        this.smallEventHandlers = smallEventHandlers;
    }

    @PostMapping(value="event")
    public ResponseEntity<?> event(
            @RequestParam String signature, @RequestParam String timestamp,
            @RequestParam String nonce, @RequestParam(value="msg_signature")String msgSignature,
            @RequestParam(value="encrypt_type")String encryptType, @RequestBody byte[] bytes){

        LOGGER.debug("Timestamp is {}, nonce is {}, signature is {}", timestamp, nonce, msgSignature);

        String body = new String(bytes, UTF_8_CHARSET);
        LOGGER.debug("Body is {}", body);

        try{
            String decBody = encrypt.decryptMsg(msgSignature, timestamp, nonce, body);
            LOGGER.debug("Desc body is {}", decBody);
            return eventHandlers.handler(decBody);
        }catch(WxEncryptException e){
            LOGGER.debug("Receive message is fail, error is {}", e.getMessage());
        }
        return ResponseEntity.ok("fail");
    }

    @GetMapping(value = "preAuthorizer", produces = APPLICATION_JSON_UTF8_VALUE)
    public DeferredResult<ResultVo<String>> preAuthorizer(@RequestParam(required = false) String shopId){
        DeferredResult<ResultVo<String>> result = new DeferredResult<>();

        String redirectUri = properties.getRedirectUri() +
                "?shop_id=" + (StringUtils.isBlank(shopId)? "wx10000001" : shopId);

        clientService.obtainPreAuthCode().subscribe(e ->{
            if(e.getCode() == 0){
                String c = String.format("https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid=%s&pre_auth_code=%s&" +
                        "redirect_uri=%s&auth_type=%d", properties.getAppid(), e.getPreAuthCode(), urlEncoder(redirectUri), 2);
                LOGGER.debug("Wx authorizer url is {}", c);
                result.setResult(ResultVo.success(c));
            }else {
                LOGGER.error("Obtain pre auth code fail, code {} message {}", e.getCode(), e.getMessage());
                result.setResult(ResultVo.error(150, "得到预授权码失败"));
            }
        });

        return result;
    }

    private String urlEncoder(String v){
        try{
            return URLEncoder.encode(v, "UTF-8");
        }catch (UnsupportedEncodingException e){
            return "";
        }
    }

    @GetMapping(value = "authorizer", produces = MediaType.TEXT_HTML_VALUE)
    public DeferredResult<ResponseEntity<String>> authorizer(
            @RequestParam(value="auth_code") String authCode,
            @RequestParam(value="expires_in") Integer expiresIn,
            @RequestParam(value = "shop_id", required = false)String shopId){

        LOGGER.info("Start shop {} authorizer auth code {} expire in {}", shopId, authCode, expiresIn);

        DeferredResult<ResponseEntity<String>> result = new DeferredResult<>();
        Shop s = shopService.get(shopId);
        if(isClose(s.getState())){
            throw new BaseException("店铺已经关闭");
        }

        clientService.obtainQueryAuth(authCode).subscribe(e -> {
            int validateCode = validateAppid(shopId, e.getAuthorizerAppid());
            if(validateCode != 0){
                String uri = properties.getFailUri() + "?code=" + validateCode;
                redirectUri(result, uri);
                return ;
            }
            LOGGER.debug("Validate appid {} code is {}", e.getAuthorizerAppid(), validateCode);

            if(!isSuccess(e.getCode())){
                LOGGER.error("Obtain token fail, shopId {} code {} message {}", shopId, e.getCode(), e.getMessage());
                String uri = properties.getFailUri() + "?code=" + 20000;
                redirectUri(result, uri);
            }

            try {
                saveAuthorizerToken(shopId, e);
                noticeInitMessageTemplate(e.getAuthorizerAppid());
                saveAuthorizerConfigure(shopId, e.getAuthorizerAppid(), result);
            }catch (Exception ex){
                LOGGER.error("Init {} authorizer info fail, error is {}", ex.getMessage());
                String uri = properties.getFailUri() + "?code=" + 20000;
                redirectUri(result, uri);
            }
        });
        return result;
    }

    private boolean isClose(Shop.State state){
        return state == Shop.State.CLOSE;
    }

    private boolean isSuccess(int code){
        return code == 0;
    }

    private void saveAuthorizerToken(String shopId, ObtainQueryAuthResponse response){
        ShopWxToken t = new ShopWxToken();
        t.setShopId(shopId);
        t.setAppid(response.getAuthorizerAppid());
        t.setRefreshToken(response.getAuthorizerRefreshToken());
        t.setAccessToken(response.getAuthorizerAccessToken());
        Date now = new Date();
        t.setUpdateTime(now);
        t.setExpiresTime(DateUtils.addSeconds(now, response.getExpiresIn()));
        shopWxService.saveToken(t);
    }

    private int validateAppid(String shopId, String appid){
        Optional<ShopWxAuthorized> optional = shopWxService.getAuthorized(appid);
        if(!optional.isPresent()){
            return 0;
        }

        if(!StringUtils.equals(shopId, optional.get().getShopId())){
            return 20001;
        }

        if(shopWxService.queryAuthorized(shopId).size() > 1){
            return 20002;
        }

        return 0;
    }

    private void saveAuthorizerConfigure(String shopId, String authorizerAppid, DeferredResult<ResponseEntity<String>> result){
        clientService.obtainAuthorizerInfo(authorizerAppid).subscribe(e -> {
            if(isSuccess(e.getCode())){
                ShopWxAuthorized t = new ShopWxAuthorized();
                t.setAppid(authorizerAppid);
                t.setShopId(shopId);
                t.setAuthorization(true);
                t.setQrcodeUrl(e.getQrcodeUrl());
                t.setName(e.getPrincipalName());
                t.setNickname(e.getNickname());
                t.setUsername(e.getUsername());
                t.setHeadImg(e.getHeadImg());
                t.setVerifyTypeInfo(e.getVerifyTypeInfo());
                t.setServiceTypeInfo(e.getServiceTypeInfo());
                t.setAuthorizationInfo(toJson(e.getAuthorizationInfo()));
                t.setMiniProgramInfo(toJson(e.getMiniProgramInfo()));
                t.setBusinessInfo(toJson(e.getBusinessInfo()));

                shopWxService.saveAuthorized(t);

                String uri = properties.getSuccessUri() + "?appid=" + authorizerAppid;
                redirectUri(result, uri);
            }else {
                LOGGER.error("Save authorizer info fail, appid {} errCode {}", authorizerAppid, e.getCode());
                String uri = properties.getFailUri() + "?code=" + 20000;
                redirectUri(result, uri);
            }
        });
    }

    private void redirectUri(DeferredResult<ResponseEntity<String>> result, String uri){
        result.setResult(ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).header(HttpHeaders.LOCATION, uri).build());
    }

    private String toJson(Map<String,Object> data){
        try{
            return objectMapper.writeValueAsString(data);
        }catch (IOException e){
            LOGGER.error("To json fail, error is {}", e.getMessage());
            return StringUtils.EMPTY;
        }
    }

    private void noticeInitMessageTemplate(String appid){
        applicationContext.publishEvent(new InitSmallTemplateEvent(this, appid));
    }

    @RequestMapping(value="/authorizer/event/{appid}")
    public ResponseEntity<String> organEvent(@PathVariable(value="appid") String appid,
                                     @RequestParam(value="signature") String signature,
                                     @RequestParam(value="timestamp") String timestamp,
                                     @RequestParam(value="nonce") String nonce,
                                     @RequestParam(value="encrypt_type") String encryptType,
                                     @RequestParam(value="msg_signature") String msgSignature,
                                     @RequestBody byte[] bytes){

        LOGGER.debug("Appid is {} ,timestamp is {}, nonce is {}, signature is {}", appid, timestamp, nonce, msgSignature);

        String body = new String(bytes, Charset.forName("UTF-8"));
        LOGGER.debug("Wx event Body is {}", body);

        try{
            String decBody = encrypt.decryptMsg(msgSignature, timestamp, nonce, body);
            LOGGER.debug("Desc body is {}", decBody);
            String m = smallEventHandlers.handler(appid, decBody);
            return ResponseEntity.ok(encodeMessage(encrypt, m));
        }catch(WxEncryptException e){
            LOGGER.debug("Receive message is fail, error is {}", e.getMessage());
            return ResponseEntity.ok("");
        }
    }

    /**
     * 消息加密
     *
     * @param encrypt 加密对象
     * @param message 消息
     * @return
     */
    private String encodeMessage(WxEncrypt encrypt, String message)throws WxEncryptException{
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonce = SecurityUtils.randomStr(16);

        String e = encrypt.encryptMsg(message, timeStamp, nonce);
        LOGGER.debug("Encrypt content is {}", e);
        return e;
    }
}
