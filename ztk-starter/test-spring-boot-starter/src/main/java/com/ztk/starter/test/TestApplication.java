package com.ztk.starter.test;

import com.ztk.starter.ds.annotation.EnableDS;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 类的描述
 *
 * @author sunyue
 * @date 2019/9/30 上午10:30
 */
@SpringBootApplication
@EnableDS(packages = {"com.ztk.starter.test.ds.service"})
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
