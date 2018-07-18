package com.tuoshecx.server.api.sys.wx;

import com.tuoshecx.server.api.sys.wx.form.SmallAuditConfigureSaveForm;
import com.tuoshecx.server.api.sys.wx.form.SmallAuditConfigureUpdateForm;
import com.tuoshecx.server.api.vo.OkVo;
import com.tuoshecx.server.api.vo.ResultPageVo;
import com.tuoshecx.server.api.vo.ResultVo;
import com.tuoshecx.server.wx.small.devops.domain.SmallAuditConfigure;
import com.tuoshecx.server.wx.small.devops.service.SmallAuditConfigureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 小程序审核配置API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/sys/wx/configure/auditConfigure")
@Api(value = "/sys/wx/configure/auditConfigure", tags = "小程序审核配置")
public class SmallAuditConfigureController {

    @Autowired
    private SmallAuditConfigureService service;

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("新增小程序审核配置")
    public ResultVo<SmallAuditConfigure> save(@Validated @RequestBody SmallAuditConfigureSaveForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(service.save(form.toDomain()));
    }

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改小程序审核配置")
    public ResultVo<SmallAuditConfigure> update(@Validated @RequestBody SmallAuditConfigureUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(service.update(form.toDomain()));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到小程序审核配置")
    public ResultVo<SmallAuditConfigure> get(@PathVariable("id")String id){
        return ResultVo.success(service.get(id));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("删除小程序审核配置")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        return ResultVo.success(new OkVo(service.delete(id)));
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询小程序审核配置")
    public ResultPageVo<SmallAuditConfigure> query(@RequestParam(required = false) String shopId,
                                                   @RequestParam(required = false) String appid,
                                                   @RequestParam(required = false) String title,
                                                   @RequestParam(required = false) String tag,
                                                   @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                                   @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        List<SmallAuditConfigure> data = service.query(shopId, appid, title, tag, page * rows, rows);
        return new ResultPageVo.Builder<>(page, rows, data)
                .count(true, () -> service.count(shopId,appid, title, tag))
                .build();
    }

}
