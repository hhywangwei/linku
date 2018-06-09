package com.linku.server.api.common;

import com.linku.server.api.vo.ResultVo;
import com.linku.server.tencent.map.QqMapService;
import com.linku.server.tencent.map.client.response.DistrictResponse;
import com.linku.server.tencent.map.client.response.SearchResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * 行政区划API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/district")
@Api(value = "/district", tags = "P-行政区划API接口")
public class DistrictController {

    @Autowired
    private QqMapService service;

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询行政区划")
    public ResultVo<Collection<DistrictResponse.District>> district(@RequestParam(value = "id", required = false) String id){

        DistrictResponse response = service.district(e -> e.setId(id).build());
        return ResultVo.success(response.getDistricts());
    }

    @GetMapping(value = "address", produces = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation("查询地址")
    public ResultVo<Collection<SearchResponse.Address>> search(@RequestParam("district") String district,
                                                               @RequestParam("keyword") String keyword,
                                                               @RequestParam(value = "page", defaultValue = "1")int page,
                                                               @RequestParam(value = "row", defaultValue = "20")int row){

        SearchResponse response = service.search(e -> e.setDistrict(district)
                .setKeyword(keyword).setPageIndex(page).setPageSize(row).build());
        return ResultVo.success(response.getAddresses());
    }
}
