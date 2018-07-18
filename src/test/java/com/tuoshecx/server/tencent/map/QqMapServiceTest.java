package com.tuoshecx.server.tencent.map;

import com.tuoshecx.server.tencent.configure.properties.TencentProperties;
import com.tuoshecx.server.tencent.map.client.request.DistrictRequest;
import com.tuoshecx.server.tencent.map.client.response.DistrictResponse;
import com.tuoshecx.server.tencent.map.client.response.SearchResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QqMapServiceTest {
    private TencentProperties properties;

    @Before
    public void before(){
        properties = new TencentProperties();
        properties.setMapKey("L2XBZ-L7LWD-GAV46-H23SI-KAGJF-KRFUN");
    }

}
