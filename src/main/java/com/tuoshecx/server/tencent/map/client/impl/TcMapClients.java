package com.tuoshecx.server.tencent.map.client.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.common.client.HttpClient;
import com.tuoshecx.server.tencent.map.client.request.DistrictRequest;
import com.tuoshecx.server.tencent.map.client.request.IpRequest;
import com.tuoshecx.server.tencent.map.client.request.SearchRequest;
import com.tuoshecx.server.tencent.map.client.response.DistrictResponse;
import com.tuoshecx.server.tencent.map.client.response.IpResponse;
import com.tuoshecx.server.tencent.map.client.response.SearchResponse;
import org.springframework.web.client.RestTemplate;

public class TcMapClients {
    private final HttpClient<DistrictRequest, DistrictResponse> districtClient;
    private final HttpClient<SearchRequest, SearchResponse> searchClient;
    private final HttpClient<IpRequest, IpResponse> ipClient;

    public TcMapClients(RestTemplate restTemplate){
        ObjectMapper objectMapper = initObjectMapper();
        this.districtClient = new DistrictClient(restTemplate, objectMapper);
        this.searchClient = new SearchClient(restTemplate, objectMapper);
        this.ipClient = new IpClient(restTemplate, objectMapper);
    }

    private ObjectMapper initObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        return mapper;
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
