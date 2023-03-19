package com.Covidtest.service;

import com.Covidtest.dto.Result;
import com.Covidtest.dto.UserLoginFormDTO;
import com.Covidtest.dto.UserSignFormDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.Covidtest.entity.Users;

import javax.servlet.http.HttpSession;

/**
 * (Users)表服务接口
 *
 * @author makejava
 * @since 2023-03-13 22:13:22
 */
public interface UsersService extends IService<Users> {


    Result login(UserLoginFormDTO loginForm, HttpSession session);

    Result sign_In(UserSignFormDTO signForm, HttpSession session);
}

