package com.linku.server.api.client.wx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linku.server.BaseException;
import com.linku.server.api.client.CredentialContextClientUtils;
import com.linku.server.api.client.wx.form.PhoneForm;
import com.linku.server.api.client.wx.form.SaveUserForm;
import com.linku.server.api.vo.OkVo;
import com.linku.server.api.vo.ResultVo;
import com.linku.server.security.Credential;
import com.linku.server.security.token.TokenService;
import com.linku.server.user.domain.User;
import com.linku.server.user.service.UserService;
import com.linku.server.wx.small.WxSmallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 微信小程序相关API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/client/wx/small")
@Api(value = "/client/wx/small", tags = "C-微信小程序Api接口")
public class WxSmallController {
    private static final Logger logger = LoggerFactory.getLogger(WxSmallController.class);
    private static final Map<Integer, String> SEX_MAP = initSexMap();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserService service;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private WxSmallService wxService;

    @PostMapping(value = "saveUser", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("保存微信小程序用户")
    public ResultVo<User> saveUser(
            @RequestHeader(value = "token", required = false) String headToken,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String headAuthorization,
            @Validated @RequestBody SaveUserForm form, BindingResult result){

        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        String openid = currentOpenid();
        logger.debug("Save user openid is {}", openid);

        if(!wxService.isSignature(openid, form.getRawData(), form.getSignature())){
            logger.info("Signature fail, Wx signature is {}", form.getSignature());
            return ResultVo.error(new String[]{"数据签名错误"});
        }

        Optional<String> data = wxService.decrypt(openid, form.getEncryptedData(), form.getIv());
        if(!data.isPresent()){
            return ResultVo.error(new String[]{"数据解密错误"});
        }

        logger.info("User is {}", data.get());
        User t = service.saveWx(toUser(data.get()));

        if(isTempUser()){
            updateCredential(getToken(headToken, headAuthorization), t);
        }

        return ResultVo.success(t);
    }

    private User toUser(String v) {
        Map<String, Object> map = parseJson(v);

        User t = new User();
        t.setOpenid((String)map.get("openId"));
        t.setNickname((String)map.getOrDefault("nickName", ""));
        t.setProvince((String)map.getOrDefault("province", ""));
        t.setCity((String)map.getOrDefault("city", ""));
        t.setCountry((String)map.getOrDefault("country", ""));
        t.setHeadImg((String)map.getOrDefault("avatarUrl", ""));
        t.setUnionid((String)map.getOrDefault("unionId", ""));
        Integer gender = (Integer) map.get("gender");
        t.setSex(SEX_MAP.get(gender));
        Map watermark = (Map)map.get("watermark");
        t.setAppid((String)watermark.get("appid"));

        return t;
    }

    private Map<String, Object> parseJson(String v){
        try{
            return objectMapper.readValue(
                    v, objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class));
        }catch (Exception e){
            logger.error("Parse user info fail, error is {}", e.getMessage());
            throw new BaseException("JSON数据格式错误");
        }
    }

    private String getToken(String headToken, String headAuthorization) {
        return StringUtils.isNotBlank(headToken)? headToken:
                (StringUtils.isBlank(headAuthorization)? "": StringUtils.removeStart(headAuthorization, "Bearer").trim());
    }

    private void updateCredential(String toke, User user){

        if(StringUtils.isBlank(toke)){
            throw new BaseException("用户认证错误");
        }

        tokenService.update(toke, buildCredential(user));
    }

    private Credential buildCredential(User t){

        final List<String> roles = Collections.singletonList("ROLE_USER");
        final List<Credential.Attach> attaches = new ArrayList<>(3);
        attaches.add(new Credential.Attach(CredentialContextClientUtils.SHOP_ID_KEY, t.getShopId()));
        attaches.add(new Credential.Attach(CredentialContextClientUtils.OPENID_KEY, t.getOpenid()));

        return new Credential(t.getId(), "wx", "user", roles, Collections.unmodifiableList(attaches));
    }

    @PostMapping(value = "phone", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("通过微信小程序获取用手机号")
    public ResultVo<OkVo> phone(@Validated @RequestBody PhoneForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        Optional<String> optional = wxService.decrypt(currentOpenid(), form.getEncryptedData(), form.getIv());
        if(!optional.isPresent()){
            return ResultVo.error(new String[]{"数据解密错误"});
        }

        Map<String, Object> map = parseJson(optional.get());
        String phone = (String)map.get("phoneNumber");
        User t = service.get(credential().getId());
        t.setPhone(phone);
        service.update(t);
        return ResultVo.success(new OkVo(true));
    }

    private String currentOpenid(){
        return CredentialContextClientUtils.currentOpenid();
    }

    private boolean isTempUser(){
        Credential credential = CredentialContextClientUtils.getCredential();
        return CredentialContextClientUtils.isTemp(credential.getType());
    }

    private Credential credential(){
        return CredentialContextClientUtils.getCredential();
    }

    private static Map<Integer, String> initSexMap(){
        Map<Integer, String> map = new HashMap<>(6);
        map.put(0, "未知");
        map.put(1, "男");
        map.put(2, "女");

        return Collections.unmodifiableMap(map);
    }
}
