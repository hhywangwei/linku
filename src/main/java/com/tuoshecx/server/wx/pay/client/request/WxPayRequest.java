package com.tuoshecx.server.wx.pay.client.request;

import com.tuoshecx.server.wx.pay.utils.WxPayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 实现请求微信API是body数据构建类
 *
 * @author WangWei
 */
public abstract class WxPayRequest {
    private final static Logger logger = LoggerFactory.getLogger(WxPayRequest.class);

    private final String appid;
    private final String mchid;
    private final boolean sub;
    private final String subMchId;
    private final String subAppid;
    private final String key;
    private final String nonceStr;

    public WxPayRequest(String appid, String mchid, String key, String subMchId){
        this.appid = appid;
        this.mchid = mchid;
        this.key = key;
        this.sub = StringUtils.isNotBlank(subMchId);
        this.subMchId = subMchId;
        this.subAppid = sub ? appid : "";
        this.nonceStr = WxPayUtils.nonceStr(6);
    }

    /**
     * 构建请求Body
     *
     * @return 请求API body数据
     */
    public String body(){
        Map<String, String> parameters = new LinkedHashMap<>(10,1);

        buildParameters(parameters, appid, mchid, sub, subMchId);
        putNotBlank(parameters, "nonce_str", nonceStr);

        String sign = sign(parameters, key);
        parameters.put("sign", sign);

        return buildXmlBody(parameters);
    }

    /**
     * 构建访问参数
     *
     * parameter.key 参数名(微信给的参数字段), parameter.value 参数值
     *
     * @param parameters 参数容器
     * @param appid appid
     * @param mchid mchid
     * @param sub 是否第三方支付
     * @param subMchId 第三方mchid
     */
    protected abstract void buildParameters(Map<String, String> parameters, String appid, String mchid, boolean sub, String subMchId);

    /**
     * 添加可以为空参数到参数集合中
     *
     * @param parameters 参数集合
     * @param name       添加参数名
     * @param value      添加参数值
     */
    protected void putCanBlank(Map<String, String> parameters, String name, String value){
        if(StringUtils.isNotBlank(value)){
            parameters.put(name, value);
        }
    }

    /**
     * 添加参数不能为空
     *
     * @param parameters 参数集合
     * @param name       参数名
     * @param value      参数值
     */
    protected void putNotBlank(Map<String, String> parameters, String name, String value){
        if(StringUtils.isBlank(value)){
            logger.error("Parameter \"{}\" not blank", name);
            throw new IllegalArgumentException("Parameter \"" + name + "\" not blank");
        }
        parameters.put(name, value);
    }

    /**
     * 签名传输数据
     *
     * @param parameters  参数
     * @return 微信签名
     */
    private String sign(Map<String, String> parameters, String key){
        return WxPayUtils.sign(parameters, key);
    }

    /**
     * 构建XML格式Body
     *
     * @param parameters 参数
     * @return body
     */
    private String buildXmlBody(Map<String, String> parameters){
        StringBuilder builder = new StringBuilder(200);
        builder.append("<xml>\n");
        parameters.forEach((k,v) ->
                builder.append("<").append(k).append(">").append(v).append("</").append(k).append(">\n"));
        builder.append("</xml>\n");
        String c = builder.toString();

        logger.debug("Wx request body is {}", c);

        return c;
    }
}
