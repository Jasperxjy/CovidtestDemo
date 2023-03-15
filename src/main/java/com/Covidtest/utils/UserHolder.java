package com.Covidtest.utils;
import  com.Covidtest.dto.UserDTO;
/**
* @author ：熊
* 类型：线程创建与操作对象
* 作用：创建不同用户的独立线程以对不同用户的操作独立管理，将被使用在所有与user有关用例中
* 创建时间：23.1.15
* */

//可能的用例：用户的创建，登录，修改信息，用户删除，用户添加、更改人脸，查询结果，用户登出

public class UserHolder {
    //创建用户线程
    private static final ThreadLocal<UserDTO> tl = new ThreadLocal<>();

    //在线程中保存用户
    public static void saveUser(UserDTO user){
        tl.set(user);
    }

    //获取线程中的用户对象
    public static UserDTO getUser(){
        return tl.get();
    }

    //移除线程中的用户对象
    public static void removeUser(){
        tl.remove();
    }
}
