package com.Covidtest.dto;
import lombok.Data;
/**
* @author ：熊
* 类型：数据层间传输对象
* 作用：在不同层之间传输User中的属性
* 创建时间：23.1.15
**/
@Data
public class UserDTO {
    /**
    * 内部变量从entity复制，需要用到多少以及安全性问题暂时未知
     * 时间3.19 已经经过调整
    */
    private String username;
    //用户身份证
    private String id;
    //用户电话
    private String phone;

}
