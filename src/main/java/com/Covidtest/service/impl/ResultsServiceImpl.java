package com.Covidtest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Covidtest.dao.ResultsDao;
import com.Covidtest.entity.Results;
import com.Covidtest.service.ResultsService;
import org.springframework.stereotype.Service;

/**
 * 检测结果表(Results)表服务实现类
 *
 * @author makejava
 * @since 2023-03-13 22:09:26
 */
@Service("resultsService")
public class ResultsServiceImpl extends ServiceImpl<ResultsDao, Results> implements ResultsService {

}

