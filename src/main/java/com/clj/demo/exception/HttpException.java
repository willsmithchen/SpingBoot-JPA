package com.clj.demo.exception;

/**
 * @author lujia chen
 * @version 1.0.version
 * @created 2021/5/14
 * @description 请求异常
 * @date 2021/5/14
 **/
public class HttpException extends RuntimeException {
    public static final String MISS_TYPE="不支持提交类型：";
    public HttpException(final String message) { super(message); }

    public HttpException( final Throwable cause) { super( cause); }

    public HttpException(String reason, final String message) { super(reason + message); }

    public HttpException(final String message, final Throwable cause) { super(message, cause); }
}
