package com.linku.server.api.sys.shop;

import com.linku.server.BaseException;
import com.linku.server.api.form.PasswordResetForm;
import com.linku.server.api.sys.shop.form.IncTryUseTimeForm;
import com.linku.server.api.vo.OkVo;
import com.linku.server.api.vo.ResultPageVo;
import com.linku.server.api.vo.ResultVo;
import com.linku.server.api.sys.shop.form.ShopSaveForm;
import com.linku.server.api.sys.shop.form.ShopUpdateForm;
import com.linku.server.base.domain.Sys;
import com.linku.server.base.service.SysService;
import com.linku.server.shop.domain.Manager;
import com.linku.server.shop.domain.Shop;
import com.linku.server.shop.service.ManagerService;
import com.linku.server.shop.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Date;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 管理端店铺管理API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/sys/shop")
@Api(value = "/sys/shop", tags = "S-店铺管理API接口", position = 100)
public class ShopSysController {
    private final ShopService service;
    private final ManagerService managerService;

    @Autowired
    public ShopSysController(ShopService service, ManagerService managerService){
        this.service = service;
        this.managerService = managerService;
    }

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "新增店铺")
    public ResultVo<Shop> save(@Valid @RequestBody ShopSaveForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        Shop t = form.toDomain();
        validateTryTime(t);
        return ResultVo.success(service.save(t, form.getManager(), form.getPassword()));
    }

    private void validateTryTime(Shop t){
        if(t.getTryUse()){
            if(t.getTryToTime() == null){
                throw new BaseException("使用结束日期不能未空");
            }
            if(t.getTryFromTime() == null){
                t.setTryFromTime(new Date());
            }
            if(t.getTryFromTime().after(t.getTryToTime())){
                throw new BaseException("开始日期大于结束日期");
            }
        }else{
            t.setTryFromTime(null);
            t.setTryToTime(null);
        }
    }

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "修改店铺信息")
    public ResultVo<Shop> update(@Valid @RequestBody ShopUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        Shop t = form.toDomain();
        return ResultVo.success(service.update(t));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "得到店铺")
    public ResultVo<Shop> get(@ApiParam(value = "店铺编号", required = true) @PathVariable("id") String id){
        return ResultVo.success(service.get(id));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "删除店铺")
    public ResultVo<OkVo> delete(@ApiParam(value = "店铺编号", required = true) @PathVariable("id") String id){
        return ResultVo.success(new OkVo(service.delete(id)));
    }

    @PutMapping(value = "{id}/open", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "开放店铺")
    public ResultVo<Shop> open(@ApiParam(value = "店铺编号", required = true) @PathVariable("id") String id){
        return ResultVo.success(service.open(id));
    }

    @PutMapping(value = "{id}/close", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "关闭店铺")
    public ResultVo<Shop> close(@ApiParam(value = "店铺编号", required = true) @PathVariable("id") String id){
        return ResultVo.success(service.close(id));
    }

    @PutMapping(value = "{id}/openFormal", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "开通正式店铺，店铺由测试店铺改为正式店铺")
    public ResultVo<Shop> openFormal(@PathVariable("id") String id){
        return ResultVo.success(service.openFormal(id));
    }

    @PutMapping(value = "incTryUseTime", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "延长试用时间")
    public ResultVo<Shop> incTryUseTime(@Validated @RequestBody IncTryUseTimeForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(service.incTryUseTime(form.getId(), form.getTryToTime()));
    }

    @GetMapping(value = "{id}/getManager", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "得到店铺管理员")
    public ResultVo<Manager> getManager(@PathVariable("id") String id){
        return ResultVo.success(managerService.getShopManager(id));
    }

    @PostMapping(value = "resetPassword", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "重置店铺管理员密码")
    public ResultVo<OkVo> resetPassword(@Validated @RequestBody PasswordResetForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        Manager t = managerService.get(form.getId());
        boolean ok = managerService.resetPassword(t.getId(), t.getShopId(), form.getNewPassword());
        return ResultVo.success(new OkVo(ok));
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "查询店铺")
    public ResultPageVo<Shop> query(
            @ApiParam(value = "店铺名称") @RequestParam(required = false) String name,
            @ApiParam(value = "联系电话") @RequestParam(required = false) String phone,
            @ApiParam(value = "省份") @RequestParam(required = false) String province,
            @ApiParam(value = "城市") @RequestParam(required = false) String city,
            @ApiParam(value = "县") @RequestParam(required = false)String county,
            @ApiParam(value = "地址") @RequestParam(required = false)String address,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        return new ResultPageVo
                .Builder<>(page, rows, service.query(name, phone, province, city, county, address, page * rows, rows))
                .count(isCount, () -> service.count(name, phone, province, city, county, address))
                .build();
    }

}
