package com.linku.server.api.client.marketing;

import com.linku.server.BaseException;
import com.linku.server.api.client.CredentialContextClientUtils;
import com.linku.server.api.client.marketing.vo.MarketingVo;
import com.linku.server.api.vo.ResultPageVo;
import com.linku.server.api.vo.ResultVo;
import com.linku.server.marketing.domain.Marketing;
import com.linku.server.marketing.service.*;
import com.linku.server.order.domain.Order;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 营销活动API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">Wangwei</a>
 */
@RestController
@RequestMapping("/client/marketing")
@Api(value = "/client/marketing", tags = "C-营销活动API接口")
public class MarketingClientController {

    private final BuyMarketingFactory buyFactory;
    private final GetMarketingFactory getFactory;
    private final QueryMarketing queryMarketing;

    @Autowired
    public MarketingClientController(GroupBuyService groupBuyService, SharePresentService presentService,
                                     SecondKillService secondKillService, OrderMarketingService orderService) {

        this.getFactory = new GetMarketingFactory(groupBuyService, presentService, secondKillService);
        this.queryMarketing = new QueryMarketing(groupBuyService, presentService, secondKillService);
        this.buyFactory = new BuyMarketingFactory(orderService);
    }

    @PostMapping(value = "{type}/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("购买营销活动")
    public ResultVo<Order> buy(@PathVariable("type") @ApiParam(value = "活动类型", allowableValues = "GROUP_BUY,SECOND_KILL,SHARE_PRESENTER") String type,
                               @PathVariable("id") @ApiParam(value = "编号") String id){

        Order o =  buyFactory.buyHandler(StringUtils.upperCase(type))
                             .orElseThrow(() -> new BaseException("营销活动不存在"))
                             .apply(new BuyMarketingFactory.BuyMarketing(currentUserId(), id));

        return ResultVo.success(o);
    }

    @GetMapping(value = "{type}/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到营销活动")
    public ResultVo<MarketingVo<Marketing>> get(@PathVariable("type") @ApiParam(value = "活动类型", allowableValues = "GROUP_BUY,SECOND_KILL,SHARE_PRESENTER") String type,
                                                @PathVariable("id") @ApiParam(value = "编号") String id){

        Marketing m = getFactory.getHandler(StringUtils.upperCase(type))
                                .orElseThrow(() -> new BaseException("营销活动不存在"))
                                .apply(id);

        return ResultVo.success(new MarketingVo<>(m, type));
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询营销活动")
    public ResultPageVo<MarketingVo<Marketing>> query(@RequestParam(required = false) @ApiParam("营销活动名") String name,
                                                      @RequestParam(required = false) @ApiParam(value = "活动类型", allowableValues = "GROUP_BUY,SECOND_KILL,SHARE_PRESENTER") String type,
                                                      @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                                      @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        return queryMarketing.query(currentShopId(), name, type, page, rows);
    }

    private String currentShopId(){
        return CredentialContextClientUtils.currentShopId();
    }

    private String currentUserId(){
        return CredentialContextClientUtils.getCredential().getId();
    }
}
