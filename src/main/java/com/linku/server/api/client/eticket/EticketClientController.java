package com.linku.server.api.client.eticket;

import com.linku.server.BaseException;
import com.linku.server.api.client.CredentialContextClientUtils;
import com.linku.server.api.client.eticket.vo.QrcodeVo;
import com.linku.server.api.vo.ResultPageVo;
import com.linku.server.api.vo.ResultVo;
import com.linku.server.common.utils.SecurityUtils;
import com.linku.server.eticket.domain.Eticket;
import com.linku.server.eticket.service.EticketService;
import com.linku.server.security.Credential;
import com.linku.server.user.domain.Account;
import com.linku.server.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 电子券API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/client/eticket")
@Api(value = "/client/eticket", tags = "C-用户电子券API接口")
public class EticketClientController {

    @Autowired
    private EticketService service;

    @Autowired
    private UserService userService;

    @GetMapping(value = "qrcode", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("电子券二维码")
    public ResultVo<QrcodeVo> qrcode(@RequestParam String id){
        Eticket t = service.get(id);

        if(!StringUtils.equals(t.getUserId(), getCredential().getId())){
            throw new BaseException("电子券不存在");
        }

        String valCode = genValCode(t);

        return ResultVo.success(new QrcodeVo(t, valCode));
    }

    private String genValCode(Eticket t){
        Account account = userService.getAccount(t.getUserId());
        String c = String.format("%s&%s&%s", t.getCode(), t.getUserId(), account.getPayCode());
        return SecurityUtils.sha1(c);
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "查询电子券")
    public ResultPageVo<Eticket> query(@RequestParam(required = false, defaultValue = "WAIT") @ApiParam("电子券状态") String state,
                                       @RequestParam(required = false) @ApiParam("商品名称") String goodsName,
                                       @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                       @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        String userId = getCredential().getId();
        Eticket.State s = StringUtils.isBlank(state) ? null : Eticket.State.valueOf(state);
        List<Eticket> data = service.query(userId, StringUtils.EMPTY, s, StringUtils.EMPTY, goodsName, page * rows, rows);
        return new ResultPageVo.Builder<>(page, rows, data).build();
    }

    private Credential getCredential(){
        return CredentialContextClientUtils.getCredential();
    }
}
