package com.Covidtest.service;

import com.Covidtest.dto.EmployeeLoginFormDTO;
import com.Covidtest.dto.EmployeeLogoutDTO;
import com.Covidtest.dto.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import com.Covidtest.entity.Employees;

import javax.servlet.http.HttpSession;

/**
 * (Employees)表服务接口
 *
 * @author makejava
 * @since 2023-03-13 22:08:31
 */
public interface EmployeesService extends IService<Employees> {

    Result login(EmployeeLoginFormDTO employeeLoginFormDTO, HttpSession session);

    Result sign_in(EmployeeLoginFormDTO employeeLoginFormDTO , HttpSession session);

    Result logout(EmployeeLogoutDTO employeeLogoutDTO);
}

