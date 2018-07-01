package com.tuoshecx.server.api.manage.ticket;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.api.manage.CredentialContextManageUtils;
import com.tuoshecx.server.api.manage.ticket.form.UseForm;
import com.tuoshecx.server.api.vo.ResultPageVo;
import com.tuoshecx.server.api.vo.ResultVo;
import com.tuoshecx.server.common.utils.SecurityUtils;
import com.tuoshecx.server.ticket.domain.Eticket;
import com.tuoshecx.server.ticket.service.EticketService;
import com.tuoshecx.server.user.domain.Account;
import com.tuoshecx.server.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 电子券管理API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/manage/eticket")
@Api(value = "/manage/eticket", tags = "M-店铺电子券管理")
public class EticketManageController {

    @Autowired
    private EticketService service;

    @Autowired
    private UserService userService;

    @PostMapping(value = "use", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("使用电子券")
    public ResultVo<Eticket> use(@Validated @RequestBody UseForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        Eticket t = service.getByCode(form.getCode());
        String valCode = genValCode(t);
        if(!StringUtils.equals(form.getValCode(), valCode)){
            throw new BaseException("验证码错误");
        }
        return ResultVo.success(service.use(form.getCode(), currentShopId()));
    }

    private String genValCode(Eticket t){
        Account account = userService.getAccount(t.getUserId());
        String c = String.format("%s&%s&%s", t.getCode(), t.getUserId(), account.getPayCode());
        return SecurityUtils.sha1(c);
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "查询电子券")
    public ResultPageVo<Eticket> query(@RequestParam(required = false, defaultValue = "WAIT") @ApiParam("电子券状态") String state,
                                       @RequestParam(required = false) @ApiParam("用户名称") String name,
                                       @RequestParam(required = false) @ApiParam("商品名称") String goodsName,
                                       @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
                                       @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                       @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        String shopId = currentShopId();
        Eticket.State s = StringUtils.isBlank(state)? null : Eticket.State.valueOf(state);
        List<Eticket> data = service.query(StringUtils.EMPTY, shopId, s, name, goodsName, page * rows, rows);
        return new ResultPageVo.Builder<>(page, rows, data)
                .count(isCount, () -> service.count(StringUtils.EMPTY, shopId, s, name, goodsName))
                .build();
    }

    private String currentShopId(){
        return CredentialContextManageUtils.currentShopId();
    }
}
