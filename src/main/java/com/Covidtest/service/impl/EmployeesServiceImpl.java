package com.Covidtest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Covidtest.dao.EmployeesDao;
import com.Covidtest.entity.Employees;
import com.Covidtest.service.EmployeesService;
import org.springframework.stereotype.Service;

/**
 * (Employees)表服务实现类
 *
 * @author makejava
 * @since 2023-03-13 22:08:31
 */
@Service("employeesService")
public class EmployeesServiceImpl extends ServiceImpl<EmployeesDao, Employees> implements EmployeesService {

}

