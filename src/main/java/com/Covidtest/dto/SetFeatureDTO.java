package com.Covidtest.dto;

import lombok.Data;

@Data
public class SetFeatureDTO {
    //用户身份证
    private String id;
    //用户拍摄的图片
    private String[] imgs;
}
