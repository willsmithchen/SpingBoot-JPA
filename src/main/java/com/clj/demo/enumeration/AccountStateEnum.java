package com.clj.demo.enumeration;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lujia chen
 * @version 1.0.version
 * @created 2021/11/3
 * @description
 * @date 2021/11/3
 **/
@Getter
@Slf4j
public enum AccountStateEnum {
    /**
     *
     **/
    NORMAL("01", "正常"),
    OVERDUE("02", "逾期"),
    BAD_DEBTS("03", "呆账"),
    SETTLE("04", "结清"),
    FREEZE("05", "冻结"),
    STOP("06", "止付"),
    CLOSEING("07", "销户"),
    NON_ACTIVATE("08", "未激活"),
    BANK_STOP("09", "银行止付"),
    LOW_RECOVERY("10", "私法追偿"),

    OTHER("99", "其他"),
    ;
    String code;
    String desc;

    AccountStateEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


}