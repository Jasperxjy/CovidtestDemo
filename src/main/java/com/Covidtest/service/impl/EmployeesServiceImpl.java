package com.Covidtest.service.impl;

import com.Covidtest.dto.Result;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Covidtest.dao.EmployeesDao;
import com.Covidtest.entity.Employees;
import com.Covidtest.service.EmployeesService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * (Employees)表服务实现类
 *
 * @author makejava
 * @since 2023-03-13 22:08:31
 */
@Service("employeesService")
public class EmployeesServiceImpl extends ServiceImpl<EmployeesDao, Employees> implements EmployeesService {

    @Override
    public Result login(EmployeesService employeesService, HttpSession session) {
        //TODO 实现登录功能
        return Result.fail("功能未完成");
    }
}

