package com.Covidtest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.Covidtest.dao")
@SpringBootApplication
public class CovidTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(CovidTestApplication.class, args);
    }
}
