package com.clj.demo.enumeration;

/**
 * @author lujia chen
 * @version 1.0.version
 * @created 2021/10/22
 * @description pdf枚举类型
 * @date 2021/10/22
 **/
public enum PdfTypeEnum {
    WECHAT_PAY("微信支付"),
    ALIPAY("支付宝"),
    ALI_INTERFACE("阿里巴巴接口", "CN"),
    WECHAT_INTERFACE("财经线-印章组", "天威诚信数字认证中心");

    PdfTypeEnum(String name) {
        this.name = name;

    }

    PdfTypeEnum(String out, String organization) {
        this.out = out;
        this.organization = organization;
    }

    private String name;
    private String out;
    private String organization;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
