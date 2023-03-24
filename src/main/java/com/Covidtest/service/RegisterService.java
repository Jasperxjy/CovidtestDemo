package com.Covidtest.service;

import com.Covidtest.dto.GetOrderDTO;
import com.Covidtest.dto.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import com.Covidtest.entity.Register;

import javax.servlet.http.HttpSession;

/**
 * 核酸登记表(Register)表服务接口
 *
 * @author makejava
 * @since 2023-03-13 22:09:09
 */
public interface RegisterService extends IService<Register> {

    Result get_14d_Orders(GetOrderDTO getOrderDTO, HttpSession session);

    Result refresh(GetOrderDTO getOrderDTO, HttpSession session);

    Result neworder(Register register, HttpSession session);
}

