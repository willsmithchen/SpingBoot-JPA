package com.clj.demo.common;

/**
 * @author lujia chen
 * @version 1.0.version
 * @created 2021/11/3
 * @description 常量配置
 * @date 2021/11/3
 **/
public final class Constants {

    public static final String VERSION = "v1.3";

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final String ENCRYPT = "EncryptServiceImpl";
    public static final String CALLBACK = "CallbackServiceImpl";

    public static final String PERSON_TYPE="1";
    public static final String COMPANY_TYPE="2";
    public static final String[] DATE_PATTERNS = {
            "yyyy.MM.dd",
            "yyyy/MM/dd",
            "yyyy-MM-dd",
            "yyyy年M",
            "yyyy年MM",
            "yyyy年M月",
            "yyyy年MM月",
            "yyyy.MM",
            "yyyy.MM.dd",
            "yyyy",
            "yyyy.MM.dd HH:mm:ss",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm",
            "yy-MM-dd HH:mm:ss",
            "yyyy/MM/dd HH:mm:ss",
            "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
            "yyyyMMdd",
            "yyyy",
            "yyyy年MM月dd日"
    };


    /**
     * 有数渠道接口返回查询无记录状态码
     */
    public static final String NO_RECORD = "0001";

    public static final String SUCCESS_0000 = "0000";

    private Constants() {
    }
}
