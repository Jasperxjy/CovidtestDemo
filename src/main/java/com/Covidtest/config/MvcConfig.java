package com.Covidtest.config;

import com.Covidtest.utils.EmployeeLoginInterceptor;
import com.Covidtest.utils.MedicalLoginInterceptor;
import com.Covidtest.utils.UserLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器
        registry.addInterceptor(new UserLoginInterceptor(stringRedisTemplate))
                .excludePathPatterns(
                        //指定不需要检验登录状态的请求路径,
                        //TODO 没有开发完成，可能会更改
                        "/users/login",
                        "/users/sign_in",
                        "/register/neworder",
                        "/employee/*",
                        "/results/set",
                        "/results/new",
                        "/medical/*"

                );
        //添加employee登录拦截器
        registry.addInterceptor(new EmployeeLoginInterceptor(stringRedisTemplate))
                .excludePathPatterns(
                        "/users/*",
                        "/medical/*",
                        "/results/set",
                        "/register/getorder",
                        "/register/refresh",
                        "/results/get",
                        "/employee/login",
                        "/employee/sign_in"
                );
        //添加medical登录拦截器
        registry.addInterceptor(new MedicalLoginInterceptor(stringRedisTemplate))
                .excludePathPatterns(
                        "/users/*",
                        "/register/getorder",
                        "/register/refresh",
                        "/results/get",
                        "/employee/*",
                        "/results/new",
                        "/register/neworder",
                        "/medical/login",
                        "/medical/sign_in"
                );
    }
}
