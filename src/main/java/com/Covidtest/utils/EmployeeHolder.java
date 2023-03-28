package com.Covidtest.utils;

import com.Covidtest.entity.Employees;

public class EmployeeHolder {
    private static final ThreadLocal<Employees> tl = new ThreadLocal<>();

    //在线程中保存
    public static void saveEmployee(Employees employee){
        tl.set(employee);
    }

    //获取线程中的对象
    public static Employees getEmployee(){
        return tl.get();
    }

    //移除线程中的对象
    public static void removeEmployee(){
        tl.remove();
    }
}
