package com.tuoshecx.server.api.sys.wx;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.api.sys.wx.form.SmallDeployForm;
import com.tuoshecx.server.api.sys.wx.vo.ProgramCategoryVo;
import com.tuoshecx.server.api.vo.ResultVo;
import com.tuoshecx.server.wx.small.client.response.GetCategoryResponse;
import com.tuoshecx.server.wx.small.devops.domain.SmallDeploy;
import com.tuoshecx.server.wx.small.devops.service.DeployService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 小程序发布API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/sys/wx/deploy")
public class SmallDeployController {

    @Autowired
    private DeployService service;

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "发布小程序")
    public ResultVo<SmallDeploy> deploy(@Validated @RequestBody SmallDeployForm form, BindingResult result){

        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        SmallDeploy t = service.deploy(form.getShopId(), form.getTemplateId());
        service.setDomain(t.getId());
        service.programCommit(t.getId());
        return ResultVo.success(service.submitAudit(t.getId()));
    }

    @GetMapping(value = "{shopId}/category", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "得到小程序目录")
    public ResultVo<Collection<ProgramCategoryVo>> getCategory(@PathVariable("shopId")String shopId){
        GetCategoryResponse response = service.getCategory(shopId);

        if(!response.isOk()){
            throw new BaseException("得到小程序目录失败");
        }
        Collection<ProgramCategoryVo> data = response.getCategories().stream()
                .map(ProgramCategoryVo::new).collect(Collectors.toList());

        return ResultVo.success(data);
    }
}
