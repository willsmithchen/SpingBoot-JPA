package com.clj.demo.service;

import com.clj.demo.common.OutCome;

/**
 * @Author lujia chen
 * @Created 2021/2/24
 * @Description 消息服务类
 * @date 2021/2/24
 * @Version 1.0.version
 **/
public interface MessageService {
    /**
     * 短信发送
     *
     * @param phone -电话号码
     * @param text  -发送内容
     * @param tplId -模板id
     * @return 短信返回是否发送成功。
     */
    OutCome sendMessage(String phone, String text, String tplId);
}
