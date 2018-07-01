package com.tuoshecx.server.api.client.goods;

import com.tuoshecx.server.api.client.CredentialContextClientUtils;
import com.tuoshecx.server.api.vo.ResultPageVo;
import com.tuoshecx.server.api.vo.ResultVo;
import com.tuoshecx.server.goods.domain.Goods;
import com.tuoshecx.server.goods.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 用户端商品查询API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/client/goods")
@Api(value = "/client/goods", tags = "C-查询商品API接口")
public class GoodsClientController {

    @Autowired
    private GoodsService service;

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到商品")
    public ResultVo<Goods> get(@PathVariable("id") String id){
        Goods t = service.get(id);
        return ResultVo.success(t);
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询商品")
    public ResultPageVo<Goods> query(@RequestParam(required = false) @ApiParam("商品分类") String catalog,
                                     @RequestParam(required = false) @ApiParam("商品名称") String name,
                                     @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                     @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        final String shopId = currentShopId();
        List<Goods> data = service.query(shopId, catalog, name, true, page * rows, rows);
        return new ResultPageVo.Builder<>(page, rows, data).build();
    }

    private String currentShopId(){
        return CredentialContextClientUtils.currentShopId();
    }
}
