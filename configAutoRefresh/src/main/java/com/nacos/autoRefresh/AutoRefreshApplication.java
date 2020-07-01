package com.nacos.autoRefresh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@SpringBootApplication
public class AutoRefreshApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoRefreshApplication.class, args);
    }


}
