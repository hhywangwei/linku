package com.linku.server.api.wx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linku.server.BaseException;
import com.linku.server.api.vo.ResultVo;
import com.linku.server.shop.domain.Shop;
import com.linku.server.shop.domain.ShopWxConfigure;
import com.linku.server.shop.domain.ShopWxToken;
import com.linku.server.shop.service.ShopService;
import com.linku.server.shop.service.ShopWxService;
import com.linku.server.wx.component.client.ComponentClientService;
import com.linku.server.wx.component.client.response.ObtainQueryAuthResponse;
import com.linku.server.wx.component.encrypt.WxEncrypt;
import com.linku.server.wx.component.encrypt.WxEncryptException;
import com.linku.server.wx.component.event.ComponentEventHandlers;
import com.linku.server.wx.configure.properties.WxComponentProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private WxComponentProperties properties;
    private final ComponentEventHandlers eventHandlers;
    private final ComponentClientService clientService;
    private final ShopService shopService;
    private final ShopWxService shopWxService;
    private final ObjectMapper objectMapper;
    private final WxEncrypt crypt;
    private final Charset charsetUTF = Charset.forName("UTF-8");

    @Autowired
    public WxComponentController(WxComponentProperties properties,
                                 ComponentEventHandlers eventHandlers,
                                 ComponentClientService clientService,
                                 ShopService shopService,
                                 ShopWxService shopWxService,
                                 ObjectMapper objectMapper) {

        this.properties = properties;
        this.eventHandlers = eventHandlers;
        this.clientService = clientService;
        this.shopService = shopService;
        this.shopWxService = shopWxService;
        this.objectMapper = objectMapper;
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

            if(isSuccess(e.getCode())){
                saveAuthorizerToken(shopId, e);
                saveAuthorizerConfigure(shopId, e.getAuthorizerAppid(), result);
            }else{
                LOGGER.error("Obtain token fail, shopId {} code {} message {}", shopId, e.getCode(), e.getMessage());
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
        Optional<ShopWxConfigure> optional = shopWxService.getConfigure(appid);
        if(!optional.isPresent()){
            return 0;
        }

        if(!StringUtils.equals(shopId, optional.get().getShopId())){
            return 20001;
        }

        if(shopWxService.queryByShopId(shopId).size() > 1){
            return 20002;
        }

        return 0;
    }

    private void saveAuthorizerConfigure(String shopId, String authorizerAppid, DeferredResult<ResponseEntity<String>> result){
        clientService.obtainAuthorizerInfo(authorizerAppid).subscribe(e -> {
            if(isSuccess(e.getCode())){
                ShopWxConfigure t = new ShopWxConfigure();
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

                shopWxService.saveConfigure(t);
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

    @RequestMapping(value="/authorizer/event/{appid}")
    public ResponseEntity<String> organEvent(@PathVariable(value="appid") String appid,
                                     @RequestParam(value="signature") String signature,
                                     @RequestParam(value="timestamp") String timestamp,
                                     @RequestParam(value="nonce") String nonce,
                                     @RequestParam(value="encrypt_type") String encryptType,
                                     @RequestParam(value="msg_signature") String msgSignature,
                                     @RequestBody(required=true) byte[] bytes){

        LOGGER.debug("Appid is {} ,timestamp is {}, nonce is {}, signature is {}", appid, timestamp, nonce, msgSignature);

        String body = new String(bytes, Charset.forName("UTF-8"));
        LOGGER.debug("Wx event Body is {}", body);

        try{
            String decBody = crypt.decryptMsg(msgSignature, timestamp, nonce, body);
            LOGGER.debug("Desc body is {}", decBody);
//            return organEventHandler.handler(decBody);
            return ResponseEntity.ok("success");
        }catch(WxEncryptException e){
            LOGGER.debug("Receive message is fail, error is {}", e.getMessage());
            return ResponseEntity.ok("fail");
        }
    }
}
