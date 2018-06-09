package com.linku.server.wx.pay.client;

import com.linku.server.wx.pay.client.impl.WxPayClients;
import com.linku.server.wx.pay.client.request.RefundQueryRequest;
import com.linku.server.wx.pay.client.request.RefundRequest;
import com.linku.server.wx.pay.client.request.TransferRequest;
import com.linku.server.wx.pay.client.request.UnifiedOrderRequest;
import com.linku.server.wx.pay.client.response.RefundQueryResponse;
import com.linku.server.wx.pay.client.response.RefundResponse;
import com.linku.server.wx.pay.client.response.TransferResponse;
import com.linku.server.wx.pay.client.response.UnifiedOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.stereotype.Service;

/**
 * 实现微信支付API请求处理
 *
 * @author WangWei
 */
@Service
public class WxPayClientService {

    private final WxPayClients clients;

    @Autowired
    public WxPayClientService(@Qualifier("wxPayConnector") ClientHttpConnector connector){
        this.clients = new WxPayClients(connector);
    }

    public UnifiedOrderResponse unifiedOrder(UnifiedOrderRequest request){
        return clients.getUnifiedOrderClient().request(request);
    }

    public TransferResponse transfer(TransferRequest request){
        return clients.getTransferClient().request(request);
    }

    public RefundResponse refund(RefundRequest request){
        return clients.getRefundClient().request(request);
    }

    public RefundQueryResponse refundQuery(RefundQueryRequest request){
        return clients.getRefundQueryClient().request(request);
    }
}
