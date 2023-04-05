package com.Covidtest.dto;

import com.Covidtest.entity.Results;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import javafx.util.converter.LocalDateTimeStringConverter;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class SetReturnResultsDTO {

    //传入的设置结果的信息，返回的查询结果的信息
    @JsonView
    private List<Results> set_return_results;
    //传入检测机构时间
    private String medical_name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

    public SetReturnResultsDTO(){
        this.set_return_results = new ArrayList<>();
        this.medical_name= "";
        this.time=null;
    }
}
