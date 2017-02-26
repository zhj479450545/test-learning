package com.rabbitemq.bindings_exchanges;

import com.rabbitmq.client.QueueingConsumer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/22.
 * 消息接收端类，将消息写入文件
 */
public class ReceiveLogsToSave extends BaseConnect {
    public static void main(String[] args) {
        try {
            ReceiveLogsToSave receiveLogsToSave = new ReceiveLogsToSave();
//            创建一个非持久的、唯一的且自动删除的队列
            String queueName = receiveLogsToSave.channel.queueDeclare().getQueue();
//            为转发器指定队列，设置bingding
            receiveLogsToSave.channel.queueBind(queueName, EXCHANGE_NAME, "");
            System.out.println("[*] Waiting for messages. To exit press Ctrl+C");
            QueueingConsumer consumer = new QueueingConsumer(receiveLogsToSave.channel);
            // 指定接收者，第二个参数为自动应答，无需手动应答
            receiveLogsToSave.channel.basicConsume(queueName, true, consumer);
                while (true) {
                    try {
                        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                        String msg = new String(delivery.getBody());
                        writeTOFile(msg);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeTOFile(String msg) {
        String dir = ReceiveLogsToSave.class.getClassLoader().getResource("").getPath();
        String logFileName = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
        File file = new File(dir, logFileName + ".txt");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write((msg+"\r\n").getBytes());
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null != fileOutputStream) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
