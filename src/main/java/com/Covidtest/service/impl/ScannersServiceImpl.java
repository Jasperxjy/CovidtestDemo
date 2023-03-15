package com.Covidtest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Covidtest.dao.ScannersDao;
import com.Covidtest.entity.Scanners;
import com.Covidtest.service.ScannersService;
import org.springframework.stereotype.Service;

/**
 * (Scanners)表服务实现类
 *
 * @author makejava
 * @since 2023-03-13 22:09:44
 */
@Service("scannersService")
public class ScannersServiceImpl extends ServiceImpl<ScannersDao, Scanners> implements ScannersService {

}

