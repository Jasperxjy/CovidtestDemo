package com.Covidtest.utils;
/**
 * @author 熊
 * 类型：用于判断字符串是否符合标准的全局正则表达式
 * 来源：参考代码复制，其中的密码，验证码，等需要修改
 * 时间：23.3.15
 */
public abstract class RegexPatterns {
    /**
     * 手机号正则
     */
    public static final String PHONE_REGEX = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";
    /**
     * 邮箱正则
     */
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    /**
     * 密码正则。4~32位的字母、数字、下划线
     */
    public static final String PASSWORD_REGEX = "^\\w{4,32}$";
    /**
     * 验证码正则, 6位数字或字母
     */
    public static final String VERIFY_CODE_REGEX = "^[a-zA-Z\\d]{6}$";
    /**
     * 身份证正则
     * 首先以数字 1-9 开头，后面跟随 5 个数字，表示前 6 位地址码，
     * 接着是 4 个数字，表示出生年份，
     * 再接下来是 2 个数字，表示出生月份，
     * 然后是 2 个数字，表示出生日期，
     * 紧接着是 3 个数字，表示顺序码，一般是随机生成的，但不能以 0 开头。
     * 最后一位是校验码，可以是数字 0-9，也可以是字母 X 或 x，用于验证身份证号码的正确性。
     */
    public static final String USER_ID_REGEX = "^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])\\d{3}[0-9Xx]$";
    /**
     * 三个时间戳正则
     * 格式为yyyy_mm_dd hh:mm:ss
     *      yyyy-mm-dd hh:mm:ss
     *      yyyy.mm.dd hh:mm:ss
     */
    public  static final  String TIMESTAMP_REGEX_1 = "\\d{4}_\\d{2}_\\d{2}\\s\\d{2}:\\d{2}:\\d{2}";
    public  static final  String TIMESTAMP_REGEX_2 = "\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}";
    public  static final  String TIMESTAMP_REGEX_3 = "\\d{4}.\\d{2}.\\d{2}\\s\\d{2}:\\d{2}:\\d{2}";

    public static final String GET_ID_SUCCESS_INFO = "^OK:IDis:.*$";
    public static final String SET_FEATURE_INFO = "^OK:Featureis:.*$";
}
