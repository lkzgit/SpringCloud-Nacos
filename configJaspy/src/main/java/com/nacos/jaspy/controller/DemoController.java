package com.nacos.jaspy.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/demo")
@RestController
@RefreshScope
public class DemoController {

    // @Value("${xxx-password:}")
    @NacosValue(value = "${xxx-password}", autoRefreshed = true)
    private String xxxPassword;

    @GetMapping("/test")
    public String test() {
        System.out.println(xxxPassword);
        return xxxPassword;
    }


}