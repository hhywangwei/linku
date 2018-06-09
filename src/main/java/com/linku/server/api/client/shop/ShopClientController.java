package com.linku.server.api.client.shop;

import com.linku.server.api.client.CredentialContextClientUtils;
import com.linku.server.api.vo.ResultVo;
import com.linku.server.shop.domain.Shop;
import com.linku.server.shop.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 店铺API接口
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/client/shop")
@Api(value = "/client/shop", tags = "C-店铺API接口")
public class ShopClientController {

    @Autowired
    private ShopService service;

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "得到店铺信息")
    public ResultVo<Shop> get(){
        return ResultVo.success(service.get(currentShopId()));
    }

    private String currentShopId(){
        return CredentialContextClientUtils.currentShopId();
    }
}
