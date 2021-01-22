package com.clj.demo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Optional;

/**
 * @Author lujia chen
 * @Created 2021/1/6
 * @Description 返回结果参数
 * @date 2021/1/6
 * @Version 1.0.version
 **/
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "返回信息")
@SuppressWarnings("unchecked")
public class OutCome<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 状态码: 0:成功，-1：失败，业务异常：非零或非-1
     */
    @ApiModelProperty(value = "状态码", example = "状态码: 0:成功，-1：失败，业务异常：非零或非-1")
    private int code;

    /**
     * 信息
     */
    @ApiModelProperty(value = "信息")
    private String message;

    /**
     * 数据列表
     */
    @ApiModelProperty(value = "数据列表")
    private T data;

    /**
     * 状态码构造器
     *
     * @param statusCode
     */
    private OutCome(StatusCode statusCode) {
        this(statusCode.getCode(), statusCode.getMessage(), null);
    }

    private OutCome(StatusCode statusCode, String message) {
        this(statusCode, message, null);
    }


    private OutCome(StatusCode statusCode, T data) {
        this(statusCode, statusCode.getMessage(), data);
    }

    private OutCome(StatusCode statusCode, String message, T data) {
        this(statusCode.getCode(), message, data);
    }

    private OutCome(int code, String message, T data) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * 返回值
     *
     * @return Optional<T>
     */
    public Optional<T> value() {
        return Optional.ofNullable(this.data);
    }

    /**
     * 成功返回结果
     *
     * @param data
     * @return
     */
    public static <T> OutCome<T> success(T data) {
        return new OutCome(StatusCodeEnum.SUCCESS, data);
    }

    /**
     * 成功返回结果
     *
     * @param code
     * @return
     */
    public static OutCome success(StatusCode code) {
        return new OutCome(code);
    }

    /**
     * 成功返回结果
     *
     * @param message
     * @return
     */
    public static OutCome success(String message) {
        return new OutCome(StatusCodeEnum.SUCCESS, message);
    }

    /**
     * 成功返回结果
     *
     * @param code
     * @param message
     * @return
     */
    public static OutCome success(StatusCode code, String message) {
        return new OutCome(code, message);
    }

    /**
     * 默认成功
     *
     * @return
     */
    public static OutCome success() {
        return new OutCome(StatusCodeEnum.SUCCESS);
    }

    /**
     * 默认失败
     *
     * @return
     */
    public static OutCome failure() {
        return new OutCome(StatusCodeEnum.FAILURE);
    }

    /**
     * 失败返回结果
     *
     * @param data
     * @return
     */
    public static <T> OutCome<T> failure(T data) {
        return new OutCome(StatusCodeEnum.FAILURE, data);
    }

    /**
     * 失败返回结果
     *
     * @param message
     * @return
     */
    public static OutCome failure(String message) {
        return new OutCome(StatusCodeEnum.FAILURE, message);
    }

    /**
     * 失败返回结果
     *
     * @param code
     * @param message
     * @return
     */
    public static OutCome failure(int code, String message) {
        return new OutCome(code, message, null);
    }

    /**
     * 原因失败
     *
     * @param statusCode
     * @return
     */
    public static OutCome failure(StatusCode statusCode) {
        return new OutCome(statusCode);
    }

    /**
     * 原因失败
     *
     * @param statusCode
     * @param data
     * @return
     */
    public static <T> OutCome<T> failure(StatusCode statusCode, T data) {
        return new OutCome(statusCode, data);
    }

    /**
     * 原因失败
     *
     * @param statusCode
     * @return
     */
    public static OutCome failure(StatusCode statusCode, String message) {
        return new OutCome(statusCode, message);
    }

    /**
     * 根据状态判断是否成功
     *
     * @param status > 0 成功否则失败
     * @return
     */
    public static OutCome status(int status) {
        return (status > 0) ? success() : failure();
    }

    /**
     * 根据状态判断是否成功
     *
     * @param status
     * @return
     */
    public static OutCome status(boolean status) {
        return status ? success() : failure();
    }

    @Transient
    public boolean ok() {
        return (0 == code || StatusCodeEnum.SUCCESS.getCode() == code);
    }
}
