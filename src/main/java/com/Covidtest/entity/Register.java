package com.Covidtest.entity;

import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * 核酸登记表(Register)表实体类
 *
 * @author makejava
 * @since 2023-03-13 22:09:09
 */
@SuppressWarnings("serial")
public class Register extends Model<Register> {
    //姓名
    private String name;
    //身份证
    private String id;
    //核酸单号
    private String naid;
    //采样时间
    private Date registime;
    //管号
    private String groupT;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNaid() {
        return naid;
    }

    public void setNaid(String naid) {
        this.naid = naid;
    }

    public Date getRegistime() {
        return registime;
    }

    public void setRegistime(Date registime) {
        this.registime = registime;
    }

    public String getGroupT() {
        return groupT;
    }

    public void setGroupT(String groupT) {
        this.groupT = groupT;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.naid;
    }
    }

