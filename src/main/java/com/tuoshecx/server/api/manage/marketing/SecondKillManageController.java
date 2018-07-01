package com.tuoshecx.server.api.manage.marketing;

import com.tuoshecx.server.api.manage.CredentialContextManageUtils;
import com.tuoshecx.server.api.manage.marketing.form.SecondKillSaveForm;
import com.tuoshecx.server.api.manage.marketing.form.SecondKillUpdateForm;
import com.tuoshecx.server.api.vo.OkVo;
import com.tuoshecx.server.api.vo.ResultPageVo;
import com.tuoshecx.server.api.vo.ResultVo;
import com.tuoshecx.server.marketing.domain.SecondKill;
import com.tuoshecx.server.marketing.service.SecondKillService;
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
 * 秒杀活动设置API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/manage/marketing/secondKill")
@Api(value = "/manage/marketing/secondKill", tags = "M-秒杀营销活动设置API接口")
public class SecondKillManageController {
    @Autowired
    private SecondKillService service;

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("新增秒杀活动")
    public ResultVo<SecondKill> save(@Valid @RequestBody SecondKillSaveForm form, BindingResult result){

        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(service.save(form.toDomain(currentShopId())));
    }

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改秒杀活动")
    public ResultVo<SecondKill> update(@Valid @RequestBody SecondKillUpdateForm form, BindingResult result){

        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(service.update(form.toDomain(currentShopId())));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到秒杀活动")
    public ResultVo<SecondKill> get(@PathVariable("id") String id){

        return ResultVo.success(service.get(id));
    }

    @PutMapping(value = "{id}/open", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("上架秒杀活动")
    public ResultVo<SecondKill> open(@PathVariable("id") String id){

        return ResultVo.success(service.open(id, currentShopId()));
    }

    @PutMapping(value = "{id}/close", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("下架秒杀活动")
    public ResultVo<SecondKill> close(@PathVariable("id") String id){

        return ResultVo.success(service.close(id, currentShopId()));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("删除秒杀活动")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        boolean ok = service.delete(id, currentShopId());
        return ResultVo.success(new OkVo(ok));
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询秒杀活动")
    public ResultPageVo<SecondKill> query(@RequestParam(required = false) @ApiParam("营销活动名") String name,
                                          @RequestParam(required = false) @ApiParam(value = "是否开启") Boolean isOpen,
                                          @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
                                          @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                          @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        String shopId = currentShopId();
        List<SecondKill> data = service.query(shopId, name, isOpen, page * rows, rows);
        return new ResultPageVo.Builder<>(page, rows, data)
                .count(isCount, () -> service.count(shopId, name, isOpen))
                .build();

    }

    private String currentShopId(){
        return CredentialContextManageUtils.currentShopId();
    }
}
