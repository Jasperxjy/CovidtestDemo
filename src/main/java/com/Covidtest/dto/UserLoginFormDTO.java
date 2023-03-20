package com.Covidtest.dto;

import lombok.Data;

/**
 * @author 熊
 * 作用：存储来自前端传入的json信息，用于保存登录信息
 */
@Data
public class UserLoginFormDTO {

    //存储身份证
    private String id;
    //存储手机号
    private String phone;
    //存储密码
    private String password;
}
