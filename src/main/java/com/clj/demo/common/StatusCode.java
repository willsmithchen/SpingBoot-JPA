package com.clj.demo.common;

import java.io.Serializable;

/**
 * @Author lujia chen
 * @Created 2021/1/6
 * @Description 状态码接口
 * @date 2021/1/6
 * @Version 1.0.version
 **/
public interface StatusCode extends Serializable {
    long serialVersionUID = 1L;

    /**
     * 消息
     *
     * @return String
     */
    String getMessage();

    /**
     * 状态码
     *
     * @return int
     */
    int getCode();
}
