package com.clj.demo.web;

import com.alibaba.druid.util.StringUtils;
import com.clj.demo.common.OutCome;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author lujia chen
 * @Created 2021/1/7
 * @Description 生产者发送消息
 * @date 2021/1/7
 * @Version 1.0.version
 **/
@Slf4j
@RestController
@Api(tags = "RocketMQ管理")
@RequestMapping(value = "/mq-producer")
public class MQProducerController {
    @Autowired(required = false)
    DefaultMQProducer defaultMQProducer;

    @SneakyThrows
    @GetMapping(value = "/send-mq")
    @ApiOperation(value = "发送消息")
    public OutCome send(String msg) {
        if (StringUtils.isEmpty(msg)) {
            return OutCome.success();
        }
        log.info("发送MQ消息内容：" + msg);
        Message message = new Message("TestTopic", "TestTag", msg.getBytes());
        //默认3秒超时
        SendResult sendResult = defaultMQProducer.send(message);
        log.info("消息发送响应：" + sendResult.toString());

        return OutCome.success(sendResult);
    }
}
