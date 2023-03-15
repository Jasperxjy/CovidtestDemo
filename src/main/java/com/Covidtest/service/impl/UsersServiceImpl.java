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

}

