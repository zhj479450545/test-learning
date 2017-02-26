package com.rabbitemq.hello_wold;

import com.rabbitmq.client.*;
import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2017/2/21.
 */
public class QueueConsumer extends EndPoint implements Runnable, Consumer {

    public QueueConsumer() {

    }

    public QueueConsumer(String endPointName) throws IOException, TimeoutException {
        super(endPointName);
    }

    public void run() {
//        开始消费消息，并且是自动接收消息
        try {
            channel.basicConsume(endPointName, true, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 当消费者注册完毕时调用此方法
     *
     * @param consumerTag
     */
    public void handleConsumeOk(String consumerTag) {
        System.out.println("Consumer:" + consumerTag + " registed");
    }

    public void handleDelivery(String consumerTag, Envelope envelops, BasicProperties properties, byte[] body) {
        Map map = (Map) SerializationUtils.deserialize(body);
        System.out.println("Message Number" + map.get("message number") + " received.");

    }

    public void handleCancelOk(String s) {

    }

    public void handleCancel(String s) throws IOException {

    }

    public void handleShutdownSignal(String s, ShutdownSignalException e) {

    }

    public void handleRecoverOk(String s) {

    }

    public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {

    }

}
