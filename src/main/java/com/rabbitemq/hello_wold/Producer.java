package com.rabbitemq.hello_wold;

import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2017/2/21.
 * 生产者类的任务是向队列里写一条消息
 */
public class Producer extends EndPoint {
    public Producer() {

    }

    public Producer(String endPiontName) throws IOException, TimeoutException {
        super(endPiontName);
    }

    public void sendMessage(Serializable object) throws IOException {
        channel.basicPublish("", endPointName, null, SerializationUtils.serialize(object));
    }
}
