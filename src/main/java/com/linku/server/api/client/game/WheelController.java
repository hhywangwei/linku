package com.linku.server.api.client.game;

import com.linku.server.api.client.CredentialContextClientUtils;
import com.linku.server.api.client.game.form.PlayForm;
import com.linku.server.api.vo.ResultVo;
import com.linku.server.game.play.domain.Play;
import com.linku.server.game.wheel.service.BigWheelPrizeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/client/game/wheel")
@Api(value = "/client/game/wheel", tags = "大转盘游戏")
public class WheelController {

    @Autowired
    private BigWheelPrizeService service;

    @PostMapping(value = "play", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "抽奖")
    public ResultVo<BigWheelPrizeService.BigWheelPrize> play(
            @Validated @RequestBody PlayForm form, BindingResult result){

        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        return ResultVo.success(service.prize(form.getGameId(), currentUserId()));
    }

    private String currentUserId(){
        return CredentialContextClientUtils.getCredential().getId();
    }
}
