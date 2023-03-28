package com.Covidtest.service;

import com.Covidtest.dto.GetResultsDTO;
import com.Covidtest.dto.Result;
import com.Covidtest.dto.SetReturnResultsDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.Covidtest.entity.Results;

import javax.servlet.http.HttpSession;

/**
 * 检测结果表(Results)表服务接口
 *
 * @author makejava
 * @since 2023-03-13 22:09:26
 */
public interface ResultsService extends IService<Results> {

    Result get_result(GetResultsDTO getResultsDTO, HttpSession session);

    Result new_result(Results results, HttpSession session);

    Result set_result(SetReturnResultsDTO setResultsDTO, HttpSession session);

}

