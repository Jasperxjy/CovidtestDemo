package com.Covidtest.dto;

import lombok.Data;

/**
 * @author 熊
 * 作用：用于在前端向后端传输查询订单请求的信息
 */
@Data
public class GetOrderDTO {
    //身份证号
    private String id;
    //当前时间
    private String current_time;

}
