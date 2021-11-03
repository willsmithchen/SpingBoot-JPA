package com.clj.demo.exception;

/**
 * @author lujia chen
 * @version 1.0.version
 * @created 2021/5/14
 * @description JSON异常提示
 * @date 2021/5/14
 **/
public final class JSONException extends RuntimeException {
    public static final String GET_VALUE_ERROR = "获取json中key值异常";

    public JSONException(final String message) {
        super(message);
    }

    public JSONException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
