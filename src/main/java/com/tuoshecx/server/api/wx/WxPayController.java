package com.tuoshecx.server.api.wx;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.shop.domain.ShopWxPay;
import com.tuoshecx.server.shop.service.ShopWxPayService;
import com.tuoshecx.server.wx.configure.properties.WxPayProperties;
import com.tuoshecx.server.wx.pay.client.parse.ResponseParseException;
import com.tuoshecx.server.wx.pay.client.parse.ResponseParseFactory;
import com.tuoshecx.server.wx.pay.service.WxPayService;
import com.tuoshecx.server.wx.pay.utils.WxPayUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * 微信支付通知处理
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/wx/pay")
public class WxPayController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WxPayController.class);

    @Autowired
    private WxPayProperties properties;

    @Autowired
    private WxPayService service;

    @Autowired
    private ShopWxPayService shopWxPayService;

    @PostMapping(value = "notify")
    @ApiOperation(value = "接受微信支付通知接口")
    public String notify(@RequestBody(required = false) byte[] bytes){

        String body = bodyStr(bytes);
        LOGGER.debug("WeiXin pay notify body is {}", body);
        if(StringUtils.isBlank(body)){
            return successResponse();
        }

        try{
            Map<String, String> data = ResponseParseFactory.xmlParse().parse(body, e -> e);

            if(!isWxSign(data)){
                return failResponse("微信签名错误");
            }

            if(!isWxSuccess(data)){
                return successResponse();
            }

            boolean ok = service.payNotify(data);
            return ok ? successResponse(): failResponse("系统错误");
        }catch (ResponseParseException e){
            LOGGER.error("Wx pay notify message format error {}", e.getMessage());
            return failResponse("解析内容错误");
        }catch (BaseException e){
            LOGGER.error("Wx pay notify error is {}", e.getMessage());
            return failResponse("系统错误");
        }
    }

    private String bodyStr(byte[] bytes){
        return  bytes == null || bytes.length == 0 ?
                "" : new String(bytes, Charset.forName("UTF-8"));
    }

    private boolean isWxSign(Map<String, String> params){
        final String key ;
        if(isShop(properties.getPayType())){
            String appid = params.get("appid");
            ShopWxPay shopWxPay = shopWxPayService.getAppid(appid);
            key = shopWxPay.getKey();
        }else{
            key = properties.getKey();
        }
        String wxSign = params.remove("sign");
        String sign = WxPayUtils.sign(params, key);
        LOGGER.debug("Wx sign is {}, local sign is {}", wxSign, sign);
        return StringUtils.equals(wxSign, sign);
    }

    private boolean isShop(String payType){
        return StringUtils.equals(payType, "shop");
    }

    private boolean isWxSuccess(Map<String, String> data){
        String code = data.getOrDefault("return_code", "FAIL");
        String resultCode = data.getOrDefault("result_code", "FAIL");
        return StringUtils.equals(code, "SUCCESS") && StringUtils.equals(resultCode, "SUCCESS");
    }

    private  String successResponse(){
        return "<xml>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
    }

    private String failResponse(String msg){
        return "<xml>\n" +
                "  <return_code><![CDATA[FAIL]]></return_code>\n" +
                "  <return_msg><![CDATA["+ msg +"]]></return_msg>\n" +
                "</xml>";
    }
}
