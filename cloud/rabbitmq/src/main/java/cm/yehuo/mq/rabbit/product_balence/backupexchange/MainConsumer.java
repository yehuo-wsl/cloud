package cm.yehuo.mq.rabbit.product_balence.backupexchange;
import	java.util.HashMap;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Map;

/**
 * @Description:
 * @Author: 夜火
 * @Created Date: 2020年06月08日
 */
public class MainConsumer {

    private static final String exchange_log = "main_exchange";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        //channel.exchangeDeclare(exchange_log, BuiltinExchangeType.DIRECT);需要去掉
        String queue = "main_exqueue";
        channel.queueDeclare(queue,false,false,false,null);
        channel.queueBind(queue, exchange_log, "tom");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body,"UTF-8"));
            }
        };
        channel.basicConsume(queue, true, consumer);
    }

}