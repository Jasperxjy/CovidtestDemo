package com.Covidtest.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.Covidtest.entity.Users;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (Users)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-13 22:13:22
 */

@Repository
public interface UsersDao extends BaseMapper<Users> {

    @Select("select id from users")
    List<String> selectAllid();
}

