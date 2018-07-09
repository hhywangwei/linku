package com.tuoshecx.server.api.manage.wx;

import com.tuoshecx.server.api.manage.CredentialContextManageUtils;
import com.tuoshecx.server.api.manage.wx.vo.ShopWxInfoVo;
import com.tuoshecx.server.api.vo.ResultPageVo;
import com.tuoshecx.server.api.vo.ResultVo;
import com.tuoshecx.server.shop.domain.ShopWxAuthorized;
import com.tuoshecx.server.shop.service.ShopWxService;
import com.tuoshecx.server.wx.small.message.domain.SmallTemplate;
import com.tuoshecx.server.wx.small.message.domain.SendMessage;
import com.tuoshecx.server.wx.small.message.service.SendMessageService;
import com.tuoshecx.server.wx.small.message.service.WxSmallTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 店铺托管微信小程序信息管理
 *
 * @author <a href="mailto:hhywangwei@gmmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/manage/wx")
@Api(value = "/manage/wx", tags = "M-店铺托管微信小程序信息")
public class ManageWxController {

    @Autowired
    private ShopWxService service;

    @Autowired
    private WxSmallTemplateService smallTemplateService;

    @Autowired
    private SendMessageService sendMessageService;

    @GetMapping(value = "info", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到店铺托管微信小程序公众号信息")
    public ResultVo<ShopWxInfoVo> getInfo(){
        List<ShopWxAuthorized> list = service.queryAuthorized(currentShopId());
        return ResultVo.success(list.isEmpty()? ShopWxInfoVo.noHas(): ShopWxInfoVo.has(list.get(0)));
    }

    @GetMapping(value = "messageTemplate", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询微信消息模板信息")
    public ResultPageVo<SmallTemplate> queryMessageTemplate(@RequestParam(required = false) @ApiParam(value = "调用") String callKey,
                                                            @RequestParam(required = false) @ApiParam(value = "标题") String title,
                                                            @RequestParam(required = false) @ApiParam(value = "备注") String remark,
                                                            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                                            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        List<ShopWxAuthorized> authorizeds = service.queryAuthorized(currentShopId());
        List<SmallTemplate> data = authorizeds.isEmpty()?
                Collections.emptyList() : smallTemplateService.query(authorizeds.get(0).getAppid(), callKey, title, remark, page *rows, rows);

        return new ResultPageVo.Builder<>(page, rows, data)
                .count(true, () -> authorizeds.isEmpty()? 0L: smallTemplateService.count(authorizeds.get(0).getAppid(), callKey, title, remark))
                .build();
    }

    @GetMapping(value = "sendMessage", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询发送消息日志")
    public ResultPageVo<SendMessage> querySendMessage(@RequestParam(required = false) @ApiParam("消息标题") String title,
                                                      @RequestParam(required = false) @ApiParam("调用关键字") String callKey,
                                                      @RequestParam(required = false) @ApiParam("接收用户openid") String openid,
                                                      @RequestParam(required = false) @ApiParam("消息状态") String status,
                                                      @RequestParam(required = false) @ApiParam("查询数据开始日期") Date fromTime,
                                                      @RequestParam(required = false) @ApiParam("查询数据结束日期") Date toTime,
                                                      @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                                      @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        List<ShopWxAuthorized> authorizeds = service.queryAuthorized(currentShopId());
        List<SendMessage> data = authorizeds.isEmpty()?
                Collections.emptyList() :
                sendMessageService.query(authorizeds.get(0).getAppid(), title, callKey, openid, status, fromTime, toTime, page * rows, rows);

        return new ResultPageVo.Builder<>(page, rows, data)
                .count(true, () -> authorizeds.isEmpty() ? 0L : sendMessageService.count(authorizeds.get(0).getAppid(), title, callKey, openid, status, fromTime, toTime))
                .build();
    }

    private String currentShopId(){
        return CredentialContextManageUtils.currentShopId();
    }
}
