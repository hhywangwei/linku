package com.linku.server.api.manage.goods;

import com.linku.server.api.manage.CredentialContextManageUtils;
import com.linku.server.api.manage.goods.form.CatalogSaveForm;
import com.linku.server.api.manage.goods.form.CatalogUpdateForm;
import com.linku.server.api.vo.OkVo;
import com.linku.server.api.vo.ResultPageVo;
import com.linku.server.api.vo.ResultVo;
import com.linku.server.goods.domain.Catalog;
import com.linku.server.goods.service.CatalogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 商品分类管理API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/manage/catalog")
@Api(value = "/manage/catalog", tags = "M-商品分类管理API接口")
public class CatalogManageController {

    @Autowired
    private CatalogService service;

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("新增商品分类")
    public ResultVo<Catalog> save(@Valid @RequestBody CatalogSaveForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(service.save(form.toDomain(currentShopId())));
    }

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("修改商品分类")
    public ResultVo<Catalog> update(@Valid @RequestBody CatalogUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }
        return ResultVo.success(service.update(form.toDomain(currentShopId())));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到商品分类")
    public ResultVo<Catalog> get(@PathVariable("id") String id){
        return ResultVo.success(service.get(id));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("删除商品分类")
    public ResultVo<OkVo> delete(@PathVariable("id") String id){
        boolean ok = service.delete(id, currentShopId());
        return ResultVo.success(new OkVo(ok));
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询商品分类")
    public ResultPageVo<Catalog> query(@RequestParam(required = false) @ApiParam("分类名称") String name,
                                       @RequestParam(defaultValue = "false") @ApiParam(value = "是否得到查询记录数") boolean isCount,
                                       @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                       @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        final String shopId = currentShopId();
        return new ResultPageVo.Builder<>(page, rows, service.query(shopId, name, page * rows, rows))
                .count(isCount, () -> service.count(shopId, name))
                .build();
    }

    private String currentShopId(){
        return CredentialContextManageUtils.currentShopId();
    }
}
