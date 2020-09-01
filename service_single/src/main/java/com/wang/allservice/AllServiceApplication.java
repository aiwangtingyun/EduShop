package com.wang.allservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.wang")
@MapperScan(basePackages = {"com.wang.allservice.mapper"})
public class AllServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AllServiceApplication.class, args);
    }

}
