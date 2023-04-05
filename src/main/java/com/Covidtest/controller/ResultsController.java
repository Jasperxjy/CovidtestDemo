package com.Covidtest.controller;

import com.Covidtest.dto.*;
import com.Covidtest.entity.Results;
import com.Covidtest.service.ResultsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/results")
public class ResultsController {
    @Resource
    private ResultsService resultsService;

    /**
     * 获得结果表的信息,若缓存没有则从数据库中查找并更新缓存
     * @param getResultsDTO 传入结果表的组号数组
     * @param session 会话
     * @return 结果表数组
     */
    @GetMapping("/get")
    public Result get_results(@RequestBody GetResultsDTO getResultsDTO, HttpSession session){
        return  resultsService.get_result(getResultsDTO,session);
    }

    /**
     * 新建一个结果表，并同时更新缓存和数据库
     * @param results 传入的新建的结果表的信息
     * @param session 会话
     * @return 创建结果
     */
    @PostMapping("/new")
    public Result new_result(@RequestBody Results results, HttpSession session){
        return  resultsService.new_result(results,session);
    }

    /**
     * 设置结果表的结果，包括时间，检测单位，阴/阳性
     * @param setResultsDTO 传入的批量录入结果的信息
     * @param session 会话
     * @return 录入结果
     */
    @PostMapping("/set")
    public Result set_result(@RequestBody SetReturnResultsDTO setResultsDTO, HttpSession session){
        return resultsService.set_result(setResultsDTO,session);
    }
}
