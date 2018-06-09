package com.linku.server.api.manage.marketing;

import com.linku.server.api.manage.CredentialContextManageUtils;
import com.linku.server.api.manage.marketing.form.PresentSaveForm;
import com.linku.server.api.manage.marketing.form.PresentUpdateForm;
import com.linku.server.api.vo.OkVo;
import com.linku.server.api.vo.ResultPageVo;
import com.linku.server.api.vo.ResultVo;
import com.linku.server.marketing.domain.SharePresent;
import com.linku.server.marketing.service.SharePresentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 多人行活动设置API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/manage/marketing/present")
@Api(value = "/manage/marketing/present", tags = "M-多人行营销活动设置API接口")
public class PresentManageController {

    @Autowired
    private SharePresentService service;

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("新增多人行活动")
    public ResultVo<SharePresent> save(@Valid @RequestBody PresentSaveForm form, BindingResult result){

        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(service.save(form.toDomain(currentShopId())));
    }

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改多人行活动")
    public ResultVo<SharePresent> update(@Valid @RequestBody PresentUpdateForm form, BindingResult result){

        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(service.update(form.toDomain(currentShopId())));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到多人行活动")
    public ResultVo<SharePresent> get(@PathVariable("id") String id){

        return ResultVo.success(service.get(id));
    }

    @PutMapping(value = "{id}/open", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("上架多人行活动")
    public ResultVo<SharePresent> open(@PathVariable("id") String id){

        return ResultVo.success(service.open(id, currentShopId()));
    }

    @PutMapping(value = "{id}/close", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("下架多人行活动")
    public ResultVo<SharePresent> close(@PathVariable("id") String id){

        return ResultVo.success(service.close(id, currentShopId()));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("删除多人行活动")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        boolean ok = service.delete(id, currentShopId());
        return ResultVo.success(new OkVo(ok));
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询多人行活动")
    public ResultPageVo<SharePresent> query(@RequestParam(required = false) @ApiParam("营销活动名") String name,
                                            @RequestParam(required = false) @ApiParam(value = "是否开启") Boolean isOpen,
                                            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
                                            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        String shopId = currentShopId();
        List<SharePresent> data = service.query(shopId, name, isOpen, page * rows, rows);
        return new ResultPageVo.Builder<>(page, rows, data)
                .count(isCount, () -> service.count(shopId, name, isOpen))
                .build();

    }

    private String currentShopId(){
        return CredentialContextManageUtils.currentShopId();
    }
}
