package com.linku.server.api.manage.game;

import com.linku.server.BaseException;
import com.linku.server.api.manage.CredentialContextManageUtils;
import com.linku.server.api.manage.game.form.BigWheelSaveForm;
import com.linku.server.api.manage.game.form.BigWheelUpdateForm;
import com.linku.server.api.manage.game.vo.BigWheelVo;
import com.linku.server.api.vo.OkVo;
import com.linku.server.api.vo.ResultPageVo;
import com.linku.server.api.vo.ResultVo;
import com.linku.server.game.wheel.domain.BigWheel;
import com.linku.server.game.wheel.domain.BigWheelItem;
import com.linku.server.game.wheel.service.BigWheelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/game/wheel")
@Api(value = "/game/wheel", tags = "M-大转盘游戏管理API接口")
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

        BigWheel t = service.save(form.toDomain(), toItems(form));

        return get(t.getId());
    }

    private List<BigWheelItem> toItems(BigWheelSaveForm form){
        List<BigWheelItem> items = new ArrayList<>();
        for(int i = 0; i < form.getItems().size(); i++){
            BigWheelSaveForm.BigWheelSaveItemForm t = form.getItems().get(i);
            BigWheelItem item = new BigWheelItem();

            item.setIndex(i);
            item.setMoney(t.getMoney());
            item.setTitle(t.getTitle());
            item.setRatio(t.getRatio());

            items.add(item);
        }

        return items;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改大转盘游戏")
    public ResultVo<BigWheelVo> update(@Validated @RequestBody BigWheelUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        BigWheel t = service.update(form.toDomain(), toItems(form));

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
                                     @RequestParam(required = false) @ApiParam("状态") String state,
                                     @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
                                     @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                     @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        final String shopId = currentShopId();
        final BigWheel.State stateObj;
        try{
            stateObj = BigWheel.State.valueOf(state);
        }catch (Exception e){
            throw new BaseException("状态不存在");
        }

        return new ResultPageVo.Builder<>(page, rows, service.query(shopId, name, stateObj,page * rows, rows))
                .count(isCount, () -> service.count(shopId, name, stateObj))
                .build();
    }

    private String currentShopId(){
        return CredentialContextManageUtils.currentShopId();
    }
}
