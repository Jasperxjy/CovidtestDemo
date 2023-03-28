package com.Covidtest.utils;

/**
 * @author 熊
 * 作用：定义redis中的一些常量
 */
public class RedisConstants {
    //
    public static final String LOGIN_TOKEN="login:token:";
    //登录token有效期分钟数，含义为操作一次，有效用户信息在redis中存储的时长，即在登录状态下操作一次可以保持多久的登录状态
    public static final long LOGIN_LIMIT_TIME = 30L;
    //记录表的缓存名
    public static final String CACHE_REGISTER = "cache:register";
    //内存中的核酸记录信息暂定为7天更新
    public static final long CACHE_ORDERS_LIMIT_TIME = 7L;
    //刷新缓存中的记录间隔时间为2min
    public static final long CACHE_REFRESH_LIMIT_TIME = 2L;
    //刷新表时间的缓存名
    public static final String LIMIT_REGISTER_REFRESH = "limit:register_refresh";

    //Employee登录
    public static final String EMPLOYEE_LOGIN_TOKEN="employees：token:";
    //Employee登录时间，永久
    public static final long EMPLOYEE_LOGIN_TIME = -1L;

    //Medical登录
    public static final String MEDICAL_LOGIN_TOKEN="medical:token:";
    //Medical登陆时间,三十分钟
    public static final long MEDICAL_LOGIN_TIME=30L;

    //结果表的缓存名
    public static final String CACHE_RESULTS = "cache:result";
    //结果表的缓存持续时间为七天
    public static final long CACHE_RESULT_LIMIT_TIME = 7L;


}
