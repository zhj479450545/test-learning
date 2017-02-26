package com.rabbitemq.bindings_exchanges;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2017/2/22.
 */
public class BaseConnect {
    protected final static String EXCHANGE_NAME = "ex_log";
    protected Channel channel;

    BaseConnect() {
        try {
//            创建连接和通道
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            // 声明转发器和类型
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
