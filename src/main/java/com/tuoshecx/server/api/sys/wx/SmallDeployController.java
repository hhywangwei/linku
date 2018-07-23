package com.tuoshecx.server.api.sys.wx;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.api.sys.wx.form.SmallDeployForm;
import com.tuoshecx.server.api.sys.wx.vo.ProgramCategoryVo;
import com.tuoshecx.server.api.vo.ResultVo;
import com.tuoshecx.server.wx.small.client.response.GetCategoryResponse;
import com.tuoshecx.server.wx.small.client.response.GetQrcodeResponse;
import com.tuoshecx.server.wx.small.devops.domain.SmallDeploy;
import com.tuoshecx.server.wx.small.devops.service.DeployService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
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

    @GetMapping(value = "{shopId}/getQrcode", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "得到小程序体验二维码")
    public ResponseEntity<byte[]> getQrcode(@PathVariable("shopId")String shopId,
                                            @RequestParam(value = "path", required = false)String path){
        GetQrcodeResponse response = service.getQrcode(shopId, "");

        if(response.isOk()){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.IMAGE_JPEG)
                    .contentLength(response.getContent().length)
                    .body(response.getContent());
        }else{
            byte[] content = String.format("{\"code\":%d, \"message\": \"%s\"}", 1000, "获取体验二维吗失败")
                    .getBytes(Charset.forName("UTF-8"));
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(content);
        }
    }
}
