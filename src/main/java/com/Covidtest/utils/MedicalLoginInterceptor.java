package com.Covidtest.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.Covidtest.entity.Medicalfacility;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.Covidtest.utils.RedisConstants.MEDICAL_LOGIN_TOKEN;

public class MedicalLoginInterceptor implements HandlerInterceptor {
    private StringRedisTemplate stringredistemplate;

    public MedicalLoginInterceptor(StringRedisTemplate redisTemplate){
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
        Map<Object, Object> medicalMap = stringredistemplate.opsForHash().entries(MEDICAL_LOGIN_TOKEN+token);
        //判断用户是否存在
        if(medicalMap.isEmpty()) {
            //用户不存在，拦截 返回401状态码，表明没有授权
            response.setStatus(401);
            return false;
        }
        //将查询到的哈希数据转为medical,不忽略转换中的错误
        Medicalfacility medicalfacility = BeanUtil.fillBeanWithMap(medicalMap,new Medicalfacility(),false);
        //用户存在，保存在ThreadLocal
        MedicalHolder.saveMedical(medicalfacility);
        //刷新token有效期,30分钟
        stringredistemplate.expire(RedisConstants.MEDICAL_LOGIN_TOKEN+token,RedisConstants.MEDICAL_LOGIN_TIME, TimeUnit.MINUTES);
        //放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //移除用户
        MedicalHolder.removeMedical();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
