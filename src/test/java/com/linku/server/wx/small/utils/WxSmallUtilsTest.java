package com.linku.server.wx.small.utils;

import org.junit.Test;

import java.util.Optional;

public class WxSmallUtilsTest {

    @Test
    public void testDecrypt(){
        String sessionKey = "PCmOImkBKRKnBZnUDJt0cw==";
        String vi = "6YB3KapEyxkgMbpOa3B7Rw==";
        String data = "XZHtFej3QNxh/Jdz5S8C5zFe38v/NpIYLPhVMZSGdR0uWV7Rqj64AVA2vzeujHnpunmXQzaQfjcdvcFN4OaEVGB7bayrcU8AVBMJEFNJNx8oA1i2uevgJIc2GKBwg889aXA8hDV/FyCyj0W+8q2bX2e8NxkNE0fhl6PcMOJxIvD9vrVCIvw+lEVF6m6qfLarG2dDVey0xF+fHoogJKLvg3I0MXt+39B52W1v/xTyoCVbBR54a5phndT/bqFjHrTjpcANWArZt8yUeS9HSwmMRbhqSLN0HXfVTmsFDmnc2AjUNlVQGYTlJ4nY7yfvmrfsnCFetXeOQMx1WTlnrvabvhgK58JwRr3/Ul4xflrO3JZL6IVVSQ0MiV6Q4mszU6lsAD775G5HR+Oa4H8MwMsdWS418CydEZq1tFh2A1xV6T3MhIkbitP+Nn2Up8gWRq4o+KAxP7YjDsyWr2GU9wWOXCXjTJ6824LVXmh1QA7+5hM=";
        Optional<String> optional = WxSmallUtils.decrypt(data, sessionKey, vi);
    }
}
