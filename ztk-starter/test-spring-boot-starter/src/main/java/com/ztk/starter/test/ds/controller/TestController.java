package com.ztk.starter.test.ds.controller;

import com.alibaba.fastjson.JSONObject;
import com.ztk.starter.ds.http.HttpClientUtil;
import com.ztk.starter.ds.model.DSRequest;
import com.ztk.starter.test.ds.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 类的描述
 *
 * @author sunyue
 * @date 2019/9/30 下午1:13
 */
@RestController
@RequestMapping("/ds/test")
@AllArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/test1")
    public Object test1() {
        DSRequest request = new DSRequest();
        request.setUrl("https://www.6tiantian.com");
        request.setPath("/api/teacher/book/list");
        Map<String, Object> params = new HashMap<>();
        params.put("customConfigId", "custom_config_id_2");
        params.put("token", "002684ba533f61234d527871f0c8db51af7376fe");
        params.put("type", "3");
        request.setParam(JSONObject.toJSONString(params));
        return testService.test(request).getData();
    }

    @GetMapping("/test2")
    public Object test2(String name) {
        System.out.println(name);
        return "this is test method test2";
    }
}
