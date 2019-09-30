package com.ztk.starter.test.ds.controller;

import com.ztk.starter.ds.model.DSRequest;
import com.ztk.starter.test.ds.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return testService.test(request).getData();
    }
}
