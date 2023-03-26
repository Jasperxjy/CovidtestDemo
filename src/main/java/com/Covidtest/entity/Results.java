package com.Covidtest.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 检测结果表(Results)表实体类
 *
 * @author makejava
 * @since 2023-03-13 22:09:26
 */
@SuppressWarnings("serial")
public class Results extends Model<Results> {
    //管号
    private String groupT;
    //核酸结果
    private String result;
    //检测时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime detecttime;
    //检测单位
    private String detectunit;


    public String getGroupT() {
        return groupT;
    }

    public void setGroupT(String groupT) {
        this.groupT = groupT;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDateTime getDetecttime() {
        return detecttime;
    }

    public void setDetecttime(LocalDateTime detecttime) {
        this.detecttime = detecttime;
    }

    public String getDetectunit() {
        return detectunit;
    }

    public void setDetectunit(String detectunit) {
        this.detectunit = detectunit;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.groupT;
    }
    }

