package com.clj.demo.enumeration;

import lombok.Getter;

/**
 * @author lujia chen
 * @version 1.0.version
 * @created 2021/11/3
 * @description
 * @date 2021/11/3
 **/
@Getter
public enum CertificationTypeEnum {

    ID_CARD("10", "身份证"),
    RESIDENCE_BOOKLET("11", "户口簿"),
    TEMP_ID_CARD("17", "临时身份证"),
    MILITARY_ID("13", "军官证"),
    SOLDBUCH("14", "士兵证"),
    PASSPORT("12", "护照"),
    HONG_KONG_RESIDENTS("15", "港澳居民来往内地通行证"),
    TAIWAN_RESIDENTS("16", "台湾同胞来往内地通行证"),
    RESIDENCE_PERMIT_FOREIGNERS("18", "外国人居留证"),
    POLICE_ID_CARD("19", "警官证"),
    OTHER("23", "其他证件"),
    HONE_KONG_ID_CARD("24", "香港身份证"),
    AO_MEN_ID_CARD("25", "澳门身份证"),
    TAIWAN_ID_CARD("26", "台湾身份证"),
    MILITARY_ID_CARD("27", "军人身份证件");

    String code;
    String desc;

    CertificationTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
