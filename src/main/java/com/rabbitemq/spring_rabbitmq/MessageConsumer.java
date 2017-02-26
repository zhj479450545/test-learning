package com.rabbitemq.spring_rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * 消息消费者
 * Created by Administrator on 2017/2/22.
 */
public class MessageConsumer implements MessageListener {
    public void onMessage(Message message) {
        System.out.println("receive message:" + message);
    }
}
