package com.tuoshecx.server.api.manage.ticket;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.api.manage.CredentialContextManageUtils;
import com.tuoshecx.server.api.vo.ResultPageVo;
import com.tuoshecx.server.api.vo.ResultVo;
import com.tuoshecx.server.ticket.domain.Pticket;
import com.tuoshecx.server.ticket.service.PticketService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("manage/pticket")
public class PticketManagerController {

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

        List<Pticket> data = service.query(StringUtils.EMPTY, currentShopId(), Pticket.State.valueOf(state), page * rows, rows);
        return new ResultPageVo.Builder<>(page, rows, data).build();
    }

    private String currentShopId(){
        return CredentialContextManageUtils.currentShopId();
    }
}
