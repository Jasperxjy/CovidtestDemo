package com.Covidtest.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.Covidtest.dto.UserDTO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class UserLoginInterceptor implements HandlerInterceptor {

    private StringRedisTemplate stringredistemplate;

    /**
     * 由于在MVCconfig中手动new，没有自动装配，所以手动利用构造器注入
     * @param stringRedisTemplate 从Mvcconfig中注入的redis服务对象
     */
    public UserLoginInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringredistemplate = stringRedisTemplate;
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
        Map<Object, Object> userMap = stringredistemplate.opsForHash().entries(RedisConstants.LOGIN_TOKEN+token);
        //判断用户是否存在
        if(userMap.isEmpty()) {
            //用户不存在，拦截 返回401状态码，表明没有授权
            response.setStatus(401);
            return false;
        }
        //将查询到的哈希数据转为userDTO对象,不忽略转换中的错误
        UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap,new UserDTO(),false);
        //用户存在，保存在ThreadLocal
        UserHolder.saveUser(userDTO);
        //刷新token有效期,到30分钟
        stringredistemplate.expire(RedisConstants.LOGIN_TOKEN+token,RedisConstants.LOGIN_LIMIT_TIME, TimeUnit.MINUTES);
        //放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //移除用户
        UserHolder.removeUser();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
