package com.Covidtest.controller;

import com.Covidtest.dto.GetOrderDTO;
import com.Covidtest.dto.Result;
import com.Covidtest.entity.Register;
import com.Covidtest.service.RegisterService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Generated;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 提供有关核酸订单查询相关的方法
 * @author 熊
 *
 */
@RestController
@RequestMapping("/register")
public class RegisterController {

    @Resource
    private RegisterService registerService;

    /**
     *用户获取订单消息的方法
     * @param getOrderDTO 获取订单的请求体，包括时间（暂定）和身份证号
     * @param session 前后端会话
     * @return 返回订单组
     */
    @GetMapping("/getOrder")
    public Result get_14d_Orders(@RequestBody GetOrderDTO getOrderDTO, HttpSession session){
        //TODO 完成查询14天核酸订单的功能
        return registerService.get_14d_Orders(getOrderDTO,session);
    }

    /**
     * 用户刷新缓存中的订单的方法
     * @param getOrderDTO 获取获取订单的请求体，包括时间（暂定）和身份证号
     * @param session 前后端会话
     * @return 返回订单组
     */
    @GetMapping("/refresh")
    public Result refresh(@RequestBody GetOrderDTO getOrderDTO, HttpSession session){
        //TODO 完成刷新查询
        return  registerService.refresh(getOrderDTO,session);
    }

    /**
     * employee登记终端发送的创建订单请求
     * @param register 发送的来自终端的信息，包括完整的订单信息
     * @param session 前后端会话
     * @return 返回订单创建结果
     */
    @PostMapping("/neworder")
    public  Result neworder(@RequestBody Register register,HttpSession session){
        //TODO 完成新订单的创建
        return  registerService.neworder(register,session);
    }
}
