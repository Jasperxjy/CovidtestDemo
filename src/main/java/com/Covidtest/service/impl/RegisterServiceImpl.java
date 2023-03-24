package com.Covidtest.service.impl;

import com.Covidtest.dto.GetOrderDTO;
import com.Covidtest.dto.Result;
import com.Covidtest.utils.RegexUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Covidtest.dao.RegisterDao;
import com.Covidtest.entity.Register;
import com.Covidtest.service.RegisterService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * 核酸登记表(Register)表服务实现类
 *
 * @author makejava
 * @since 2023-03-13 22:09:09
 */
@Service("registerService")
public class RegisterServiceImpl extends ServiceImpl<RegisterDao, Register> implements RegisterService {

    @Override
    public Result get_14d_Orders(GetOrderDTO getOrderDTO, HttpSession session) {
        //TODO 实现基础查询操作，直接查询缓存
        return Result.fail("功能未完成");

    }

    @Override
    public Result refresh(GetOrderDTO getOrderDTO, HttpSession session) {
        //TODO 从数据库中查询，并添加到缓存中，返回查询结果
        return null;
    }

    @Override
    public Result neworder(Register register, HttpSession session) {//可能会有新的插入策略以应对高峰期的插入请求，具体请参考业务测试结果
        //TODO 完成创建新订单，并同时添加到缓存中，要保证事务的一致性
        return null;
    }
}

