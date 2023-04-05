package com.Covidtest.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (Medicalfacility)表实体类
 *
 * @author makejava
 * @since 2023-03-13 22:08:49
 */
@SuppressWarnings("serial")
public class Medicalfacility extends Model<Medicalfacility> {
    //医疗机构ID
    @TableId
    private String medicalfacilityid;
    //医疗机构名称
    private String name;
    //医疗机构密码
    private String password;


    public String getMedicalfacilityid() {
        return medicalfacilityid;
    }

    public void setMedicalfacilityid(String medicalfacilityid) {
        this.medicalfacilityid = medicalfacilityid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.medicalfacilityid;
    }
    }

