package com.tuoshecx.server.wx.pay.client.impl;

import com.tuoshecx.server.common.client.HttpClient;
import com.tuoshecx.server.wx.pay.client.request.RefundQueryRequest;
import com.tuoshecx.server.wx.pay.client.request.RefundRequest;
import com.tuoshecx.server.wx.pay.client.request.TransferRequest;
import com.tuoshecx.server.wx.pay.client.request.UnifiedOrderRequest;
import com.tuoshecx.server.wx.pay.client.response.RefundQueryResponse;
import com.tuoshecx.server.wx.pay.client.response.RefundResponse;
import com.tuoshecx.server.wx.pay.client.response.TransferResponse;
import com.tuoshecx.server.wx.pay.client.response.UnifiedOrderResponse;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.web.client.RestTemplate;

/**
 * 微信支付API访问
 *
 * @author WangWei
 */
public class WxPayClients {
    private final HttpClient<UnifiedOrderRequest, UnifiedOrderResponse> unifiedOrderClient;

    private final HttpClient<TransferRequest, TransferResponse> transferClient;

    private final HttpClient<RefundRequest, RefundResponse> refundClient;

    private final HttpClient<RefundQueryRequest, RefundQueryResponse> refundQueryClient;

    public WxPayClients(RestTemplate restTemplate){
        this.unifiedOrderClient = new UnifiedOrderClient(restTemplate);
        this.transferClient = new TransferClient(restTemplate);
        this.refundClient = new RefundClient(restTemplate);
        this.refundQueryClient = new RefundQueryClient(restTemplate);
    }

    public HttpClient<UnifiedOrderRequest, UnifiedOrderResponse> getUnifiedOrderClient() {
        return unifiedOrderClient;
    }

    public HttpClient<TransferRequest, TransferResponse> getTransferClient() {
        return transferClient;
    }

    public HttpClient<RefundRequest, RefundResponse> getRefundClient() {
        return refundClient;
    }

    public HttpClient<RefundQueryRequest, RefundQueryResponse> getRefundQueryClient() {
        return refundQueryClient;
    }
}
