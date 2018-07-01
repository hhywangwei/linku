package com.tuoshecx.server.api.manage.manager;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.api.form.PasswordResetForm;
import com.tuoshecx.server.api.form.PasswordUpdateForm;
import com.tuoshecx.server.api.manage.CredentialContextManageUtils;
import com.tuoshecx.server.api.manage.manager.form.ManagerSaveForm;
import com.tuoshecx.server.api.manage.manager.form.ManagerUpdateForm;
import com.tuoshecx.server.api.vo.OkVo;
import com.tuoshecx.server.api.vo.ResultPageVo;
import com.tuoshecx.server.api.vo.ResultVo;
import com.tuoshecx.server.shop.domain.Manager;
import com.tuoshecx.server.shop.service.ManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 店员管理API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("manage/manager")
@Api(value = "manage/manager", tags = "M-店员管理API接口")
public class ManagerManageController {
    private final ManagerService service;

    @Autowired
    public ManagerManageController(ManagerService service) {
        this.service = service;
    }

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("新增店员")
    public ResultVo<Manager> save(@Valid @RequestBody ManagerSaveForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(service.save(form.toDomain(currentShopId())));
    }

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改店员")
    public ResultVo<Manager> update(@Valid @RequestBody ManagerUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(service.update(form.toDomain(), currentShopId()));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到店员")
    public ResultVo<Manager> get(@PathVariable("id")String id){

        Manager t = service.get(id);
        if(!StringUtils.equals(t.getShopId(), currentShopId())){
            throw new BaseException("雇员不存在");
        }
        return ResultVo.success(t);
    }

    @PutMapping(value = "{id}/active", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("激活店员")
    public ResultVo<Manager> active(@PathVariable("id")String id){
        try{
            return ResultVo.success(service.active(id, currentShopId()));
        }catch (BaseException e){
            return ResultVo.error(e.getCode(), e.getMessage());
        }
    }

    @PutMapping(value = "{id}/inactive", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("禁用店员")
    public ResultVo<Manager> inactive(@PathVariable("id")String id){
        try{
            return ResultVo.success(service.inactive(id, currentShopId()));
        }catch (BaseException e){
            return ResultVo.error(e.getCode(), e.getMessage());
        }
    }

    @PutMapping(value = "updatePassword", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改店员密码")
    public ResultVo<OkVo> updatePassword(@Valid @RequestBody PasswordUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        boolean ok = service.updatePassword(
                form.getId(), currentShopId(), form.getPassword(), form.getNewPassword());
        return ResultVo.success(new OkVo(ok));
    }

    @PutMapping(value = "resetPassword", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("重置店员密码")
    public ResultVo<OkVo> resetPassword(@Valid @RequestBody PasswordResetForm form, BindingResult result){

        boolean ok = service.resetPassword(form.getId(), currentShopId(), form.getNewPassword());
        return ResultVo.success(new OkVo(ok));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("删除店员")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){

        return ResultVo.success(new OkVo(service.delete(id, currentShopId())));
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询店员")
    public ResultPageVo<Manager> query(
            @ApiParam(value = "用户名") @RequestParam(required = false) String username,
            @ApiParam(value = "姓名") @RequestParam(required = false) String name,
            @ApiParam(value = "联系电话") @RequestParam(required = false)String phone,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        String shopId = currentShopId();
        return new ResultPageVo.Builder<>(page, rows, service.query(shopId, username, name, phone, page * rows, rows))
                .count(isCount, () -> service.count(shopId, username, name, phone))
                .build();
    }

    private String currentShopId(){
        return CredentialContextManageUtils.currentShopId();
    }
}
