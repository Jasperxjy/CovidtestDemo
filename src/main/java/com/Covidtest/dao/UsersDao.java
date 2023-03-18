package com.Covidtest.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.Covidtest.entity.Users;

/**
 * (Users)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-13 22:13:22
 */
public interface UsersDao extends BaseMapper<Users> {
    //此处的功能在参考资料里是使用了redis，功能和原理未知

}

