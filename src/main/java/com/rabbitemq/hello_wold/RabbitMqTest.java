package com.rabbitemq.hello_wold;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2017/2/21.
 */
public class RabbitMqTest {
    public static void testRabbitMq() {
        try {
            QueueConsumer consumer = new QueueConsumer("queue");
            Thread consumerThread = new Thread(consumer);
            consumerThread.start();

            Producer producer = new Producer("queue");
            for (int i=0;i<10000000;i++) {
                HashMap message = new HashMap();
                message.put("message number", i);
                producer.sendMessage(message);
                System.out.println("Message number_" + i + "sent.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        testRabbitMq();
    }
}
