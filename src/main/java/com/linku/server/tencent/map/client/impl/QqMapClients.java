package com.linku.server.tencent.map.client.impl;

import com.linku.server.common.client.HttpClient;
import com.linku.server.tencent.map.client.request.DistrictRequest;
import com.linku.server.tencent.map.client.request.IpRequest;
import com.linku.server.tencent.map.client.request.SearchRequest;
import com.linku.server.tencent.map.client.response.DistrictResponse;
import com.linku.server.tencent.map.client.response.IpResponse;
import com.linku.server.tencent.map.client.response.SearchResponse;

public class QqMapClients {
    private final HttpClient<DistrictRequest, DistrictResponse> districtClient;
    private final HttpClient<SearchRequest, SearchResponse> searchClient;
    private final HttpClient<IpRequest, IpResponse> ipClient;

    public QqMapClients(){
        this.districtClient = new DistrictClient();
        this.searchClient = new SearchClient();
        this.ipClient = new IpClient();
    }

    public HttpClient<DistrictRequest, DistrictResponse> getDistrictClient() {
        return districtClient;
    }

    public HttpClient<SearchRequest, SearchResponse> getSearchClient() {
        return searchClient;
    }

    public HttpClient<IpRequest, IpResponse> getIpClient() {
        return ipClient;
    }
}
