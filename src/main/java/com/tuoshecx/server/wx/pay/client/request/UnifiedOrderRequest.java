package com.tuoshecx.server.wx.pay.client.request;

import com.tuoshecx.server.common.utils.DateUtils;
import com.tuoshecx.server.wx.pay.client.TradeType;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

/**
 * 构建微信统一下单请求
 *
 * @author WangWei
 */
public class UnifiedOrderRequest extends WxPayRequest {
    private static final String DATE_PATTER = "yyyyMMddHHmmss";


    private final String deviceInfo;
    private final String body;
    private final String detail;
    private final String attach;
    private final String outTradeNo;
    private final String feeType;
    private final int totalFee;
    private final String spbillCreateIp;
    private final String timeStart;
    private final String timeExpire;
    private final String goodsTag;
    private final String notifyUrl;
    private final TradeType tradeType;
    private final String productId;
    private final String limitPay;
    private final String openid;

    private UnifiedOrderRequest(String appid, String mchid, String subMchId, String key, String deviceInfo, String body,
                                String detail, String attach, String outTradeNo, String feeType, int totalFee,
                                String spbillCreateIp, String timeStart, String timeExpire, String goodsTag,
                                String notifyUrl, TradeType tradeType, String productId, String limitPay, String openid) {

        super(appid, mchid, key, subMchId);

        this.deviceInfo = deviceInfo;
        this.body = body;
        this.detail = detail;
        this.attach = attach;
        this.outTradeNo = outTradeNo;
        this.feeType = feeType;
        this.totalFee = totalFee;
        this.spbillCreateIp = spbillCreateIp;
        this.timeStart = timeStart;
        this.timeExpire = timeExpire;
        this.goodsTag = goodsTag;
        this.notifyUrl = notifyUrl;
        this.tradeType = tradeType;
        this.productId = productId;
        this.limitPay = limitPay;
        this.openid = openid;
    }

    @Override
    protected void buildParameters(Map<String, String> parameters, String appid, String mchid, boolean sub, String subMchId) {
        putNotBlank(parameters, "appid", appid);
        putNotBlank(parameters, "mch_id", mchid);
        if(sub){
            putNotBlank(parameters,"sub_appid", appid);
            putNotBlank(parameters, "sub_openid", openid);
            putNotBlank(parameters, "sub_mch_id", subMchId);
        }
        putCanBlank(parameters, "device_info", deviceInfo);
        putNotBlank(parameters, "body", body);
        putCanBlank(parameters, "detail", detail);
        putCanBlank(parameters, "attach", attach);
        putNotBlank(parameters, "out_trade_no", outTradeNo);
        putCanBlank(parameters, "fee_type", feeType);
        putNotBlank(parameters, "total_fee", String.valueOf(totalFee));
        putNotBlank(parameters, "spbill_create_ip", spbillCreateIp);
        putCanBlank(parameters, "time_start", timeStart);
        putCanBlank(parameters, "time_expire", timeExpire);
        putCanBlank(parameters, "goods_tag", goodsTag);
        putNotBlank(parameters, "notify_url", notifyUrl);
        putNotBlank(parameters, "trade_type", tradeType.name());
        putCanBlank(parameters, "product_id", productId);
        putCanBlank(parameters, "limit_pay", limitPay);
        putNotBlank(parameters, "openid", openid);
    }

    public static class Builder{
        private String key;
        private String appid;
        private String mchid;
        private String subMchId;
        private String deviceInfo;
        private String body;
        private String detail;
        private String attach;
        private String outTradeNo;
        private String feeType;
        private int totalFee;
        private String spbillCreateIp;
        private String timeStart;
        private String timeExpire;
        private String goodsTag;
        private String notifyUrl;
        private TradeType tradeType;
        private String productId;
        private String limitPay;
        private String openid;

        public Builder(String appid, String mchid, String key, String body, String outTradeNo,
                       int totalFee, String notifyUrl, TradeType tradeType, String openid, String spbillCreateIp){

            this(appid, mchid, "", key, body, outTradeNo, totalFee, notifyUrl, tradeType, openid, spbillCreateIp);
        }

        public Builder(String appid, String mchid, String subMchId, String key, String body, String outTradeNo,
                       int totalFee, String notifyUrl, TradeType tradeType, String openid, String spbillCreateIp){

            this.appid = appid;
            this.mchid = mchid;
            this.subMchId = subMchId;
            this.key = key;
            this.body = body;
            this.outTradeNo = outTradeNo;
            this.feeType="CNY";
            this.totalFee = totalFee;
            this.spbillCreateIp = spbillCreateIp;
            this.timeStart = DateUtils.format(DATE_PATTER, new Date());
            this.timeExpire = DateUtils.format(DATE_PATTER, DateUtils.plusMinutes(new Date(), 30));
            this.notifyUrl = notifyUrl;
            this.tradeType = tradeType;
            this.openid = openid;
            this.limitPay = "no_credit";
        }

        public Builder setDeviceInfo(String deviceInfo){
            this.deviceInfo = deviceInfo;
            return this;
        }

        public Builder setDetail(String detail){
            this.detail = detail;
            return this;
        }

        public Builder setAttach(String attach){
            this.attach = attach;
            return this;
        }

        public Builder setFeeType(String feeType){
            this.feeType = feeType;
            return this;
        }

        public Builder setSpbillCreateIp(String spbillCreateIp){
            Assert.hasText(spbillCreateIp, "spbillCreateIp is blank");

            this.spbillCreateIp = spbillCreateIp;
            return this;
        }

        public Builder setTimeStart(Date timeStart){
            this.timeStart = LocalDateTime
                    .ofInstant(timeStart.toInstant(), ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern(DATE_PATTER));
            return this;
        }

        public Builder setTimeExpire(Date timeExpire){
            this.timeExpire = LocalDateTime
                    .ofInstant(timeExpire.toInstant(), ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern(DATE_PATTER));
            return this;
        }

        public Builder setGoodsTag(String goodsTag){
            this.goodsTag = goodsTag;
            return this;
        }

        public Builder setProductId(String productId){
            this.productId = productId;
            return this;
        }

        public Builder setLimitPay(String limitPay){
            this.limitPay = limitPay;
            return this;
        }

        public UnifiedOrderRequest build(){
            return new UnifiedOrderRequest(appid, mchid, subMchId, key, deviceInfo, body, detail, attach,
                    outTradeNo, feeType, totalFee, spbillCreateIp, timeStart, timeExpire, goodsTag, notifyUrl, tradeType,
                    productId, limitPay, openid);
        }
    }
}
