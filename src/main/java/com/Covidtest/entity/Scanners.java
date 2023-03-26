package com.Covidtest.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (Scanners)表实体类
 *
 * @author makejava
 * @since 2023-03-13 22:09:44
 */
//TODO 这个数据库可能会由于业务实现逻辑进行大量的调整
@SuppressWarnings("serial")
public class Scanners extends Model<Scanners> {
    //扫管器唯一ID
    private String scannerid;
    //扫管器分配的IPv4地址
    private String ipv4;
    //设备状态链接接或失联
    private String linkstatus;
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

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    public String getLinkstatus() {
        return linkstatus;
    }

    public void setLinkstatus(String linkstatus) {
        this.linkstatus = linkstatus;
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

