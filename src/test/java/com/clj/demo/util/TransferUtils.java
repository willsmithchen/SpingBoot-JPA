package com.clj.demo.util;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

/**
 * @ClassName TransferUtils
 * @Description
 * @Author liujinwen
 * @Date 2019/8/20 13:39
 * @Version 1.0
 **/
public class TransferUtils {
    private TransferUtils() {}

    private static final String[] PATTERNS = {
        "yyyy-MM-dd HH:mm:ss",
        "yyyy-MM-dd HH:mm",
        "yy-MM-dd HH:mm:ss",
        "yyyy/MM/dd HH:mm:ss",
        "yyyy-MM-dd",
        "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
        "yyyy-MM-dd'T'HH:mm:ss'Z'",
        "yyyyMMdd"
    };

    public static Date transDate(String value) {
        try {
            if (StringUtils.isBlank(value)) {
                return null;
            }
            return DateUtils.parseDate(value, PATTERNS);
        } catch (ParseException e) {
            throw new RuntimeException("时间格式转换错误", e);
        }
    }

    public static Instant transInstant(String value) {
        Date date = transDate(value);
        if (Objects.isNull(date)) {
            return null;
        }
        return date.toInstant();
    }
    public static String transStringFormat(String value, String format) {
        Date date = transDate(value);
        if (Objects.isNull(date)) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }
}
