package com.Covidtest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Covidtest.dao.UsersDao;
import com.Covidtest.entity.Users;
import com.Covidtest.service.UsersService;
import org.springframework.stereotype.Service;

/**
 * (Users)表服务实现类
 *
 * @author makejava
 * @since 2023-03-13 22:13:22
 */
@Service("usersService")
public class UsersServiceImpl extends ServiceImpl<UsersDao, Users> implements UsersService {


    /**
     * 描述：用于在登陆时没有对应用户而创建新用户的工具私有方法
     * @param id
     * @return user
     */
    private Users createUserWithID(String id){
        //1.创建用户
        Users user =new Users();
        //2.设置用户的身份证号
        user.setId(id);
        //3.保存用户
        save(user);
        return user;
    }
}

