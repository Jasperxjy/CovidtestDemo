package com.Covidtest.controller;

import com.Covidtest.dto.EmployeeLoginFormDTO;
import com.Covidtest.dto.EmployeeLogoutDTO;
import com.Covidtest.dto.Result;
import com.Covidtest.service.EmployeesService;
import com.Covidtest.utils.EmployeeHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/employee")
public class EmployeesController {
    @Resource
    private EmployeesService employeesService;
    @PostMapping("/login")
    public Result login(@RequestBody EmployeeLoginFormDTO employeeLoginFormDTO, HttpSession session){
        return employeesService.login(employeeLoginFormDTO, session);

    }

    @PostMapping("/sign_in")
    public Result sign_in(@RequestBody EmployeeLoginFormDTO employeeLoginFormDTO,HttpSession session){
        return employeesService.sign_in(employeeLoginFormDTO,session);
    }
    @GetMapping("/logout")
    public Result logout(@RequestBody EmployeeLogoutDTO employeeLogoutDTO){
        return employeesService.logout(employeeLogoutDTO);
    }

    @GetMapping("/me")
    public Result me(){
        return Result.ok(EmployeeHolder.getEmployee());
    }

}
