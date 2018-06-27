package com.linku.server.api.client.order;

import com.linku.server.api.client.order.form.GoodsSaveForm;
import com.linku.server.api.client.CredentialContextClientUtils;
import com.linku.server.api.client.order.vo.OrderVo;
import com.linku.server.api.vo.OkVo;
import com.linku.server.api.vo.ResultPageVo;
import com.linku.server.api.vo.ResultVo;
import com.linku.server.order.domain.Order;
import com.linku.server.order.domain.OrderItem;
import com.linku.server.order.service.OrderService;
import com.linku.server.security.Credential;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 订单处理API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/client/order")
@Api(value = "/client/order", tags = "C-订单处理API接口")
public class OrderClientController {
    private static final Logger logger = LoggerFactory.getLogger(OrderClientController.class);

    @Autowired
    private OrderService service;

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到订单")
    public ResultVo<OrderVo> get(@PathVariable("id")String id){
        Order o = service.get(id);
        List<OrderItem> items = service.getItems(id);
        return ResultVo.success(new OrderVo(o, items));
    }

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("创建商品订单")
    public ResultVo<OrderVo> saveGoods(@Validated @RequestBody GoodsSaveForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        if(form.getGoodsIds().size() != form.getCounts().size()){
            return ResultVo.error(new String[]{"购买商品和购买数量不匹配"});
        }

        final String userId = getCredential().getId();
        Order t = service.save(userId, form.getGoodsIds(), form.getCounts(),
                0, StringUtils.EMPTY, StringUtils.EMPTY);

        return get(t.getId());
    }

    @PutMapping(value = "{id}/pay", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "测试支付订单", hidden = true)
    public ResultVo<OkVo> pay(@PathVariable("id") String id){
        service.pay(id);
        return ResultVo.success(new OkVo(true));
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询用户订单")
    public ResultPageVo<Order> query(@RequestParam(defaultValue = "PAY") @ApiParam(value = "订单状态, PAY:已支付,WAIT:未支付") Order.State state,
                                     @RequestParam(value = "fromTime", required = false) @ApiParam(value = "查询开始时间")Date fromTime,
                                     @RequestParam(value = "toTime", required = false) @ApiParam(value = "查询结束时间")Date toTime,
                                     @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                     @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        String userId = getCredential().getId();
        List<Order> data = service.query(StringUtils.EMPTY, userId, state, fromTime, toTime, page * rows, rows);
        return new ResultPageVo.Builder<>(page, rows, data).build();
    }

    private Credential getCredential(){
        return CredentialContextClientUtils.getCredential();
    }
}
