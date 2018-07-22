package com.tuoshecx.server.api.client.marketing;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.api.client.CredentialContextClientUtils;
import com.tuoshecx.server.api.client.marketing.vo.MarketingVo;
import com.tuoshecx.server.api.vo.HasVo;
import com.tuoshecx.server.api.vo.OkVo;
import com.tuoshecx.server.api.vo.ResultPageVo;
import com.tuoshecx.server.api.vo.ResultVo;
import com.tuoshecx.server.marketing.domain.GroupRecord;
import com.tuoshecx.server.marketing.domain.Marketing;
import com.tuoshecx.server.marketing.service.*;
import com.tuoshecx.server.order.domain.Order;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

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

    private final GroupRecordService groupRecordService;
    private final BuyMarketingFactory buyFactory;
    private final GetMarketingFactory getFactory;
    private final QueryMarketing queryMarketing;

    @Autowired
    public MarketingClientController(GroupBuyService groupBuyService, SharePresentService presentService,
                                     SecondKillService secondKillService, OrderMarketingService orderService,
                                     GroupRecordService groupRecordService) {

        this.getFactory = new GetMarketingFactory(groupBuyService, presentService, secondKillService);
        this.queryMarketing = new QueryMarketing(groupBuyService, presentService, secondKillService);
        this.buyFactory = new BuyMarketingFactory(orderService);
        this.groupRecordService = groupRecordService;
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

    @GetMapping(value = "group_buy/{marketingId}/record", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询到还未完成的团购记录")
    public ResultVo<Collection<GroupRecord>> queryGroupRecord(@PathVariable("marketingId")String marketingId,
                                                             @RequestParam(defaultValue = "3") @ApiParam("查询记录数") Integer limit){
        Collection<GroupRecord> records = groupRecordService.
                query(StringUtils.EMPTY, marketingId, GroupRecord.State.WAIT, " create_time ASC ", 0, limit);

        return ResultVo.success(records);
    }

    @GetMapping(value = "share_person/{marketingId}/record", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到用户参加多人行记录")
    public ResultVo<HasVo<GroupRecord>> getUserSharePerson(@PathVariable("marketingId")String marketingId){
        Optional<GroupRecord> optional = groupRecordService.getPresentOneWait(marketingId, currentUserId());
        return optional.map(e -> ResultVo.success(HasVo.has(e))).orElse(ResultVo.success(HasVo.notHas()));
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

    @PutMapping(value = "{id}/share", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("分享团购")
    public ResultVo<OkVo> share(@PathVariable("id") String id){
        groupRecordService.share(id);
        return ResultVo.success(new OkVo(true));
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
