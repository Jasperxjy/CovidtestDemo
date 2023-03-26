package com.Covidtest.dto;

import lombok.Data;

@Data
public class EmployeeLoginFormDTO {
    //扫脸平台登录账号
    private String employeeid;
    //登录密码
    private String password;
}
