package com.tuoshecx.server.api.manage.goods;

import com.tuoshecx.server.api.manage.CredentialContextManageUtils;
import com.tuoshecx.server.api.manage.goods.form.GoodsIncStockForm;
import com.tuoshecx.server.api.manage.goods.form.GoodsSaveForm;
import com.tuoshecx.server.api.manage.goods.form.GoodsUpdateForm;
import com.tuoshecx.server.api.manage.goods.vo.GoodsVo;
import com.tuoshecx.server.api.vo.OkVo;
import com.tuoshecx.server.api.vo.ResultPageVo;
import com.tuoshecx.server.api.vo.ResultVo;
import com.tuoshecx.server.goods.domain.Goods;
import com.tuoshecx.server.goods.domain.GroupItem;
import com.tuoshecx.server.goods.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 店铺商品管理API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/manage/goods")
@Api(value = "/manage/goods", tags = "M-商品管理API接口")
public class GoodsManageController {

    @Autowired
    private GoodsService service;

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到商品")
    public ResultVo<GoodsVo> get(@PathVariable("id")String id){
        Goods t = service.get(id);
        List<GoodsVo.GroupItemVo> groupItems = t.getGroup()?
                service.queryGroupItems(t.getId()).stream().map(this::toGoodsItemVo).collect(Collectors.toList())
                : Collections.emptyList();
        return ResultVo.success(new GoodsVo(t, groupItems));
    }

    private GoodsVo.GroupItemVo toGoodsItemVo(GroupItem t){
        return new GoodsVo.GroupItemVo(service.get(t.getItemGoodsId()), t.getCount());
    }

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("新增商品")
    public ResultVo<GoodsVo> save(@Validated @RequestBody GoodsSaveForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        Goods o = service.save(form.toDomain(currentShopId()), form.toGroupItems());
        return get(o.getId());
    }

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改商品")
    public ResultVo<GoodsVo> update(@Validated @RequestBody GoodsUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        Goods o = service.update(form.toDomain(currentShopId()), form.toGroupItems());
        return get(o.getId());
    }

    @PutMapping(value = "{id}/open", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("商品上级")
    public ResultVo<GoodsVo> open(@PathVariable("id")String id){
        Goods o = service.open(id, currentShopId());
        return get(o.getId());
    }

    @PutMapping(value = "{id}/close", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("商品下架")
    public ResultVo<GoodsVo> close(@PathVariable("id")String id){
        Goods o = service.close(id, currentShopId());
        return get(o.getId());
    }

    @PutMapping(value = "incStock", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("新增商品库存")
    public ResultVo<GoodsVo> incStock(@Validated @RequestBody GoodsIncStockForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        Goods goods = service.incStock(form.getId(), currentShopId(), form.getCount());
        return get(goods.getId());
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("删除商品")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        boolean ok = service.delete(id, currentShopId());
        return ResultVo.success(new OkVo(ok));
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询商品")
    public ResultPageVo<Goods> query(@RequestParam(required = false) @ApiParam("商品分类") String catalog,
                                 @RequestParam(required = false) @ApiParam("商品名称") String name,
                                 @RequestParam(required = false) @ApiParam("是否上架") Boolean isOpen,
                                 @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
                                 @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                 @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        final String shopId = currentShopId();
        List<Goods> data = service.query(shopId, catalog, name, isOpen, page * rows, rows);
        return new ResultPageVo.Builder<>(page, rows, data)
                .count(isCount, () -> service.count(shopId, catalog, name, isOpen))
                .build();
    }

    private String currentShopId(){
        return CredentialContextManageUtils.currentShopId();
    }
}
