package com.tuoshecx.server.api.client.pay;

import com.tuoshecx.server.api.client.CredentialContextClientUtils;
import com.tuoshecx.server.api.client.pay.form.PerPayForm;
import com.tuoshecx.server.api.client.pay.vo.PerPayVo;
import com.tuoshecx.server.api.vo.ResultVo;
import com.tuoshecx.server.security.Credential;
import com.tuoshecx.server.shop.domain.ShopWxPay;
import com.tuoshecx.server.shop.service.ShopWxPayService;
import com.tuoshecx.server.wx.configure.properties.WxPayProperties;
import com.tuoshecx.server.wx.pay.client.TradeType;
import com.tuoshecx.server.wx.pay.domain.WxUnifiedOrder;
import com.tuoshecx.server.wx.pay.service.WxPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 微信支付API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/client/pay/wx")
@Api(value = "/client/pay/wx", tags = "C-微信支付接口")
public class WxPayClientController {
    private static final Logger logger = LoggerFactory.getLogger(WxPayClientController.class);

    @Autowired
    private WxPayProperties properties;

    @Autowired
    private WxPayService service;

    @Autowired
    private ShopWxPayService shopWxPayService;

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("微信小程支付")
    public ResultVo<PerPayVo> small(@Validated @RequestBody PerPayForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        WxUnifiedOrder order = service.prePay(credential().getId(), form.getOutTradeNo(), TradeType.JSAPI);
        String key = isShop(properties.getPayType())?
                shopWxPayService.getAppid(order.getAppid()).getKey(): properties.getKey();
        return ResultVo.success(new PerPayVo(order.getAppid(), key, order.getPrepay()));
    }

    private boolean isShop(String payType){
        return StringUtils.equals(payType, "shop");
    }

    private Credential credential(){
        return CredentialContextClientUtils.getCredential();
    }
}
