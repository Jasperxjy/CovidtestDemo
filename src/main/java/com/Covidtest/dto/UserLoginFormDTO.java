package com.Covidtest.dto;

import lombok.Data;

@Data
public class UserLoginFormDTO {

    //存储身份证
    private String id;
    //存储手机号
    private String phone;
    //存储密码
    private String password;
}
