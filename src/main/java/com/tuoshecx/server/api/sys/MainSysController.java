package com.tuoshecx.server.api.sys;

import com.tuoshecx.server.api.form.LoginForm;
import com.tuoshecx.server.api.vo.LoginVo;
import com.tuoshecx.server.api.vo.ResultVo;
import com.tuoshecx.server.security.Credential;
import com.tuoshecx.server.security.token.TokenService;
import com.tuoshecx.server.base.domain.Sys;
import com.tuoshecx.server.base.service.SysService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 系统管理基础接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/sys")
@Api(value = "/sys", tags = "S-系统管理基础接口", position = 100)
public class MainSysController {
    private final TokenService tokenService;
    private final SysService sysService;

    @Autowired
    public MainSysController(TokenService tokenService, SysService sysService) {
        this.tokenService = tokenService;
        this.sysService = sysService;
    }

    @PostMapping(value = "login", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("系统管理登陆接口")
    public ResultVo<LoginVo<Sys>> login(@Valid @RequestBody LoginForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        Sys t = sysService.getValidate(form.getUsername(), form.getPassword());
        String token = createToken(t);
        return ResultVo.success(new LoginVo<>(token, t));
    }

    private String createToken(Sys t){
        List<String> roles = Collections.unmodifiableList(Arrays.asList(t.getRoles()));
        List<Credential.Attach> attaches = Collections.emptyList();
        Credential c = new Credential(t.getId(), "base", "sys", roles, attaches);
        return tokenService.generate(c);
    }
}
