package com.Covidtest.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (Scanners)表实体类
 *
 * @author makejava
 * @since 2023-03-27 21:05:45
 */
@SuppressWarnings("serial")
public class Scanners extends Model<Scanners> {
    //扫管器唯一ID
    private String scannerid;
    //设备分派状态
    private String assignstatus;
    //设备分派对象号码
    private String assignedid;


    public String getScannerid() {
        return scannerid;
    }

    public void setScannerid(String scannerid) {
        this.scannerid = scannerid;
    }

    public String getAssignstatus() {
        return assignstatus;
    }

    public void setAssignstatus(String assignstatus) {
        this.assignstatus = assignstatus;
    }

    public String getAssignedid() {
        return assignedid;
    }

    public void setAssignedid(String assignedid) {
        this.assignedid = assignedid;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.scannerid;
    }
    }

