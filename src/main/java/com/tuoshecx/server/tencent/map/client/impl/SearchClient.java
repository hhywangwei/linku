package com.tuoshecx.server.tencent.map.client.impl;

import com.tuoshecx.server.tencent.map.client.request.SearchRequest;
import com.tuoshecx.server.tencent.map.client.response.SearchResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.time.Duration;
import java.util.Map;

class SearchClient extends BaseMapClient<SearchRequest, SearchResponse> {

    SearchClient() {
        super("Search");
    }

    @Override
    protected byte[] doRequest(WebClient client, SearchRequest request) {
        return client.get().uri(builder -> buildURI(builder, request))
                .retrieve()
                .bodyToMono(byte[].class)
                .block(Duration.ofSeconds(30));
    }

    private URI buildURI(UriBuilder builder, SearchRequest request){
        builder.scheme("http")
                .host("apis.map.qq.com")
                .path("/ws/place/v1/search")
                .queryParam("key", request.getKey())
                .queryParam("output", request.getOutput())
                .queryParam("keyword", encoderURL(request.getKeyword()))
                .queryParam("boundary", String.format("region(%s,0)", encoderURL(request.getDistrict())))
                .queryParam("page_size", request.getPageSize())
                .queryParam("page_index", request.getPageIndex());

        return builder.build();
    }

    private String encoderURL(String v){
        try{
            return URLEncoder.encode(v, "UTF-8");
        }catch (Exception e){
            //none instance
        }
        return "";
    }

    @Override
    protected SearchResponse buildResponse(Map<String, Object> data) {
        return new SearchResponse(data);
    }
}
