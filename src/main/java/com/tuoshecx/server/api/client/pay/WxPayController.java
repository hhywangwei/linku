package com.tuoshecx.server.api.client.pay;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.api.client.CredentialContextClientUtils;
import com.tuoshecx.server.api.client.pay.form.PerPayForm;
import com.tuoshecx.server.api.client.pay.vo.PerPayVo;
import com.tuoshecx.server.api.vo.ResultVo;
import com.tuoshecx.server.security.Credential;
import com.tuoshecx.server.wx.configure.properties.WxPayProperties;
import com.tuoshecx.server.wx.pay.client.TradeType;
import com.tuoshecx.server.wx.pay.client.parse.ResponseParseException;
import com.tuoshecx.server.wx.pay.client.parse.ResponseParseFactory;
import com.tuoshecx.server.wx.pay.service.WxPayService;
import com.tuoshecx.server.wx.pay.utils.WxPayUtils;
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

import java.nio.charset.Charset;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
/**
 * 微信支付API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/client/pay/wx")
@Api(value = "/client/pay/wx", tags = "C-微信支付接口")
public class WxPayController {
    private static final Logger logger = LoggerFactory.getLogger(WxPayController.class);

    @Autowired
    private WxPayProperties properties;

    @Autowired
    private WxPayService service;

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("微信小程支付")
    public ResultVo<PerPayVo> small(@Validated @RequestBody PerPayForm form, BindingResult result){
        if(result.hasErrors()){
            return ResultVo.error(result.getAllErrors());
        }

        String perPayId = service.prePay(credential().getId(), form.getOutTradeNo(), TradeType.JSAPI);
        return ResultVo.success(new PerPayVo(properties, perPayId));
    }

    @PostMapping(value = "notify")
    @ApiOperation(value = "接受微信支付通知接口")
    public String notify(@RequestBody(required = false) byte[] bytes){

        String body = bodyStr(bytes);
        logger.debug("WeiXin pay notify body is {}", body);
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
            logger.error("Wx pay notify message format error {}", e.getMessage());
            return failResponse("解析内容错误");
        }catch (BaseException e){
            logger.error("Wx pay notify error is {}", e.getMessage());
            return failResponse("系统错误");
        }
    }

    private String bodyStr(byte[] bytes){
        return  bytes == null || bytes.length == 0 ?
                "" : new String(bytes, Charset.forName("UTF-8"));
    }

    private boolean isWxSign(Map<String, String> params){
        String wxSign = params.remove("sign");
        String sign = WxPayUtils.sign(params, properties.getKey());
        logger.debug("Wx sign is {}, local sign is {}", wxSign, sign);
        return StringUtils.equals(wxSign, sign);
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

    private Credential credential(){
        return CredentialContextClientUtils.getCredential();
    }
}
