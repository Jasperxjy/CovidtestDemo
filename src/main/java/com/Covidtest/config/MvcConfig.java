package com.Covidtest.config;

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
                        //TODO 没有开发完成，需要指定更多
                        "/users/login",
                        "/users/sign_in"
                );
    }
}
