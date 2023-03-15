package com.Covidtest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Covidtest.dao.RegisterDao;
import com.Covidtest.entity.Register;
import com.Covidtest.service.RegisterService;
import org.springframework.stereotype.Service;

/**
 * 核酸登记表(Register)表服务实现类
 *
 * @author makejava
 * @since 2023-03-13 22:09:09
 */
@Service("registerService")
public class RegisterServiceImpl extends ServiceImpl<RegisterDao, Register> implements RegisterService {

}

