package com.linku.server.api.client.ticket;

import com.linku.server.BaseException;
import com.linku.server.api.client.CredentialContextClientUtils;
import com.linku.server.api.vo.ResultPageVo;
import com.linku.server.api.vo.ResultVo;
import com.linku.server.ticket.domain.Pticket;
import com.linku.server.ticket.service.PticketService;
import com.linku.server.security.Credential;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 优惠券API接口
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/client/pticket")
public class PticketClientController {

    @Autowired
    private PticketService service;

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("得到优惠券")
    public ResultVo<Pticket> get(@PathVariable("id") String id){

        Pticket t = service.get(id);
        if(!StringUtils.equals(t.getShopId(), currentShopId())){
            throw new BaseException("优惠券不存在");
        }
        return ResultVo.success(t);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultPageVo<Pticket> query(@RequestParam(defaultValue = "WAIT") @ApiParam(value = "优惠券状态") String state,
                                       @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                       @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        List<Pticket> data = service.query(getCredential().getId(), StringUtils.EMPTY, Pticket.State.valueOf(state), page * rows, rows);
        return new ResultPageVo.Builder<>(page, rows, data).build();
    }

    private Credential getCredential(){
        return CredentialContextClientUtils.getCredential();
    }

    private String currentShopId(){
        return CredentialContextClientUtils.currentShopId();
    }
}
