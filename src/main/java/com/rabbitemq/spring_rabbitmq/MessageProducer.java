package com.rabbitemq.spring_rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 消息生产者
 * Created by Administrator on 2017/2/22.
 */
@Service
public class MessageProducer {
    @Resource
    private AmqpTemplate amqpTemplate;

    public void sendMessage(Object message) {
        System.out.println("to send message:" + message);
        amqpTemplate.convertAndSend("queueTestKey", message);
    }
}
