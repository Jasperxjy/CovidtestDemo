package com.Covidtest.utils;

import com.Covidtest.dto.UserDTO;
import com.Covidtest.entity.Medicalfacility;

public class MedicalHolder {
    //创建用户线程
    private static final ThreadLocal<Medicalfacility> tl = new ThreadLocal<>();

    //在线程中保存用户
    public static void saveMedical(Medicalfacility medicalfacility){
        tl.set(medicalfacility);
    }

    //获取线程中的用户对象
    public static Medicalfacility getMedical(){
        return tl.get();
    }

    //移除线程中的用户对象
    public static void removeMedical(){
        tl.remove();
    }
}
