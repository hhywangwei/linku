package com.tuoshecx.server.api.client;

import com.tuoshecx.server.api.client.form.RegisterForm;
import com.tuoshecx.server.api.client.form.WxLoginForm;
import com.tuoshecx.server.api.form.LoginForm;
import com.tuoshecx.server.api.vo.LoginVo;
import com.tuoshecx.server.api.vo.ResultVo;
import com.tuoshecx.server.security.Credential;
import com.tuoshecx.server.security.token.TokenService;
import com.tuoshecx.server.user.domain.User;
import com.tuoshecx.server.user.service.UserService;
import com.tuoshecx.server.wx.small.WxSmallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 客户端基础API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/client")
@Api(value = "/client", tags = "客户端基础API接口")
public class MainClientController {

    @Autowired
    private UserService service;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private WxSmallService wxService;

    @PostMapping(value = "wxLogin", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "微信小程序用户登陆")
    public Mono<ResultVo<LoginVo<User>>> wxLogin(@Validated @RequestBody WxLoginForm form, BindingResult result){
        if(result.hasErrors()){
            return Mono.create(e -> ResultVo.error(result.getAllErrors()));
        }

        return wxService.login(form.getCode())
                .map(e -> e.map(this::loginOpenid).orElseGet(()-> ResultVo.error(100, "用登陆失败")));
    }

    private ResultVo<LoginVo<User>> loginOpenid(String openid){
        return service.getByOpenid(openid)
                .map(e -> ResultVo.success(new LoginVo<>(createToken(e, "wx", "user"), e)))
                .orElseGet(()-> {
                    User u = tempUser(openid);
                    return ResultVo.success(new LoginVo<>(createToken(u, "wx", "temp"), u));
                });
    }

    private String createToken(User t, String enter, String type){
        final List<String> roles = Collections.singletonList("ROLE_USER");
        final List<Credential.Attach> attaches = new ArrayList<>(3);
        if(!isTemp(type)){
            attaches.add(new Credential.Attach(CredentialContextClientUtils.SHOP_ID_KEY, t.getShopId()));
        }
        if(isWxEnter(enter)){
            attaches.add(new Credential.Attach(CredentialContextClientUtils.OPENID_KEY, t.getOpenid()));
        }
        return tokenService.generate(new Credential(t.getId(),
                enter, type, roles, Collections.unmodifiableList(attaches)));
    }

    private boolean isTemp(String type){
        return StringUtils.equals("temp", type);
    }

    private boolean isWxEnter(String enter){
        return StringUtils.equals("wx", enter);
    }

    private User tempUser(String openid){
        User t = new User();
        t.setOpenid(openid);
        return t;
    }

    @PostMapping(value = "login", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("用户登陆")
    public ResultVo<LoginVo<User>> login(@Validated @RequestBody LoginForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        User user = service.getValidate(form.getUsername(), form.getPassword());
        String token = createToken(user,"base", "user");
        return ResultVo.success(new LoginVo<>(token, user));
    }

    @PostMapping(value = "register", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("注册用户")
    public ResultVo<User> register(@Validated @RequestBody RegisterForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        User user = service.saveUsername(form.getShopId(), form.getUsername(), form.getPassword());
        return ResultVo.success(user);
    }
}
