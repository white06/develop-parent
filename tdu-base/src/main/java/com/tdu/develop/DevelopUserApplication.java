package com.tdu.develop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@MapperScan({"com.tdu.develop.mapper.auto","com.tdu.develop.mapper.custom"})
public class DevelopUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevelopUserApplication.class, args);
        System.out.println("=================================");
        System.out.println("=============开发框架启动成功===============");
        System.out.println("=================================");
    }

   /* protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DevelopUserApplication.class);
    }*/
}
