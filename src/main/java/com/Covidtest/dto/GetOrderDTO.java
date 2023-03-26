package com.Covidtest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;


/**
 * @author 熊
 * 作用：用于在前端向后端传输查询订单请求的信息
 */
@Data
public class GetOrderDTO {
    //身份证号
    private String id;
    //当前时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime current_time;

}
