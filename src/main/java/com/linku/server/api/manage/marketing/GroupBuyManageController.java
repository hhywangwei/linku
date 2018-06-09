package com.linku.server.api.manage.marketing;

import com.linku.server.marketing.domain.GroupBuy;
import com.linku.server.marketing.service.GroupBuyService;
import com.linku.server.api.manage.CredentialContextManageUtils;
import com.linku.server.api.manage.marketing.form.GroupBuySaveForm;
import com.linku.server.api.manage.marketing.form.GroupBuyUpdateForm;
import com.linku.server.api.vo.OkVo;
import com.linku.server.api.vo.ResultPageVo;
import com.linku.server.api.vo.ResultVo;
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
 * 团购活动设置API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/manage/marketing/groupBuy")
@Api(value = "/manage/marketing/groupBuy", tags = "M-团购营销活动设置API接口")
public class GroupBuyManageController {

    @Autowired
    private GroupBuyService service;

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("新增团购活动")
    public ResultVo<GroupBuy> save(@Valid @RequestBody GroupBuySaveForm form, BindingResult result){

        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(service.save(form.toDomain(currentShopId())));
    }

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改团购活动")
    public ResultVo<GroupBuy> update(@Valid @RequestBody GroupBuyUpdateForm form, BindingResult result){

        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(service.update(form.toDomain(currentShopId())));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到团购活动")
    public ResultVo<GroupBuy> get(@PathVariable("id") String id){

        return ResultVo.success(service.get(id));
    }

    @PutMapping(value = "{id}/open", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("上架团购活动")
    public ResultVo<GroupBuy> open(@PathVariable("id") String id){

        return ResultVo.success(service.open(id, currentShopId()));
    }

    @PutMapping(value = "{id}/close", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("下架团购活动")
    public ResultVo<GroupBuy> close(@PathVariable("id") String id){

        return ResultVo.success(service.close(id, currentShopId()));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("删除团购活动")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        boolean ok = service.delete(id, currentShopId());
        return ResultVo.success(new OkVo(ok));
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询团购活动")
    public ResultPageVo<GroupBuy> query(@RequestParam(required = false) @ApiParam("营销活动名") String name,
                                        @RequestParam(required = false) @ApiParam(value = "是否开启") Boolean isOpen,
                                        @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
                                        @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                        @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        String shopId = currentShopId();
        List<GroupBuy> data = service.query(shopId, name, isOpen, page * rows, rows);
        return new ResultPageVo.Builder<>(page, rows, data)
                .count(isCount, () -> service.count(shopId, name, isOpen))
                .build();

    }

    private String currentShopId(){
        return CredentialContextManageUtils.currentShopId();
    }

}
