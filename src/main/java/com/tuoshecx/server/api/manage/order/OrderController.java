package com.tuoshecx.server.api.manage.order;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.api.manage.CredentialContextManageUtils;
import com.tuoshecx.server.api.vo.ResultPageVo;
import com.tuoshecx.server.api.vo.ResultVo;
import com.tuoshecx.server.order.domain.Order;
import com.tuoshecx.server.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/manage/order")
@Api(value = "/manage/order", tags = "M-商铺订单管理")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到订单")
    public ResultVo<Order> get(@PathVariable("id") String id){
        Order t = service.get(id);

        if(!StringUtils.equals(t.getShopId(), currentShopId())){
            throw new BaseException("订单不存在");
        }

        return ResultVo.success(t);
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询订单")
    public ResultPageVo<Order> query(@RequestParam(defaultValue = "PAY") @ApiParam(value = "订单状态, PAY:已支付,WAIT:未支付") Order.State state,
                                     @RequestParam(value = "fromTime", required = false) @ApiParam(value = "查询开始时间")Date fromTime,
                                     @RequestParam(value = "toTime", required = false) @ApiParam(value = "查询结束时间")Date toTime,
                                     @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                     @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){


        String shopId = currentShopId();
        List<Order> data = service.query(shopId, StringUtils.EMPTY, state, fromTime, toTime, page * rows, rows);
        return new ResultPageVo.Builder<>(page, rows, data)
                .count(true, () -> service.count(shopId, StringUtils.EMPTY, state, fromTime, toTime))
                .build();
    }

    private String currentShopId(){
        return CredentialContextManageUtils.currentShopId();
    }
}