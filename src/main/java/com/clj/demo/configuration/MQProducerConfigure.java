package com.clj.demo.configuration;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author lujia chen
 * @Created 2021/1/6
 * @Description MQ生产者配置
 * @date 2021/1/6
 * @Version 1.0.version
 **/
@Data
@Slf4j
@ToString
@Configuration
@ConfigurationProperties(prefix = "rocketmq.producer")
public class MQProducerConfigure {
    /**
     * 是否开启自动配置
     */
    /**
     * 生产组名称
     */
    private String groupName;
    /**
     * 注册地址
     */
    private String nameSrvAddr;
    /**
     * 消息最大值
     */
    private Integer maxMessageSize;
    /**
     * 消息发送超时时间
     */
    private Integer sendMsgTimeout;
    /**
     * 失败重试次数
     */
    private Integer retryTimeWhenSendFailed;

    /**
     * 生成者配置
     *
     * @return
     */
    @SneakyThrows
    @Bean
    @ConditionalOnProperty(prefix = "rocketmq.producer", value = "isOnOff", havingValue = "on")
    public DefaultMQProducer defaultMQProducer() {
        log.info("defaultProducer--正在创建------------");
        DefaultMQProducer producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(nameSrvAddr);
        producer.setVipChannelEnabled(false);
        producer.setMaxMessageSize(maxMessageSize);
        producer.setSendMsgTimeout(sendMsgTimeout);
        producer.setRetryTimesWhenSendFailed(retryTimeWhenSendFailed);
        producer.start();
        log.info("rocketmq producer server 开启成功----------");
        return producer;
    }

}
