package com.Covidtest.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (Users)表实体类
 *
 * @author makejava
 * @since 2023-03-13 22:13:22
 */
@SuppressWarnings("serial")
public class Users extends Model<Users> {
    //用户名，真实姓名
    private String username;
    //用户身份证
    private String id;
    //用户电话
    private String phonenum;
    //用户口罩人脸特征向量
    private byte[] faceinfoMask;
    //用户人脸特征向量
    private byte[] faceinfoReveal;
    //用户密码
    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public byte[] getFaceinfoMask() {
        return faceinfoMask;
    }

    public void setFaceinfoMask(byte[] faceinfoMask) {
        this.faceinfoMask = faceinfoMask;
    }

    public byte[] getFaceinfoReveal() {
        return faceinfoReveal;
    }

    public void setFaceinfoReveal(byte[] faceinfoReveal) {
        this.faceinfoReveal = faceinfoReveal;
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
        return this.id;
    }

}

