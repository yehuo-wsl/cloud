package cm.yehuo.mq.rabbit.consumer_balence.ackconfirm;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Description: 取消自动确认，不手动确认（不会删此消息）
 * @Author: 夜火
 * @Created Date: 2020年06月08日
 */
public class AckConsumer2 {

    public static final String exchange_log = "ack_ex";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchange_log, BuiltinExchangeType.DIRECT);
        String queue = "erorsor";
        channel.queueDeclare("erorsor",true,false,false,null);
        channel.queueBind(queue, exchange_log, "tom");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body,"UTF-8"));
            }
        };
        //取消自动确认
        channel.basicConsume(queue, false, consumer);
    }

}