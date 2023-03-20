package com.Covidtest.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.Covidtest.dto.Result;
import com.Covidtest.dto.UserDTO;
import com.Covidtest.dto.UserLoginFormDTO;
import com.Covidtest.dto.UserSignFormDTO;
import com.Covidtest.utils.RegexUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Covidtest.dao.UsersDao;
import com.Covidtest.entity.Users;
import com.Covidtest.service.UsersService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.Covidtest.utils.RedisConstants.LOGIN_LIMIT_TIME;
import static com.Covidtest.utils.RedisConstants.LOGIN_TOKEN;

/**
 * (Users)表服务实现类
 *
 * @author makejava
 * @since 2023-03-13 22:13:22
 */

@Service("usersService")
public class UsersServiceImpl extends ServiceImpl<UsersDao, Users> implements UsersService {

    //引入redis组件
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result login(UserLoginFormDTO loginForm, HttpSession session) {
        //TODO 实现登录功能
        String id=loginForm.getId();
        String phone=loginForm.getPhone();
        Users user;
        //判断是否有电话
        if(phone==null){
            //校验身份证
            if(RegexUtils.isIDInvalid(id)){
                //不合法，返回错误信息
                return Result.fail("身份证格式错误，请重新输入");
        }
             //合法，根据身份证查找用户
             user = query().eq("id",id).one();
        }else {
            //校验电话
            if(RegexUtils.isPhoneInvalid(phone)){
                //不合法，返回错误信息
                return Result.fail("电话号格式错误，请重新输入");
            }
            //合法，根据电话号查找用户
            user = query().eq("phonenum",phone).one();
        }
        //判断用户是否存在
        if (user==null){
            //不存在，返回信息
            return Result.fail("用户不存在，请重新输入或注册");
        }
        //存在，校验密码
        if(user.getPassword().equals(loginForm.getPassword())) {
            //密码正确，保存用户信息到redis中

            //使用自带的UUID随机生成token，作为登录令牌
            String token= UUID.randomUUID().toString();
            //将user转为hash存储,后面一个数据将users转为userDTO，实际上是将user的人脸、密码等敏感信息隐去，减小线程池中的内存开支，并提高安全性
            UserDTO userDTO = BeanUtil.copyProperties(user,UserDTO.class);
            //将user转成哈希
            Map<String,Object> userMap = BeanUtil.beanToMap(userDTO);
            //存储,注意如果以后userMap中存储了有非string的字段会报错，需要找解决方法
            stringRedisTemplate.opsForHash().putAll(LOGIN_TOKEN + token , userMap);
            //设置有效期
            stringRedisTemplate.expire(LOGIN_TOKEN + token ,LOGIN_LIMIT_TIME, TimeUnit.MINUTES);
            //返回token
            return Result.ok(token);
        }else {
        //密码错误，返回信息
            return Result.fail("密码错误，请重新输入");
        }
    }

    @Override
    public Result sign_In(UserSignFormDTO signForm, HttpSession session) {
        //TODO 实现注册功能
        return null;
    }
}

