package com.Covidtest.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.Covidtest.entity.Employees;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class EmployeeLoginInterceptor implements HandlerInterceptor{
    private StringRedisTemplate stringredistemplate;

    public EmployeeLoginInterceptor(StringRedisTemplate redisTemplate){
        this.stringredistemplate=redisTemplate;

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取从前端传入的cookie的token
        String token=request.getHeader("authorization");
        if (StrUtil.isBlank(token)) {
            //若为空，用户不存在，拦截 返回401状态码，表明没有授权
            response.setStatus(401);
            return false;
        }
        //基于token获取redis中的用户
        Map<Object, Object> employeerMap = stringredistemplate.opsForHash().entries(RedisConstants.EMPLOYEE_LOGIN_TOKEN+token);
        //判断用户是否存在
        if(employeerMap.isEmpty()) {
            //用户不存在，拦截 返回401状态码，表明没有授权
            response.setStatus(401);
            return false;
        }
        //将查询到的哈希数据转为employees,不忽略转换中的错误
        Employees employee = BeanUtil.fillBeanWithMap(employeerMap,new Employees(),false);
        //用户存在，保存在ThreadLocal
        EmployeeHolder.saveEmployee(employee);
        //刷新token有效期,永久
        stringredistemplate.expire(RedisConstants.EMPLOYEE_LOGIN_TOKEN+token,RedisConstants.EMPLOYEE_LOGIN_TIME, TimeUnit.HOURS);
        //放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //移除用户
        EmployeeHolder.removeEmployee();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
