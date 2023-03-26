package com.Covidtest.controller;

import com.Covidtest.dto.EmployeeLoginFormDTO;
import com.Covidtest.dto.Result;
import com.Covidtest.service.EmployeesService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/employee")
public class EmployeesController {
    @Resource
    private EmployeesService employeesService;
    public Result login(@RequestBody EmployeeLoginFormDTO employeeLoginFormDTO, HttpSession session){
        return employeesService.login(employeesService, session);

    }
}
