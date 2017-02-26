package com.rabbitemq.hello_wold;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2017/2/21.
 * 终端类（生产者与消费者的父类）
 */
public abstract class EndPoint {
    protected Channel channel;
    protected Connection connection;
    protected String endPointName;

    public EndPoint() {
    }

    public EndPoint(String endPointName) throws IOException, TimeoutException {
        this.endPointName = endPointName;
//        创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
//        设置rabbitMq的服务ip地址(本处设置本地ip)
        factory.setHost("127.0.0.1");
//        创建一个连接
        connection = factory.newConnection();
//        创建一个通道
        channel = connection.createChannel();
//        为此通道声明一个消息队列，若消息队列不存在，就会默认在服务端创建一个消息队列
        channel.queueDeclare(endPointName, false, false, false, null);
    }

    /**
     * 关闭channel和connection。并非必须，因为默认是自动关闭的
     *
     */
    public void close() throws IOException, TimeoutException {
        if (null != this.channel) {
            this.channel.close();
        }
        if (null != this.connection) {
            this.connection.close();
        }
    }
}
