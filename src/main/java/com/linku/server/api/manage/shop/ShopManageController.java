package com.linku.server.api.manage.shop;

import com.linku.server.api.manage.CredentialContextManageUtils;
import com.linku.server.api.vo.ResultVo;
import com.linku.server.api.manage.shop.form.ShopUpdateForm;
import com.linku.server.shop.domain.Shop;
import com.linku.server.shop.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 店铺管理店铺API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/manage/shop")
@Api(value = "/manage/shop", tags = "M-店铺管理API接口")
public class ShopManageController {
    private final ShopService service;

    @Autowired
    public ShopManageController(ShopService service){
        this.service = service;
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "得到店铺信息")
    public ResultVo<Shop> get(){
        return ResultVo.success(service.get(currentShopId()));
    }

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "更新店铺信息")
    public ResultVo<Shop> update(@Valid @RequestBody ShopUpdateForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(service.update(form.toDomain(currentShopId())));
    }

    private String currentShopId(){
        return CredentialContextManageUtils.currentShopId();
    }
}
