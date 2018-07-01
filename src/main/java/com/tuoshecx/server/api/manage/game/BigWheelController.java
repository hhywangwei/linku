package com.tuoshecx.server.api.manage.game;

import com.tuoshecx.server.api.manage.CredentialContextManageUtils;
import com.tuoshecx.server.api.manage.game.form.BigWheelSaveForm;
import com.tuoshecx.server.api.manage.game.form.BigWheelUpdateForm;
import com.tuoshecx.server.api.manage.game.vo.BigWheelVo;
import com.tuoshecx.server.api.vo.OkVo;
import com.tuoshecx.server.api.vo.ResultPageVo;
import com.tuoshecx.server.api.vo.ResultVo;
import com.tuoshecx.server.game.wheel.domain.BigWheel;
import com.tuoshecx.server.game.wheel.domain.BigWheelItem;
import com.tuoshecx.server.game.wheel.service.BigWheelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/manage/game/wheel")
@Api(value = "/manage/game/wheel", tags = "M-大转盘游戏管理API接口")
public class BigWheelController {

    @Autowired
    private BigWheelService service;


    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到大转盘游戏信息")
    public ResultVo<BigWheelVo> get(@PathVariable("id")String id){

        BigWheel t = service.get(id);
        List<BigWheelItem> items = service.getItems(id);

        return ResultVo.success(new BigWheelVo(t, items));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("创建大转盘游戏")
    public ResultVo<BigWheelVo> save(@Validated @RequestBody BigWheelSaveForm form, BindingResult result){

        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        BigWheel t = service.save(form.toDomain(currentShopId()), form.toDomainItems());

        return get(t.getId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改大转盘游戏")
    public ResultVo<BigWheelVo> update(@Validated @RequestBody BigWheelUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        BigWheel t = service.update(form.toDomain(currentShopId()), form.toDomainItems());

        return get(t.getId());
    }

    @PutMapping(value = "{id}/open", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("打开大转盘游戏")
    public ResultVo<BigWheelVo> open(@PathVariable("id")String id){
        BigWheel t = service.open(id, currentShopId());

        return get(t.getId());
    }

    @PutMapping(value = "{id}/close", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("关闭大转盘游戏")
    public ResultVo<BigWheelVo> close(@PathVariable("id")String id){
        BigWheel t = service.close(id, currentShopId());

        return get(t.getId());
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("删除大转盘游戏")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        boolean ok = service.delete(id, currentShopId());
        return ResultVo.success(new OkVo(ok));
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询大转盘游戏")
    public ResultPageVo<BigWheel> query(@RequestParam(required = false) @ApiParam("名称") String name,
                                     @RequestParam(required = false) @ApiParam("状态") BigWheel.State state,
                                     @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
                                     @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                     @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        final String shopId = currentShopId();
//        final BigWheel.State stateObj;
//        try{
//            if(StringUtils.isNotBlank(state)){
//                stateObj = BigWheel.State.valueOf(state);
//            }
//        }catch (Exception e){
//            throw new BaseException("状态不存在");
//        }

        return new ResultPageVo.Builder<>(page, rows, service.query(shopId, name, state,page * rows, rows))
                .count(isCount, () -> service.count(shopId, name, state))
                .build();
    }

    private String currentShopId(){
        return CredentialContextManageUtils.currentShopId();
    }
}
