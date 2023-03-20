package com.Covidtest.utils;

/**
 * @author 熊
 * 作用：定义redis中的一些常量
 */
public class RedisConstants {
    //
    public static final String LOGIN_TOKEN="login:token:";
    //登录token有效期分钟数，含义为操作一次，有效用户信息在redis中存储的时长，即在登录状态下操作一次可以保持多久的登录状态
    public static final long LOGIN_LIMIT_TIME=30L;

}
