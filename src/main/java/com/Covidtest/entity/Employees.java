package com.Covidtest.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (Employees)表实体类
 *
 * @author makejava
 * @since 2023-03-26 12:55:35
 */
@SuppressWarnings("serial")
public class Employees extends Model<Employees> {
    //扫脸平台登录账号
    @TableId
    private String employeeid;
    //登录密码
    private String password;
    //匹配机号
    private String assignment;
    //匹配状态
    private String assignsstatus;


    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public String getAssignsstatus() {
        return assignsstatus;
    }

    public void setAssignsstatus(String assignsstatus) {
        this.assignsstatus = assignsstatus;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.employeeid;
    }
    }

