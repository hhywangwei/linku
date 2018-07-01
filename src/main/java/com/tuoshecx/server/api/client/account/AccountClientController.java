package com.tuoshecx.server.api.client.account;

import com.tuoshecx.server.api.client.CredentialContextClientUtils;
import com.tuoshecx.server.api.client.account.form.AccountUpdateForm;
import com.tuoshecx.server.api.client.account.form.UpdatePasswordClientForm;
import com.tuoshecx.server.api.vo.OkVo;
import com.tuoshecx.server.api.vo.ResultVo;
import com.tuoshecx.server.security.Credential;
import com.tuoshecx.server.user.domain.User;
import com.tuoshecx.server.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 用户账户管理API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/client/account")
@Api(value = "/client/account", tags = "C-用户信息管理API接口")
public class AccountClientController {

    @Autowired
    private UserService service;

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到用户信息")
    public ResultVo<User> get(){
        User u = service.get(getCredential().getId());
        return ResultVo.success(u);
    }

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("更新用户信息")
    public ResultVo<User> update(@Validated @RequestBody AccountUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(service.update(form.toDomain(getCredential().getId())));
    }

    @PutMapping(value = "updatePassword", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("更新用户密码")
    public ResultVo<OkVo> updatePassword(@Validated @RequestBody UpdatePasswordClientForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        boolean ok = service.updatePassword(
                getCredential().getId(), form.getPassword(), form.getNewPassword());
        return ResultVo.success(new OkVo(ok));
    }

    private Credential getCredential(){
        return CredentialContextClientUtils.getCredential();
    }
}
