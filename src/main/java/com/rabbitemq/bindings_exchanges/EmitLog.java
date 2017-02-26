package com.rabbitemq.bindings_exchanges;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/22.
 * 日志发送端
 */
public class EmitLog extends BaseConnect{

    public static void main(String[] args) {
        try {
            EmitLog emitLog = new EmitLog();
//            往转发器上发送消息
            String msg = new Date().toLocaleString() + "-- log something";
            emitLog.channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
