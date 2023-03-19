package com.Covidtest.dto;

import lombok.Data;

@Data
public class UserSignFormDTO {
    //存储姓名
    private String username;
    //存储手机号
    private String phone;
    //存储身份证
    private String id;
    //存储密码
    private String password_1;
    //存储密码二次确认
    private String password_2;

}
