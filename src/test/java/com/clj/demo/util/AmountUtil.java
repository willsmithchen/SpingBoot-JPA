package com.clj.demo.util;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.regex.Pattern;

public class AmountUtil {
    public static BigDecimal unitWang = BigDecimal.valueOf(10000.00);

    public static BigDecimal unitTransWangByYuan(BigDecimal amount) {
        return amount.divide(unitWang, 1, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 转换为整数
     *
     * @param amount
     * @return
     */
    public static BigDecimal unitTransWangByYuanInteger(BigDecimal amount) {
        return amount.divide(unitWang, 0, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal unitTransWangByYuan(Object object){
        if(Objects.nonNull(object) && StringUtils.isNotBlank(object.toString())){
            String value = Objects.toString(object,"0");
            if (NumberUtils.isNumber(value)) {
                BigDecimal amt= new BigDecimal(value);
                return amt.divide(unitWang,1, RoundingMode.HALF_UP);
            }
        }
        return BigDecimal.ZERO.setScale(1,RoundingMode.HALF_UP);
    }

    public static BigDecimal unitTransWang(Object object){
        if(Objects.nonNull(object) && StringUtils.isNotBlank(object.toString())){
            BigDecimal amt= new BigDecimal(Objects.toString(object,"0"));
            return amt.divide(BigDecimal.ONE,1, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO.setScale(1,RoundingMode.HALF_UP);
    }

    public static BigDecimal unitTransWangInteger(Object object){
        if(Objects.nonNull(object) && StringUtils.isNotBlank(object.toString())){
            BigDecimal amt= new BigDecimal(Objects.toString(object,"0"));
            return amt.divide(BigDecimal.ONE,0, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO.setScale(0,RoundingMode.HALF_UP);
    }

    public static BigDecimal toDecimal(Object object) {
        if (Objects.isNull(object)) {
            return BigDecimal.ZERO;
        }

        return new BigDecimal(object.toString());
    }
    /**
     * 判断是否为科学计数格式
     *
     * @param text -科学计数格式字符串
     * @return 如果是则返回正常的计数格式
     */
    public static String isScientificCount(String text) {
        if (org.springframework.util.StringUtils.isEmpty(text)) {
            return text;
        }
        if (text.contains("[")) {
            text = text.replace("[", "");
        }
        if (text.contains("]")) {
            text = text.replace("]", "");
        }
        String regx = "[+-]*\\d+\\.?\\d*[Ee]*[+-]*\\d+";
        boolean isNumber = Pattern.compile(regx).matcher(text).matches();
        if (isNumber) {
            return new BigDecimal(text).toPlainString();
        }
        return text;
    }
}
