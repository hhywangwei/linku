package com.tuoshecx.server.api.sys.wx;

import com.tuoshecx.server.api.sys.wx.form.SmallDeployForm;
import com.tuoshecx.server.api.vo.ResultVo;
import com.tuoshecx.server.wx.small.devops.domain.SmallDeploy;
import com.tuoshecx.server.wx.small.devops.service.DeployService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/sys/wx/deploy")
public class SmallDeployController {

    @Autowired
    private DeployService service;

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResultVo<SmallDeploy> deploy(@Validated @RequestBody SmallDeployForm form, BindingResult result){
        return ResultVo.success(service.deploy(form.getShopId(), form.getTemplateId()));
    }

    @PutMapping(value = "{id}/domain", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResultVo<SmallDeploy> setDomain(@PathVariable("id")String id){
        return ResultVo.success(service.setDomain(id));
    }

    @PutMapping(value = "{id}/commit", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResultVo<SmallDeploy> commit(@PathVariable("id") String id){
        return ResultVo.success(service.programCommit(id));
    }

    @PutMapping(value = "{id}/audit", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResultVo<SmallDeploy> audit(@PathVariable("id") String id){
        return ResultVo.success(service.submitAudit(id));
    }
}
