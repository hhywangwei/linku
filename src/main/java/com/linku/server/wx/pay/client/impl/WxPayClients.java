package com.linku.server.wx.pay.client.impl;

import com.linku.server.common.client.HttpClient;
import com.linku.server.wx.pay.client.request.RefundQueryRequest;
import com.linku.server.wx.pay.client.request.RefundRequest;
import com.linku.server.wx.pay.client.request.TransferRequest;
import com.linku.server.wx.pay.client.request.UnifiedOrderRequest;
import com.linku.server.wx.pay.client.response.RefundQueryResponse;
import com.linku.server.wx.pay.client.response.RefundResponse;
import com.linku.server.wx.pay.client.response.TransferResponse;
import com.linku.server.wx.pay.client.response.UnifiedOrderResponse;
import org.springframework.http.client.reactive.ClientHttpConnector;

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

    public WxPayClients(ClientHttpConnector connector){
        this.unifiedOrderClient = new UnifiedOrderClient(connector);
        this.transferClient = new TransferClient(connector);
        this.refundClient = new RefundClient(connector);
        this.refundQueryClient = new RefundQueryClient(connector);
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
