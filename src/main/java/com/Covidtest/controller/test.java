package com.Covidtest.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 这里是建立工程初期测试环境是否搭建完毕的类
 */
@RestController
@RequestMapping("/test")
public class test {
    @GetMapping()
    public void test(){
        System.out.println("hello world\n");
    }
}
