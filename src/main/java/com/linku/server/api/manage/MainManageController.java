package com.linku.server.api.manage;

import com.linku.server.BaseException;
import com.linku.server.api.form.LoginForm;
import com.linku.server.api.vo.LoginVo;
import com.linku.server.api.vo.ResultVo;
import com.linku.server.security.Credential;
import com.linku.server.security.token.TokenService;
import com.linku.server.shop.domain.Manager;
import com.linku.server.shop.service.ManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 店铺管理基础接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("manage")
@Api(value = "manage", tags = "M-店铺管理基础接口")
public class MainManageController {
    private static final Logger logger = LoggerFactory.getLogger(MainManageController.class);

    @Autowired
    private ManagerService service;

    @Autowired
    private TokenService tokenService;


    @PostMapping(value = "login", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("店铺管理登陆")
    public ResultVo<LoginVo<Manager>> login(@Valid @RequestBody LoginForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        try{
            Manager o = service.getValidate(form.getUsername(), form.getPassword());
            String  token = createToken(o);
            return ResultVo.success(new LoginVo<>(token, o));
        }catch (BaseException e){
            return ResultVo.error(e.getCode(), e.getMessage());
        }
    }


    private String createToken(Manager t){
        List<String> roles = Collections.unmodifiableList(Arrays.asList(t.getRoles()));
        List<Credential.Attach> attaches = Collections.singletonList(
                new Credential.Attach(CredentialContextManageUtils.SHOP_ID_KEY, t.getShopId()));
        Credential c = new Credential(t.getId(), "base", "manager", roles, attaches);
        return tokenService.generate(c);
    }
}
