package cm.yehuo.mq.rabbit.consumer_balence.ackconfirm;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Description: 普通生产者
 * @Author: 夜火
 * @Created Date: 2020年06月08日
 */
public class AckProduct {

    private static final String exchange_log = "ack_ex";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchange_log, BuiltinExchangeType.DIRECT);



        String[] routingkeys = new String[]{"tom"};

        for (int i=0;i<4;i++){
            channel.basicPublish(exchange_log, routingkeys[0],null,("i am "+routingkeys[0]).getBytes());
            Thread.sleep(200);
        }
        channel.close();
        connection.close();
    }

}