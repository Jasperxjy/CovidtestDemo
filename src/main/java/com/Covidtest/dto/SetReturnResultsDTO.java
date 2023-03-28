package com.Covidtest.dto;

import com.Covidtest.entity.Results;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
public class SetReturnResultsDTO {

    //传入的设置结果的信息，返回的查询结果的信息
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    private ArrayList<Results> set_return_results;
    //传入检测机构时间
    private String medical_name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
}
