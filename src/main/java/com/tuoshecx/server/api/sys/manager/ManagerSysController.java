package com.tuoshecx.server.api.sys.manager;

import com.tuoshecx.server.api.vo.ResultPageVo;
import com.tuoshecx.server.api.form.PasswordResetForm;
import com.tuoshecx.server.api.sys.manager.form.ManagerSaveForm;
import com.tuoshecx.server.api.sys.manager.form.ManagerUpdateForm;
import com.tuoshecx.server.api.form.PasswordUpdateForm;
import com.tuoshecx.server.api.vo.OkVo;
import com.tuoshecx.server.api.vo.ResultVo;
import com.tuoshecx.server.base.domain.Sys;
import com.tuoshecx.server.base.service.SysService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 系统管理员API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/sys/manager")
@Api(value = "/sys/manager", tags = "S-系统管理员API接口", position = 100)
public class ManagerSysController {
    private final SysService service;

    @Autowired
    public ManagerSysController(SysService service) {
        this.service = service;
    }

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("新增系统管理员")
    public ResultVo<Sys> save(@Valid @RequestBody ManagerSaveForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(service.save(form.toDomain()));
    }

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改系统管理员")
    public ResultVo<Sys> update(@Valid @RequestBody ManagerUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(service.update(form.toDomain()));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到系统操作员")
    public ResultVo<Sys> get(@PathVariable("id") String id){

        return ResultVo.success(service.get(id));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("删除系统操作员")
    public ResultVo<OkVo> delete(@PathVariable("id") String id){

        return ResultVo.success(new OkVo(service.delete(id)));
    }

    @PutMapping(value = "{id}/active", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("激活系统操作员")
    public ResultVo<Sys> active(@PathVariable("id") String id){

        return ResultVo.success(service.active(id));
    }

    @PutMapping(value = "{id}/inactive", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("禁用系统操作员")
    public ResultVo<Sys> inactive(@PathVariable("id")String id){

        return ResultVo.success(service.inactive(id));
    }

    @PutMapping(value = "updatePassword", consumes = APPLICATION_JSON_UTF8_VALUE, produces =  APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("更新用户密码")
    public ResultVo<OkVo> updatePassword(@Valid @RequestBody PasswordUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(new OkVo(service.updatePassword(form.getId(), form.getPassword(), form.getNewPassword())));
    }

    @PutMapping(value = "resetPassword", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("重置用户密码")
    public ResultVo<OkVo> resetPassword(@Valid @RequestBody PasswordResetForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(new OkVo(service.resetPassword(form.getId(), form.getNewPassword())));
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "查询系统管理员")
    public ResultPageVo<Sys> query(
            @ApiParam(value = "用户名") @RequestParam(required = false) String username,
            @ApiParam(value = "姓名") @RequestParam(required = false) String name,
            @ApiParam(value = "联系电话") @RequestParam(required = false) String phone,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        return new ResultPageVo.Builder<>(page, rows, service.query(username, name, phone, page * rows, rows))
                .count(isCount, () -> service.count(username, name, phone))
                .build();
    }
}
