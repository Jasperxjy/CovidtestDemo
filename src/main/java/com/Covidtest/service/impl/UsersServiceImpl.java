package com.Covidtest.service.impl;

import com.Covidtest.dto.Result;
import com.Covidtest.dto.UserLoginFormDTO;
import com.Covidtest.dto.UserSignFormDTO;
import com.Covidtest.utils.RegexUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Covidtest.dao.UsersDao;
import com.Covidtest.entity.Users;
import com.Covidtest.service.UsersService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * (Users)表服务实现类
 *
 * @author makejava
 * @since 2023-03-13 22:13:22
 */

@Service("usersService")
public class UsersServiceImpl extends ServiceImpl<UsersDao, Users> implements UsersService {

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
        if(user.getPassword()==loginForm.getPassword()) {
            //密码正确，保存用户信息到session中
            session.setAttribute("user",user);
            //返回成功信息
            return Result.ok();
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

