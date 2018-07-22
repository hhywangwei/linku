package com.tuoshecx.server.api.sys.wx;

import com.tuoshecx.server.api.sys.wx.form.ShopWxPaySaveForm;
import com.tuoshecx.server.api.sys.wx.form.ShopWxPayUpdateForm;
import com.tuoshecx.server.api.vo.OkVo;
import com.tuoshecx.server.api.vo.ResultPageVo;
import com.tuoshecx.server.api.vo.ResultVo;
import com.tuoshecx.server.shop.domain.ShopWxPay;
import com.tuoshecx.server.shop.service.ShopWxPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 *  店铺微信支付配置管理
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/sys/wx/configure/pay")
@Api(value = "/sys/wx/configure/pay", tags = "S-店铺微信小程序支付配置")
public class ShopWxPayController {

    @Autowired
    private ShopWxPayService service;

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("新增店铺微信支付配置")
    public ResultVo<ShopWxPay> save(@RequestBody ShopWxPaySaveForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(service.save(form.toDomain()));
    }

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改店铺微信支付配置")
    public ResultVo<ShopWxPay> update(@RequestBody ShopWxPayUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(service.update(form.toDomain()));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到微信支付配置")
    public ResultVo<ShopWxPay> get(@PathVariable("id")String id){
        return ResultVo.success(service.get(id));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("删除微信支付配置")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        return ResultVo.success(new OkVo(service.delete(id)));
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询微信支付配置")
    public ResultPageVo<ShopWxPay> query(@RequestParam(value = "shopId", required = false)String shopId,
                                         @RequestParam(value = "appid", required = false)String appid,
                                         @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                         @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        List<ShopWxPay> data = service.query(shopId, appid, page * rows, rows);
        return new ResultPageVo.Builder<>(page, rows, data)
                .count(true, () -> service.count(shopId, appid))
                .build();
    }
}
