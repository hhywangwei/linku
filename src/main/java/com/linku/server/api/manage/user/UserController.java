package com.linku.server.api.manage.user;

import com.linku.server.api.manage.CredentialContextManageUtils;
import com.linku.server.api.vo.ResultPageVo;
import com.linku.server.user.domain.User;
import com.linku.server.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 店铺会员查询API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/manage/user")
@Api(value = "/manage/user", tags = "M-店铺会员查询API接口")
public class UserController {

    @Autowired
    private UserService service;

    /**
     * 查询会员
     *
     * @param name      用户姓名
     * @param nickname  用户昵称
     * @param phone     联系电话
     * @param isCount   是否查询记录
     * @param page      查询页数
     * @param rows      查询记录数
     * @return
     */
    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询会员")
    public ResultPageVo<User> query(@RequestParam(required = false) @ApiParam("名称") String name,
                                                   @RequestParam(required = false) @ApiParam("用户昵称") String nickname,
                                                   @RequestParam(required = false) @ApiParam("联系电话") String phone,
                                                   @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
                                                   @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                                   @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        String shopId = currentShopId();
        List<User> data = service.query(shopId, name, nickname, phone, page * rows, rows);
        return new ResultPageVo.Builder<>(page, rows, data)
                .count(isCount, () -> service.count(shopId, name, nickname, phone))
                .build();

    }

    private String currentShopId(){
        return CredentialContextManageUtils.currentShopId();
    }
}
