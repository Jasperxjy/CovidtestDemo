package com.Covidtest.utils;
import cn.hutool.core.util.StrUtil;//作用，判断字符串，更改字符串格式
/**
 * @author 熊
 * 类型：前端参数校验工具
 * 作用：校验前端参数是否满足合法格式
 * 创建时间：23.3.15
 */
public class RegexUtils {
    /**
     * 是否是无效手机格式
     * @param phone 要校验的手机号
     * @return true:符合，false：不符合
     */
    public static boolean isPhoneInvalid(String phone){
        return mismatch(phone, RegexPatterns.PHONE_REGEX);
    }

    /**
     * 是否是无效身份证格式
     * @param ID 要校验的身份证号
     * @return true:符合，false：不符合
     */
    public static boolean isIDInvalid(String ID){return  mismatch(ID,RegexPatterns.USER_ID_REGEX);}

    // 校验是否不符合正则格式
    private static boolean mismatch(String str, String regex){
        if (StrUtil.isBlank(str)) {
            return true;
        }
        return !str.matches(regex);
    }
}
