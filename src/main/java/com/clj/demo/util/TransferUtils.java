package com.clj.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.StringUtil;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.clj.demo.common.Constants.DATE_PATTERNS;

/**
 * @author lujia chen
 * @version 1.0.version
 * @created 2021/11/3
 * @description 转换类
 * @date 2021/11/3
 **/
@Slf4j
public class TransferUtils {
    /**
     * @return java.math.BigDecimal
     * @Author liujinhao
     * @Description obj to BigDecimal 保留两位小数
     * @Date 11:26 2019/6/13
     * @Param [value]
     **/
    public static BigDecimal transBigDecimal(String method, Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Integer) {
            Integer money = (Integer) obj;
            if (StringUtil.isNotBlank(method) && "multiply".equals(method)) {
                return new BigDecimal(money).multiply(new BigDecimal("10000")).setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            if (StringUtil.isNotBlank(method) && "divide".equals(method)) {
                return new BigDecimal(money).divide(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            return new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (obj instanceof String) {
            String money = (String) obj;
            //判断金额中是否有中文字样
            Boolean flag = false;
            if (checkIsContainChines(money)) {
                flag = checkQuota(money);
                money = getNumberFromString(money);
            }
            if (StringUtil.isNotBlank(money)) {
                if (flag || (StringUtil.isNotBlank(method) && "multiply".equals(method))) {
                    return new BigDecimal(money).multiply(new BigDecimal("10000")).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                return new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_UP);
            }
        }
        return null;
    }

    public static BigDecimal transBigDecimal(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Integer) {
            return new BigDecimal((Integer) obj);
        } else if (obj instanceof String && StringUtil.isNotBlank(String.valueOf(obj))) {
            return new BigDecimal((String) obj);
        }
        return null;
    }


    private static final Pattern p = Pattern.compile("[\u4e00-\u9fa5]");

    private static final Pattern p1 = Pattern.compile("万|万元");

    private static final Pattern p2 = Pattern.compile("([\\d]+[\\.][\\d]*)|([\\d]+)");

    /**
     * @return java.lang.Boolean
     * @Author liujinhao
     * @Description 判断金额中是否有中文
     * @Date 21:01 2019/6/14
     * @Param [money]
     **/
    private static Boolean checkIsContainChines(String money) {
        boolean flag = false;
        Matcher m = p.matcher(money);
        if (m.find()) {
            flag = true;
        }
        return flag;
    }

    private static Boolean checkQuota(String money) {
        boolean flag = false;
        Matcher m1 = p1.matcher(money);
        if (m1.find()) {
            flag = true;
        }
        return flag;
    }

    /**
     * @return java.lang.Integer
     * @Author liujinhao
     * @Description obj to Integer
     * @Date 17:47 2019/6/13
     * @Param [obj]
     **/
    public static Integer transInteger(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Integer) {
            return (Integer) obj;
        } else if (obj instanceof String) {
            if (StringUtil.isNotBlank((String) obj)) {
                return Integer.valueOf((String) obj);
            }
        }
        return null;
    }

    /**
     * 将英文括号替换成中文括号
     *
     * @param str
     * @return
     */
    public static String replaceBrackets(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        return str.replace("(", "（").replace(")", "）");
    }

    private static String getNumberFromString(String money) {
        if (money.contains("已履行")) {
            money = money.split("已履行")[1];
        }
        Matcher m = p2.matcher(money);
        if (m.matches()) {
            return m.group(0);
        }
        return null;
    }

    public static Instant trans(String value) {
        try {
            if (StringUtils.isBlank(value)) {
                return null;
            }
            return DateUtils.parseDate(value, DATE_PATTERNS).toInstant();
        } catch (ParseException e) {
            log.error("时间格式转换错误:" + value, e);
            return null;
        }
    }

    public static Date transToDate(String value) {
        try {
            if (StringUtils.isBlank(value)) {
                return null;
            }
            return DateUtils.parseDate(value, DATE_PATTERNS);
        } catch (ParseException e) {
            log.error("时间格式转换错误:" + value, e);
            return null;
        }
    }

    public static Instant transStringToDate(String value) {
        try {
            if (StringUtils.isBlank(value)) {
                return null;
            }
            if (checkIsContainChines(value)) {
                //日期中含中文如(长期)统一格式化为
                value = "2099-01-01";
            }
            return DateUtils.parseDate(value, DATE_PATTERNS).toInstant();
        } catch (ParseException e) {
            log.error("时间格式转换错误:" + value, e);
            return null;
        }
    }


    /**
     * @return java.math.BigDecimal
     * @Author liujinhao
     * @Description Two string to BigDecimal,divde,keep 4-digit decimal
     * @Date 11:35 2019/6/13
     * @Param [divisor, dividend]
     **/
    public static BigDecimal divdeMethod(String divisor, String dividend) {
        if (StringUtils.isNotBlank(divisor) && StringUtils.isNotBlank(dividend)) {
            if (divisor.contains("%")) {
                divisor = divisor.replaceAll("%", "");
            }
            return new BigDecimal(divisor).divide(new BigDecimal(dividend), 4, RoundingMode.HALF_UP);
        }
        return null;
    }

    public static List<Map<String, String>> transListStringMap(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof List) {
            return (List<Map<String, String>>) obj;
        }
        throw new RuntimeException("未定义转换类型");
    }

    public static Boolean transBoolean(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Boolean) {
            return (Boolean) obj;
        } else if (obj instanceof String) {
            String result = (String) obj;
            if ("true".equals(result) || "True".equals(result)) {
                return Boolean.TRUE;
            } else if ("false".equals(result) || "False".equals(result)) {
                return Boolean.FALSE;
            }
        }
        return null;
    }

    public static File transferToFile(InputStream resource, String originalFileName) {
        File file = null;
        try {

            file = new File(originalFileName);
            OutputStream outputStream = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = resource.read(buffer, 0, 8192)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            resource.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
