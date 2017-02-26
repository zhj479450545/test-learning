package com.rabbitemq.bindings_exchanges;

import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

/**
 * Created by Administrator on 2017/2/22.
 */
public class ReceiveLogsToConsole extends BaseConnect {

    public static void main(String[] args) {
        try {
            ReceiveLogsToConsole receiveLogsToConsole = new ReceiveLogsToConsole();
            // 创建一个非持久的、唯一的且自动删除的队列
            String queueName = receiveLogsToConsole.channel.queueDeclare().getQueue();
            // 为转发器指定队列，设置binding
            receiveLogsToConsole.channel.queueBind(queueName, EXCHANGE_NAME, "");
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            QueueingConsumer consumer = new QueueingConsumer(receiveLogsToConsole.channel);
            // 指定接收者，第二个参数为自动应答，无需手动应答
            receiveLogsToConsole.channel.basicConsume(queueName, true, consumer);
            while (true) {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());
                System.out.println(" [x] Received '" + message + "'");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
