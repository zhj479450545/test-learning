package com.rabbitemq.spring_rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:rabbitmq/spring-rabbit.xml"})
public class TestQueue {
    @Autowired
    MessageProducer messageProducer;
    final String queue_key = "test_queue_key";

    @Test
    public void send() {
        Map<String, Object> map = new HashMap();
        map.put("data", "hello rabbitMq!");
        messageProducer.sendMessage(map);
    }
}
