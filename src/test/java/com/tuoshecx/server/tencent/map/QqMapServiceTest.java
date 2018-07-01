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

    @Test
    public void testDistrict(){
        QqMapService service = new QqMapService(properties, null);

        DistrictResponse response = service.district(DistrictRequest.Builder::build);
        Assert.assertTrue(response.getStatus() == 0);
        Assert.assertFalse(response.getDistricts().isEmpty());
    }

    @Test
    public void testSearch(){
        QqMapService service = new QqMapService(properties, null);

        SearchResponse response = service.search(e ->
                e.setDistrict("上海").setKeyword("银都").build());
        Assert.assertTrue(response.getStatus() == 0);
        Assert.assertFalse(response.getAddresses().isEmpty());
    }

}
