package com.tuoshecx.server.api.sys.wx;

import com.tuoshecx.server.api.sys.wx.form.DomainConfigureSaveForm;
import com.tuoshecx.server.api.vo.HasVo;
import com.tuoshecx.server.api.vo.ResultVo;
import com.tuoshecx.server.wx.small.devops.domain.DomainConfigure;
import com.tuoshecx.server.wx.small.devops.service.DomainConfigureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 *  小程序域名配置API接口
 *
 * @author <a href="mailto.hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/sys/wx/configure/domain")
@Api(value = "/sys/wx/configure/domain", tags ="S-小程序服务端域名配置")
public class DomainConfigureController {

    @Autowired
    private DomainConfigureService service;

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("保存小程序服务端域名配置")
    public ResultVo<DomainConfigure> save(@Validated @RequestBody DomainConfigureSaveForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(service.update(form.toDomain()));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到小程序服务端域名配置")
    public ResultVo<HasVo<DomainConfigure>> get(){
        return ResultVo.success(service.getOptional().map(HasVo::has).orElse(HasVo.notHas()));
    }

}
