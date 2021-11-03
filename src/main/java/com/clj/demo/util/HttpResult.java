package com.clj.demo.util;

/**
 * @author lujia chen
 * @version 1.0.version
 * @created 2021/5/14
 * @description Http请求结果
 * @date 2021/5/14
 **/
public class HttpResult {
    private int code;
    /**
     * 返回body信息
     */
    private String result;

    public int getCode() {
        return code;
    }

    public String getResult() {
        return result;
    }

    public HttpResult(int code, String result) {
        this.code = code;
        this.result = result;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "code=" + code +
                ", result='" + result + '\'' +
                '}';
    }
}
